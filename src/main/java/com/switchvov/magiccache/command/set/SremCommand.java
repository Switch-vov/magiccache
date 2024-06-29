package com.switchvov.magiccache.command.set;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

import java.util.Objects;

/**
 * Srem command.
 *
 * @author switch
 * @since 2024/06/29
 */
public class SremCommand implements Command {

    @Override
    public String name() {
        return "SREM";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.srem(key, vals));
    }
}
