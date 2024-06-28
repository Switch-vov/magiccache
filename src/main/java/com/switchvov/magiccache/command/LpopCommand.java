package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

import java.util.Objects;

/**
 * Lpop command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class LpopCommand implements Command {

    @Override
    public String name() {
        return "LPOP";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        int count = 1;
        if (args.length > 6) {
            String val = getVal(args);
            count = Integer.parseInt(val);
            return Reply.array(cache.lpop(key, count));
        }
        String[] lpop = cache.lpop(key, count);
        return Reply.bulkString(Objects.isNull(lpop) ? null : lpop[0]);
    }
}
