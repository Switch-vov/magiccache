package com.switchvov.magiccache.core.opr;

import com.switchvov.magiccache.core.AbstractOperator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Hash operators
 *
 * @author switch
 * @since 2024/6/30
 */
public class HashOperator extends AbstractOperator {

    public Integer hset(String key, String[] hkeys, String[] hvals) {
        if (Objects.isNull(hkeys) || hkeys.length == 0) {
            return 0;
        }
        if (Objects.isNull(hvals) || hvals.length == 0) {
            return 0;
        }
        getMap().computeIfAbsent(key, k -> new CacheEntry<>(new LinkedHashMap<String, String>(), System.currentTimeMillis(), -1000));
        CacheEntry<?> entry = getEntry(key);
        LinkedHashMap<String, String> exist = (LinkedHashMap<String, String>) entry.getValue();
        for (int i = 0; i < hkeys.length; i++) {
            exist.put(hkeys[i], hvals[i]);
        }
        return Math.toIntExact(Arrays.stream(hkeys).distinct().count());
    }

    public String hget(String key, String hkey) {
        Optional<LinkedHashMap<String, String>> exist = getValueFromHash(key);
        return exist.map(e -> e.get(hkey)).orElse(null);
    }

    public String[] hgetall(String key) {
        Optional<LinkedHashMap<String, String>> exits = getValueFromHash(key);
        return exits.map(e ->
                e.entrySet().stream().flatMap(en -> Stream.of(en.getKey(), en.getValue())).toArray(String[]::new)
        ).orElse(null);
    }

    public String[] hmget(String key, String[] hkeys) {
        Optional<LinkedHashMap<String, String>> exist = getValueFromHash(key);
        return exist.map(e ->
                Objects.isNull(hkeys) ? new String[0] : Arrays.stream(hkeys).map(e::get).toArray(String[]::new)
        ).orElse(null);
    }

    public Integer hlen(String key) {
        Optional<LinkedHashMap<String, String>> exist = getValueFromHash(key);
        return exist.map(HashMap::size).orElse(0);
    }

    public Integer hexists(String key, String hkey) {
        Optional<LinkedHashMap<String, String>> exist = getValueFromHash(key);
        return exist.map(e -> e.containsKey(hkey) ? 1 : 0).orElse(0);
    }

    public Integer hdel(String key, String[] hkeys) {
        Optional<LinkedHashMap<String, String>> exist = getValueFromHash(key);
        return exist.map(e ->
                Objects.isNull(hkeys) ? 0 : Math.toIntExact(Arrays.stream(hkeys).map(e::remove).filter(Objects::nonNull).count())
        ).orElse(0);
    }

    private Optional<LinkedHashMap<String, String>> getValueFromHash(String key) {
        if (checkInvalid(key)) {
            return Optional.empty();
        }
        CacheEntry<?> entry = getEntry(key);
        LinkedHashMap<String, String> exist = (LinkedHashMap<String, String>) entry.getValue();
        return Optional.ofNullable(exist);
    }
}
