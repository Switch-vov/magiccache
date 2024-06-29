package com.switchvov.magiccache.command.common;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Info command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class InfoCommand implements Command {
    private static final String INFO = "MagicCache server, [v1.0.0], created by switch." + CRLF
            + "Mock Redis Server at 2024-06-14." + CRLF;

    @Override
    public String name() {
        return "INFO";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        return Reply.bulkString(INFO);
    }
}
