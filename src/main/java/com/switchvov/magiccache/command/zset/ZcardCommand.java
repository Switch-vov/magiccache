package com.switchvov.magiccache.command.zset;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

import java.util.Arrays;

/**
 * Zcard command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class ZcardCommand implements Command {

    @Override
    public String name() {
        return "ZCARD";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.integer(cache.zcard(key));
    }
}
