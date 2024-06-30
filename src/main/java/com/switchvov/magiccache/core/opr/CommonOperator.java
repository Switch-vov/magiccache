package com.switchvov.magiccache.core.opr;

import com.switchvov.magiccache.core.AbstractOperator;

import java.util.Arrays;
import java.util.Objects;

/**
 * common operators.
 *
 * @author switch
 * @since 2024/6/30
 */
public class CommonOperator extends AbstractOperator {

    public int exists(String... keys) {
        return Objects.isNull(keys) ? 0 : Math.toIntExact(Arrays.stream(keys).filter(getMap()::containsKey).count());
    }

    public int del(String... keys) {
        return Objects.isNull(keys) ? 0 : Math.toIntExact(Arrays.stream(keys).map(getMap()::remove).filter(Objects::nonNull).count());
    }

    public boolean expire(String key, long ttl) {
        CacheEntry<?> entry = getEntry(key);
        if (Objects.isNull(entry)) {
            return false;
        }
        entry.setTtl(ttl * 1000L);
        entry.setTs(System.currentTimeMillis());
        return true;
    }

    public long ttl(String key) {
        CacheEntry<?> entry = getEntry(key);
        if (Objects.isNull(entry)) {
            return -2;
        }
        if (entry.getTtl() == -1000L) {
            return -1;
        }
        long ret = (entry.getTs() + entry.getTtl() - CURRENT) / 1000;
        if (ret <= 0) {
            return -1;
        }
        return ret;
    }
}
