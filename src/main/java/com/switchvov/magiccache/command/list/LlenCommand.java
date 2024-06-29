package com.switchvov.magiccache.command.list;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Llen command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class LlenCommand implements Command {

    @Override
    public String name() {
        return "LLEN";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.llen(key));
    }
}
