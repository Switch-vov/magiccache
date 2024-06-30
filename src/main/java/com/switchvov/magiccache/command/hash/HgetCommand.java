package com.switchvov.magiccache.command.hash;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Hget command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class HgetCommand implements Command {

    @Override
    public String name() {
        return "HGET";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String hkey = getVal(args);
        return Reply.bulkString(cache.hget(key, hkey));
    }
}
