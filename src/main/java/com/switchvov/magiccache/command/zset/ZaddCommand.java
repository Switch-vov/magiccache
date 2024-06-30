package com.switchvov.magiccache.command.zset;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

import java.util.Arrays;

/**
 * Zadd command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class ZaddCommand implements Command {

    @Override
    public String name() {
        return "ZADD";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] scores = getHkeys(args);
        String[] vals = getHvals(args);
        return Reply.integer(cache.zadd(key, vals, toDouble(scores)));
    }

    private double[] toDouble(String[] scores) {
        return Arrays.stream(scores).mapToDouble(Double::parseDouble).toArray();
    }
}
