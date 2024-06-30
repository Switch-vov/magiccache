package com.switchvov.magiccache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Stream;

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
        if (Objects.isNull(entry)) {
            return null;
        }
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
        int len = Math.min(size, end - start + 1);
        String[] ret = new String[len];
        for (int i = 0; i < len; i++) {
            ret[i] = exist.get(start + i);
        }
        return ret;
    }

    // ===============  2. list end ===========

    // ===============  3. set  ===========

    public Integer sadd(String key, String[] vals) {
        if (vals == null) {
            return 0;
        }
        map.computeIfAbsent(key, k -> new CacheEntry<>(new LinkedHashSet<String>()));
        CacheEntry<?> entry = map.get(key);
        LinkedHashSet<String> exist = (LinkedHashSet<String>) entry.getValue();
        int count = 0;
        for (String val : vals) {
            if (exist.add(val)) {
                count++;
            }
        }
        return count;
    }

    public String[] smembers(String key) {
        Optional<LinkedHashSet<String>> exist = getValueFromSet(key);
        return exist.map(e -> e.toArray(String[]::new)).orElse(null);
    }

    public Integer scard(String key) {
        Optional<LinkedHashSet<String>> exist = getValueFromSet(key);
        return exist.map(HashSet::size).orElse(0);
    }

    public Integer sismember(String key, String val) {
        Optional<LinkedHashSet<String>> exist = getValueFromSet(key);
        return exist.map(e -> e.contains(val) ? 1 : 0).orElse(0);
    }

    public Integer srem(String key, String[] vals) {
        if (Objects.isNull(vals)) {
            return 0;
        }
        Optional<LinkedHashSet<String>> exist = getValueFromSet(key);
        return exist.map(e -> Math.toIntExact(Arrays.stream(vals).filter(e::remove).count())).orElse(0);
    }

    public String[] spop(String key, int count) {
        Optional<LinkedHashSet<String>> exist = getValueFromSet(key);
        return exist.map(e -> {
            int len = Math.min(count, e.size());
            String[] ret = new String[len];
            for (int i = 0; i < len; i++) {
                List<String> array = e.stream().toList();
                String obj = array.get(ThreadLocalRandom.current().nextInt(array.size()));
                e.remove(obj);
                ret[i] = obj;
            }
            return ret;
        }).orElse(null);
    }

    private Optional<LinkedHashSet<String>> getValueFromSet(String key) {
        CacheEntry<?> entry = map.get(key);
        if (Objects.isNull(entry)) {
            return Optional.empty();
        }
        LinkedHashSet<String> exist = (LinkedHashSet<String>) entry.getValue();
        return Optional.ofNullable(exist);
    }

    // ===============  3. set end ===========

    // ===============  4. hash ===========

    public Integer hset(String key, String[] hkeys, String[] hvals) {
        if (Objects.isNull(hkeys) || hkeys.length == 0) {
            return 0;
        }
        if (Objects.isNull(hvals) || hvals.length == 0) {
            return 0;
        }
        map.computeIfAbsent(key, k -> new CacheEntry<>(new LinkedHashMap<String, String>()));
        CacheEntry<?> entry = map.get(key);
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
        CacheEntry<?> entry = map.get(key);
        if (Objects.isNull(entry)) {
            return Optional.empty();
        }
        LinkedHashMap<String, String> exist = (LinkedHashMap<String, String>) entry.getValue();
        return Optional.ofNullable(exist);
    }

    // ===============  4. hash end ===========

    // ===============  5. zset end ===========

    // ===============  5. zset end ===========

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CacheEntry<T> {
        private T value;
    }
}
