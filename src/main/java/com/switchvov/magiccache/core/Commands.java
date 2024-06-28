package com.switchvov.magiccache.core;

import com.switchvov.magiccache.command.CommandCommand;
import com.switchvov.magiccache.command.DecrCommand;
import com.switchvov.magiccache.command.DelCommand;
import com.switchvov.magiccache.command.ExistsCommand;
import com.switchvov.magiccache.command.GetCommand;
import com.switchvov.magiccache.command.IncrCommand;
import com.switchvov.magiccache.command.InfoCommand;
import com.switchvov.magiccache.command.LindexCommand;
import com.switchvov.magiccache.command.LlenCommand;
import com.switchvov.magiccache.command.LpopCommand;
import com.switchvov.magiccache.command.LpushCommand;
import com.switchvov.magiccache.command.LrangeCommand;
import com.switchvov.magiccache.command.MgetCommand;
import com.switchvov.magiccache.command.MsetCommand;
import com.switchvov.magiccache.command.PingCommand;
import com.switchvov.magiccache.command.RpopCommand;
import com.switchvov.magiccache.command.RpushCommand;
import com.switchvov.magiccache.command.SetCommand;
import com.switchvov.magiccache.command.StrlenCommand;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * register commands.
 *
 * @author switch
 * @since 2024/06/28
 */
public class Commands {
    private static final Map<String, Command> ALL = new LinkedHashMap<>();

    static {
        initCommands();
    }

    private static void initCommands() {
        // common commands
        register(new PingCommand());
        register(new InfoCommand());
        register(new CommandCommand());

        // string
        register(new SetCommand());
        register(new GetCommand());
        register(new StrlenCommand());
        register(new DelCommand());
        register(new ExistsCommand());
        register(new IncrCommand());
        register(new DecrCommand());
        register(new MsetCommand());
        register(new MgetCommand());

        // list
        register(new LpushCommand());
        register(new LpopCommand());
        register(new RpopCommand());
        register(new RpushCommand());
        register(new LlenCommand());
        register(new LindexCommand());
        register(new LrangeCommand());
    }

    public static void register(Command command) {
        ALL.put(command.name(), command);
    }

    public static Command get(String name) {
        return ALL.get(name);
    }

    public static String[] getCommandNames() {
        return ALL.keySet().toArray(new String[0]);
    }
}
