package com.switchvov.magiccache.command.zset;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

import java.util.Objects;

/**
 * Zscore command.
 *
 * @author switch
 * @since 2024/06/30
 */
public class ZscoreCommand implements Command {

    @Override
    public String name() {
        return "ZSCORE";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String key = getKey(args);
        String val = getVal(args);
        Double zscore = cache.zscore(key, val);
        return Reply.string(Objects.isNull(zscore) ? null : zscore.toString());
    }
}
