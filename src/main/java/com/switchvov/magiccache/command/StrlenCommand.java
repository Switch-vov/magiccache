package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Strlen command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class StrlenCommand implements Command {

    @Override
    public String name() {
        return "STRLEN";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.strlen(key));
    }
}
