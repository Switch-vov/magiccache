package com.switchvov.magiccache.command.set;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Sadd command.
 *
 * @author switch
 * @since 2024/06/29
 */
public class SaddCommand implements Command {

    @Override
    public String name() {
        return "SADD";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] vals = getParamsNoKey(args);
        return Reply.integer(cache.sadd(key, vals));
    }
}
