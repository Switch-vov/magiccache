package com.switchvov.magiccache.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * cache entries.
 *
 * @author switch
 * @since 2024/06/17
 */
public class MagicCache {
    Map<String, String> map = new HashMap<>();

    public String get(String key) {
        return map.get(key);
    }

    public void set(String key, String value) {
        map.put(key, value);
    }

    public int del(String... keys) {
        return Objects.isNull(keys) ? 0 : (int) Arrays.stream(keys).map(map::remove).filter(Objects::nonNull).count();
    }

    public int exists(String... keys) {
        return Objects.isNull(keys) ? 0 : (int) Arrays.stream(keys).filter(map::containsKey).count();
    }

    public String[] mget(String... keys) {
        return Objects.isNull(keys) ? new String[0] : Arrays.stream(keys).map(map::get).toArray(String[]::new);
    }

    public void mset(String[] keys, String[] vals) {
        if (Objects.isNull(keys) || keys.length == 0) {
            return;
        }
        for (int i = 0; i < keys.length; i++) {
            set(keys[i], vals[i]);
        }
    }

    public int incr(String key) {
        return toInt(key, val -> val + 1);
    }

    public int decr(String key) {
        return toInt(key, val -> val - 1);
    }

    public int toInt(String key, Function<Integer, Integer> operator) {
        String str = get(key);
        int val = 0;
        if (Objects.nonNull(str)) {
            val = Integer.parseInt(str);
        }
        val = operator.apply(val);
        set(key, String.valueOf(val));
        return val;
    }
}
