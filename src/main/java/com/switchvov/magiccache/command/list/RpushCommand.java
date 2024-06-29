package com.switchvov.magiccache.command.list;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Rpush command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class RpushCommand implements Command {

    @Override
    public String name() {
        return "RPUSH";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.rpush(key, vals));
    }
}
