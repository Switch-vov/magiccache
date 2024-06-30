package com.switchvov.magiccache.core.opr;

import com.switchvov.magiccache.core.AbstractOperator;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * String operators.
 *
 * @author switch
 * @since 2024/6/30
 */
public class StringOperator extends AbstractOperator {

    public void set(String key, String value) {
        putEntry(key, new CacheEntry<>(value, System.currentTimeMillis(), -1000));
    }

    public String get(String key) {
        if (checkInvalid(key)) {
            return null;
        }
        CacheEntry<String> entry = (CacheEntry<String>) getEntry(key);
        return entry.getValue();
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

    public Integer strlen(String key) {
        return Objects.isNull(get(key)) ? null : get(key).length();
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
