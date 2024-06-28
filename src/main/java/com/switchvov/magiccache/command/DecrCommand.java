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
public class DecrCommand implements Command {
    @Override
    public String name() {
        return "DECR";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.decr(key));
    }
}
