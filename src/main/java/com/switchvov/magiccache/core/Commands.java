package com.switchvov.magiccache.core;

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

        // string

        // list
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
