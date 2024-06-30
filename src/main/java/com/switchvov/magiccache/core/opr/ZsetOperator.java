package com.switchvov.magiccache.core.opr;

import com.switchvov.magiccache.core.AbstractOperator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;

/**
 * Zset operators.
 *
 * @author switch
 * @since 2024/6/30
 */
public class ZsetOperator extends AbstractOperator {

    public Integer zadd(String key, String[] values, double[] scores) {
        if (Objects.isNull(values) || values.length == 0) {
            throw new RuntimeException("ERR wrong number of arguments for 'zadd' command");
        }
        if (Objects.isNull(scores) || scores.length == 0) {
            throw new RuntimeException("ERR wrong number of arguments for 'zadd' command");
        }
        getMap().computeIfAbsent(key, k -> new CacheEntry<>(new LinkedHashSet<ZsetEntry>(), System.currentTimeMillis(), -1000));
        CacheEntry<?> entry = getEntry(key);
        LinkedHashSet<ZsetEntry> exist = (LinkedHashSet<ZsetEntry>) entry.getValue();
        int count = 0;
        for (int i = 0; i < values.length; i++) {
            ZsetEntry addEntry = new ZsetEntry(values[i], scores[i]);
            boolean remove = exist.removeIf(e -> e.equals(addEntry));
            boolean add = exist.add(addEntry);
            if (!remove && add) {
                count++;
            }
        }
        return count;
    }

    public Integer zcard(String key) {
        Optional<LinkedHashSet<ZsetEntry>> exist = getValueFromZset(key);
        return exist.map(HashSet::size).orElse(0);
    }

    public Integer zcount(String key, double min, double max) {
        Optional<LinkedHashSet<ZsetEntry>> exist = getValueFromZset(key);
        return exist.map(e ->
                Math.toIntExact(e.stream().filter(x -> x.getScore() >= min && x.getScore() <= max).count())
        ).orElse(0);
    }

    public Double zscore(String key, String val) {
        Optional<LinkedHashSet<ZsetEntry>> exist = getValueFromZset(key);
        return exist.flatMap(e ->
                e.stream().filter(x -> x.getValue().equals(val)).map(ZsetEntry::getScore).findFirst()
        ).orElse(null);
    }

    public Integer zrank(String key, String val) {
        Optional<LinkedHashSet<ZsetEntry>> exist = getValueFromZset(key);
        return exist.map(e -> {
            Double zscore = zscore(key, val);
            if (Objects.isNull(zscore)) {
                return null;
            }
            return Math.toIntExact(e.stream().filter(x -> x.getScore() < zscore).count());
        }).orElse(null);
    }

    public Integer zrem(String key, String[] vals) {
        Optional<LinkedHashSet<ZsetEntry>> exist = getValueFromZset(key);
        return exist.map(e -> Objects.isNull(vals) ? 0 :
                Math.toIntExact(Arrays.stream(vals).filter(x -> e.removeIf(en -> en.getValue().equals(x))).count())
        ).orElse(null);
    }

    private Optional<LinkedHashSet<ZsetEntry>> getValueFromZset(String key) {
        if (checkInvalid(key)) {
            return Optional.empty();
        }
        CacheEntry<?> entry = getEntry(key);
        LinkedHashSet<ZsetEntry> exist = (LinkedHashSet<ZsetEntry>) entry.getValue();
        return Optional.ofNullable(exist);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode(of = "value")
    private static class ZsetEntry {
        private String value;
        private double score;
    }
}
