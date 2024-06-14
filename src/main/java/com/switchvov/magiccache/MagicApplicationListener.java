package com.switchvov.magiccache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * plugins entrypoint.
 *
 * @author switch
 * @since 2024/06/14
 */
@Component
public class MagicApplicationListener implements ApplicationListener<ApplicationEvent> {

    private final List<MagicPlugin> plugins;

    public MagicApplicationListener(
            @Autowired List<MagicPlugin> plugins
    ) {
        this.plugins = plugins;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent are) {
            for (MagicPlugin plugin : plugins) {
                plugin.init();
                plugin.startup();
            }
        } else if (event instanceof ContextClosedEvent cce) {
            for (MagicPlugin plugin : plugins) {
                plugin.shutdown();
            }
        }
    }
}
