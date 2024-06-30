package com.switchvov.magiccache.command.hash;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Hset command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class HsetCommand implements Command {

    @Override
    public String name() {
        return "HSET";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getHkeys(args);
        String[] hvals = getHvals(args);
        return Reply.integer(cache.hset(key, hkeys, hvals));
    }
}
