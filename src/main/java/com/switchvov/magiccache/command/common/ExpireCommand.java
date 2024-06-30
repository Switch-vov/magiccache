package com.switchvov.magiccache.command.common;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Expire command.
 *
 * @author switch
 * @since 2024/6/30
 */
public class ExpireCommand implements Command {
    @Override
    public String name() {
        return "EXPIRE";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String val = getVal(args);
        long ttl = Long.parseLong(val);
        return Reply.integer(cache.expire(key, ttl) ? 1 : 0);
    }
}
