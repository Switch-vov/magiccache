package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Decr command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class DelCommand implements Command {
    @Override
    public String name() {
        return "DEL";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String[] key = getParams(args);
        return Reply.integer(cache.del(key));
    }
}
