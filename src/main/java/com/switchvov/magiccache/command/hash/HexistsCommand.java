package com.switchvov.magiccache.command.hash;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Hexists command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class HexistsCommand implements Command {

    @Override
    public String name() {
        return "HEXISTS";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String hkey = getVal(args);
        return Reply.integer(cache.hexists(key, hkey));
    }
}
