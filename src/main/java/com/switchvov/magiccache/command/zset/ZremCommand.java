package com.switchvov.magiccache.command.zset;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Zrem command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class ZremCommand implements Command {

    @Override
    public String name() {
        return "ZREM";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.zrem(key, vals));
    }
}
