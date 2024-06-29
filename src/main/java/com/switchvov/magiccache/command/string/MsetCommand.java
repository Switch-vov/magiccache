package com.switchvov.magiccache.command.string;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Mset command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class MsetCommand implements Command {

    @Override
    public String name() {
        return "MSET";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String[] keys = getKeys(args);
        String[] vals = getVals(args);
        cache.mset(keys, vals);
        return Reply.string(OK);
    }
}
