package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Incr command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class IncrCommand implements Command {
    @Override
    public String name() {
        return "INCR";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        try {
            return Reply.integer(cache.incr(key));
        } catch (NumberFormatException e) {
            return Reply.error(String.format("NFE %s value[%s] is not an integer.", key, cache.get(key)));
        }
    }
}
