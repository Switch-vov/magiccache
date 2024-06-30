package com.switchvov.magiccache.core;

import com.switchvov.magiccache.command.common.CommandCommand;
import com.switchvov.magiccache.command.hash.HdelCommand;
import com.switchvov.magiccache.command.hash.HexistsCommand;
import com.switchvov.magiccache.command.hash.HgetCommand;
import com.switchvov.magiccache.command.hash.HgetallCommand;
import com.switchvov.magiccache.command.hash.HlenCommand;
import com.switchvov.magiccache.command.hash.HmgetCommand;
import com.switchvov.magiccache.command.hash.HsetCommand;
import com.switchvov.magiccache.command.set.SaddCommand;
import com.switchvov.magiccache.command.set.ScardCommand;
import com.switchvov.magiccache.command.set.SismemberCommand;
import com.switchvov.magiccache.command.set.SmembersCommand;
import com.switchvov.magiccache.command.set.SpopCommand;
import com.switchvov.magiccache.command.set.SremCommand;
import com.switchvov.magiccache.command.string.DecrCommand;
import com.switchvov.magiccache.command.string.DelCommand;
import com.switchvov.magiccache.command.string.ExistsCommand;
import com.switchvov.magiccache.command.string.GetCommand;
import com.switchvov.magiccache.command.string.IncrCommand;
import com.switchvov.magiccache.command.common.InfoCommand;
import com.switchvov.magiccache.command.list.LindexCommand;
import com.switchvov.magiccache.command.list.LlenCommand;
import com.switchvov.magiccache.command.list.LpopCommand;
import com.switchvov.magiccache.command.list.LpushCommand;
import com.switchvov.magiccache.command.list.LrangeCommand;
import com.switchvov.magiccache.command.string.MgetCommand;
import com.switchvov.magiccache.command.string.MsetCommand;
import com.switchvov.magiccache.command.common.PingCommand;
import com.switchvov.magiccache.command.list.RpopCommand;
import com.switchvov.magiccache.command.list.RpushCommand;
import com.switchvov.magiccache.command.string.SetCommand;
import com.switchvov.magiccache.command.string.StrlenCommand;

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

        // set
        register(new SaddCommand());
        register(new SmembersCommand());
        register(new SremCommand());
        register(new ScardCommand());
        register(new SpopCommand());
        register(new SismemberCommand());

        // hash
        register(new HsetCommand());
        register(new HgetCommand());
        register(new HgetallCommand());
        register(new HlenCommand());
        register(new HdelCommand());
        register(new HexistsCommand());
        register(new HmgetCommand());
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
