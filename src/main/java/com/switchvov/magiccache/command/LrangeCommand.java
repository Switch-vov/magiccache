package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Lrange command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class LrangeCommand implements Command {

    @Override
    public String name() {
        return "LRANGE";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] params = getParamsNoKey(args);
        int start = Integer.parseInt(params[0]);
        int end = Integer.parseInt(params[1]);
        return Reply.array(cache.lrange(key, start, end));
    }
}
