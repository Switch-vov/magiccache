package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Ping command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class PingCommand implements Command {
    private static final String PONG = "PONG";

    @Override
    public String name() {
        return "PING";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        String ret = "PONG";
        if (args.length >= 5) {
            ret = args[4];
        }
        return Reply.string(OK);
    }
}
