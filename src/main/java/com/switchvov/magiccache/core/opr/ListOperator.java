package com.switchvov.magiccache.core.opr;

import com.switchvov.magiccache.core.AbstractOperator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * List operator.
 *
 * @author switch
 * @since 2024/6/30
 */
public class ListOperator extends AbstractOperator {
    public Integer lpush(String key, String... vals) {
        if (vals == null) {
            return 0;
        }
        getMap().computeIfAbsent(key, k -> new CacheEntry<>(new LinkedList<String>(), System.currentTimeMillis(), -1000));
        CacheEntry<?> entry = getEntry(key);
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        Arrays.stream(vals).forEach(exist::addFirst);
        return vals.length;
    }

    public String[] lpop(String key, int count) {
        Optional<LinkedList<String>> exist = getValueFromList(key);
        return exist.map(e -> {
            int len = Math.min(count, e.size());
            String[] ret = new String[len];
            for (int i = 0; i < len; i++) {
                ret[i] = e.removeFirst();
            }
            return ret;
        }).orElse(null);

    }

    public Integer rpush(String key, String... vals) {
        if (vals == null) {
            return 0;
        }
        getMap().computeIfAbsent(key, k -> new CacheEntry<>(new LinkedList<String>(), System.currentTimeMillis(), -1000));
        CacheEntry<?> entry = getEntry(key);
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        exist.addAll(List.of(vals));
        return vals.length;
    }

    public String[] rpop(String key, int count) {
        Optional<LinkedList<String>> exist = getValueFromList(key);
        return exist.map(e -> {
            int len = Math.min(count, e.size());
            String[] ret = new String[len];
            for (int i = 0; i < len; i++) {
                ret[i] = e.removeLast();
            }
            return ret;
        }).orElse(null);
    }

    public int llen(String key) {
        Optional<LinkedList<String>> exist = getValueFromList(key);
        return exist.map(LinkedList::size).orElse(0);
    }

    public String lindex(String key, int index) {
        Optional<LinkedList<String>> exist = getValueFromList(key);
        return exist.map(e -> {
            if (index >= e.size()) {
                return null;
            }
            return e.get(index);
        }).orElse(null);
    }

    public String[] lrange(String key, int start, int end) {
        Optional<LinkedList<String>> exist = getValueFromList(key);
        return exist.map(e -> {
            int size = e.size();
            int st = start;
            int en = end;
            if (st >= size) {
                return null;
            }
            if (st <= 0) {
                st = 0;
            }
            if (en >= size) {
                en = size - 1;
            }
            int len = Math.min(size, en - st + 1);
            String[] ret = new String[len];
            for (int i = 0; i < len; i++) {
                ret[i] = e.get(st + i);
            }
            return ret;
        }).orElse(null);
    }

    private Optional<LinkedList<String>> getValueFromList(String key) {
        if (checkInvalid(key)) {
            return Optional.empty();
        }
        CacheEntry<?> entry = getEntry(key);
        LinkedList<String> exist = (LinkedList<String>) entry.getValue();
        return Optional.ofNullable(exist);
    }
}
