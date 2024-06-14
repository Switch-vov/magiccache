package com.switchvov.magiccache;

/**
 * magic cache plugin.
 *
 * @author switch
 * @since 2024/06/14
 */
public interface MagicPlugin {
    void init();

    void startup();

    void shutdown();
}
