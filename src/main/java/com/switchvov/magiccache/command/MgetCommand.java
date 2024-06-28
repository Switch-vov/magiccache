package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Mget command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class MgetCommand implements Command {

    @Override
    public String name() {
        return "MGET";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String[] keys = getParams(args);
        return Reply.array(cache.mget(keys));
    }
}
