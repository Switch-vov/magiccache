package com.switchvov.magiccache.command.zset;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

import java.util.Objects;

/**
 * Zrank command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class ZrankCommand implements Command {

    @Override
    public String name() {
        return "ZRANK";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String val = getVal(args);
        Integer zrank = cache.zrank(key, val);
        if (Objects.isNull(zrank)) {
            return Reply.string(null);
        }
        return Reply.integer(zrank);
    }
}
