package com.switchvov.magiccache.command;

import com.switchvov.magiccache.core.Command;
import com.switchvov.magiccache.core.Commands;
import com.switchvov.magiccache.core.MagicCache;
import com.switchvov.magiccache.core.Reply;

/**
 * Command command.
 *
 * @author switch
 * @since 2024/06/28
 */
public class CommandCommand implements Command {
    @Override
    public String name() {
        return "COMMAND";
    }

    @Override
    public Reply<?> exec(MagicCache cache, String[] args) {
        return Reply.array(Commands.getCommandNames());
    }
}
