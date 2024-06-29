package com.switchvov.magiccache.command.set;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Scard command.
 *
 * @author switch
 * @since 2024/06/29
 */
public class ScardCommand implements Command {

    @Override
    public String name() {
        return "SCARD";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.scard(key));
    }
}
