package com.switchvov.magiccache.command.hash;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Hdel command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class HdelCommand implements Command {

    @Override
    public String name() {
        return "HDEL";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] hkeys = getParamsNoKey(args);
        return Reply.integer(cache.hdel(key, hkeys));
    }
}
