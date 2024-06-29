package com.switchvov.magiccache.command.set;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Smembers command.
 *
 * @author switch
 * @since 2024/06/29
 */
public class SmembersCommand implements Command {

    @Override
    public String name() {
        return "SMEMBERS";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        return Reply.array(cache.smembers(key));
    }
}
