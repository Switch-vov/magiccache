package com.switchvov.magiccache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Function;

/**
 * cache entries.
 *
 * @author switch
 * @since 2024/06/17
 */
public class MagicCache {
    Map<String, CacheEntry<?>> map = new HashMap<>();

    // ===============  1. String  ===========

    public String get(String key) {
        CacheEntry<String> entry = (CacheEntry<String>) map.get(key);
        return entry.getValue();
    }

    public void set(String key, String value) {
        map.put(key, new CacheEntry<>(value));
    }

    public int del(String... keys) {
        return Objects.isNull(keys) ? 0 : (int) Arrays.stream(keys).map(map::remove).filter(Objects::nonNull).count();
    }

    public int exists(String... keys) {
        return Objects.isNull(keys) ? 0 : (int) Arrays.stream(keys).filter(map::containsKey).count();
    }

    public String[] mget(String... keys) {
        return Objects.isNull(keys) ? new String[0] : Arrays.stream(keys).map(this::get).toArray(String[]::new);
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

    public Integer strlen(String key) {
        return Objects.isNull(get(key)) ? null : get(key).length();
    }

    // ===============  1. String end ===========

    // ===============  2. list  ===========

    public Integer lpush(String key, String... vals) {
        if (vals == null) {
            return 0;
        }
        map.computeIfAbsent(key, k -> new CacheEntry<>(new LinkedList<String>()));
        CacheEntry<?> entry = map.get(key);
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        Arrays.stream(vals).forEach(exist::addFirst);
        return vals.length;
    }

    public String[] lpop(String key, int count) {
        CacheEntry<?> entry = map.get(key);
        if (Objects.isNull(entry)) {
            return null;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (Objects.isNull(exist)) {
            return null;
        }
        int len = Math.min(count, exist.size());
        String[] ret = new String[len];
        for (int i = 0; i < len; i++) {
            ret[i] = exist.removeFirst();
        }
        return ret;
    }

    public Integer rpush(String key, String... vals) {
        if (vals == null) {
            return 0;
        }
        map.computeIfAbsent(key, k -> new CacheEntry<>(new LinkedList<String>()));
        CacheEntry<?> entry = map.get(key);
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        exist.addAll(List.of(vals));
        return vals.length;
    }

    public String[] rpop(String key, int count) {
        CacheEntry<?> entry = map.get(key);
        if (Objects.isNull(entry)) {
            return null;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (Objects.isNull(exist)) {
            return null;
        }
        int len = Math.min(count, exist.size());
        String[] ret = new String[len];
        for (int i = 0; i < len; i++) {
            ret[i] = exist.removeLast();
        }
        return ret;
    }

    public int llen(String key) {
        CacheEntry<?> entry = map.get(key);
        if (Objects.isNull(entry)) {
            return 0;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (Objects.isNull(exist)) {
            return 0;
        }
        return exist.size();
    }

    public String lindex(String key, int index) {
        CacheEntry<?> entry = map.get(key);
        if (Objects.isNull(entry)) {
            return null;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (Objects.isNull(exist)) {
            return null;
        }
        if (index >= exist.size()) {
            return null;
        }
        return exist.get(index);
    }

    public String[] lrange(String key, int start, int end) {
        CacheEntry<?> entry = map.get(key);
        if (Objects.isNull(entry)) {
            return null;
        }
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        if (Objects.isNull(exist)) {
            return null;
        }
        int size = exist.size();
        if (start >= size) {
            return null;
        }
        if (start <= 0) {
            start = 0;
        }
        if (end >= size) {
            end = size - 1;
        }
        int len = Math.min(size, end - size + 1);
        String[] ret = new String[len];
        for (int i = 0; i < len; i++) {
            ret[i] = exist.get(start + i);
        }
        return ret;
    }

    // ===============  2. list end ===========

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CacheEntry<T> {
        private T value;
    }
}
