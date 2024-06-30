package com.switchvov.magiccache.core.opr;

import com.switchvov.magiccache.core.AbstractOperator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Set operators.
 *
 * @author switch
 * @since 2024/6/30
 */
public class SetOperator extends AbstractOperator {
    public Integer sadd(String key, String[] vals) {
        if (vals == null) {
            return 0;
        }
        getMap().computeIfAbsent(key, k -> new CacheEntry<>(new LinkedHashSet<String>(), System.currentTimeMillis(), -1000L));
        CacheEntry<?> entry = getEntry(key);
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
        if (checkInvalid(key)) {
            return Optional.empty();
        }
        CacheEntry<?> entry = getEntry(key);
        LinkedHashSet<String> exist = (LinkedHashSet<String>) entry.getValue();
        return Optional.ofNullable(exist);
    }

}
