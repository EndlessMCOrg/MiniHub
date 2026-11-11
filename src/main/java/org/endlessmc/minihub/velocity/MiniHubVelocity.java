package org.endlessmc.minihub.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.endlessmc.minihub.MiniHubCore;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "minihub",
        name = "MiniHub",
        version = "1.2",
        description = "简单的命令到服务器的实现。",
        url = "https://github.com/EndlessMCOrg/MiniHub/",
        authors = {"OutHimic", "EndlessMCOrg"}
)
public final class MiniHubVelocity {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    @Inject
    public MiniHubVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        new MiniHubCore(new VelocityPlatform(server, logger, dataDirectory)).enable();
    }
}