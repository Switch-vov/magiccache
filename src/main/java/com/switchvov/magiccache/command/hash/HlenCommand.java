package com.switchvov.magiccache.command.hash;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Hlen command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class HlenCommand implements Command {

    @Override
    public String name() {
        return "HLEN";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.hlen(key));
    }
}
