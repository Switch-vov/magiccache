package com.switchvov.magiccache.command.set;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

import java.util.Objects;

/**
 * Spop command.
 *
 * @author switch
 * @since 2024/06/29
 */
public class SpopCommand implements Command {

    @Override
    public String name() {
        return "SPOP";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        int count = 1;
        if (args.length > 6) {
            String val = getVal(args);
            count = Integer.parseInt(val);
            return Reply.array(cache.spop(key, count));
        }
        String[] spop = cache.spop(key, count);
        return Reply.bulkString(Objects.isNull(spop) ? null : spop[0]);
    }
}
