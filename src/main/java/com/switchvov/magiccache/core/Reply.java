package com.switchvov.magiccache.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author switch
 * @since 2024/06/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply<T> {
    T value;
    ReplyType type;

    public static Reply<String> string(String value) {
        return new Reply<>(value, ReplyType.SIMPLE_STRING);
    }

    public static Reply<String> bulkString(String value) {
        return new Reply<>(value, ReplyType.BULK_STRING);
    }

    public static Reply<Integer> integer(Integer value) {
        return new Reply<>(value, ReplyType.INT);
    }

    public static Reply<String> error(String value) {
        return new Reply<>(value, ReplyType.ERROR);
    }

    public static Reply<String[]> array(String[] value) {
        return new Reply<>(value, ReplyType.ARRAY);
    }
}
