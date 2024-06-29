package com.switchvov.magiccache.command.string;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Set command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class SetCommand implements Command {

    @Override
    public String name() {
        return "SET";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        if (args.length <= 6) {
            cache.set(key, "");
            return Reply.string(OK);
        }
        String val = getVal(args);
        cache.set(key, val);
        return Reply.string(OK);
    }
}
