package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Lpush command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class LpushCommand implements Command {

    @Override
    public String name() {
        return "LPUSH";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.lpush(key, vals));
    }
}
