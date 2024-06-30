package com.switchvov.magiccache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AbstractOperator
 *
 * @author switch
 * @since 2024/6/30
 */
@Slf4j
public abstract class AbstractOperator {
    protected static long CURRENT = System.currentTimeMillis();
    private final static Map<String, CacheEntry<?>> MAP = new ConcurrentHashMap<>(1024);

    public boolean checkInvalid(String key) {
        CacheEntry<?> entry = getEntry(key);
        if (Objects.isNull(entry) || Objects.isNull(entry.getValue())) {
            return true;
        }
        if (entry.getTtl() > 0 && CURRENT - entry.getTs() > entry.getTtl()) {
            log.debug("KEY[{}] expire cause CURRENT[{}]-TS[{}] > TTL[{}] ms", key, CURRENT, entry.getTs(), entry.getTtl());
            MAP.remove(key);
            return true;
        }
        return false;
    }

    public static void updateTs() {
        CURRENT = System.currentTimeMillis();
    }

    protected Map<String, CacheEntry<?>> getMap() {
        return MAP;
    }

    protected CacheEntry<?> getEntry(String key) {
        return MAP.get(key);
    }

    protected Object getEntryValue(String key) {
        return getEntry(key).getValue();
    }

    protected void putEntry(String key, CacheEntry<?> entry) {
        MAP.put(key, entry);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CacheEntry<T> {
        private T value;
        private long ts; // created timestamp
        private long ttl; // alive ttl
    }

}
