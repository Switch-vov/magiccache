package com.switchvov.magiccache.command.zset;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Zcount command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class ZcountCommand implements Command {

    @Override
    public String name() {
        return "ZCOUNT";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String[] params = getParamsNoKey(args);
        double min = Double.parseDouble(params[0]);
        double max = Double.parseDouble(params[1]);
        return Reply.integer(cache.zcount(key, min, max));
    }
}
