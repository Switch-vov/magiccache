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
public class TtlCommand implements Command {
    @Override
    public String name() {
        return "TTL";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(Math.toIntExact(cache.ttl(key)));
    }
}
