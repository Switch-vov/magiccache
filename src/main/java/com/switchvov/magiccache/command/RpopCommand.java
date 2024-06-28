package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Rpop command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class RpopCommand implements Command {

    @Override
    public String name() {
        return "RPOP";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        int count = 1;
        if (args.length > 6) {
            String val = getVal(args);
            count = Integer.parseInt(val);
            return Reply.array(cache.rpop(key, count));
        }

        String[] lpop = cache.rpop(key, count);
        return Reply.bulkString(lpop == null ? null : lpop[0]);
    }
}
