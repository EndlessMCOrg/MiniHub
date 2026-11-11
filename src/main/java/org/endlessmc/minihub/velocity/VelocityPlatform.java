package org.endlessmc.minihub.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.endlessmc.minihub.platform.MiniHubPlatform;
import org.endlessmc.minihub.platform.MiniLogger;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.Optional;

public final class VelocityPlatform implements MiniHubPlatform {

    private final ProxyServer server;
    private final MiniLogger logger;
    private final Path dataDirectory;

    public VelocityPlatform(ProxyServer server, Logger logger, Path dataDirectory) {
        this.server = server;
        this.logger = new Slf4jLogger(logger);
        this.dataDirectory = dataDirectory;
    }

    @Override
    public MiniLogger logger() {
        return logger;
    }

    @Override
    public Path dataDirectory() {
        return dataDirectory;
    }

    @Override
    public boolean isPlayer(Object source) {
        return source instanceof Player;
    }

    @Override
    public void sendMessage(Object source, String message) {
        if (source instanceof CommandSource commandSource) {
            commandSource.sendMessage(LegacyComponentSerializer.legacySection().deserialize(message));
        }
    }

    @Override
    public boolean connectPlayer(Object player, String serverName) {
        if (!(player instanceof Player proxyPlayer)) {
            return false;
        }

        Optional<RegisteredServer> targetServer = server.getServer(serverName);
        if (targetServer.isEmpty()) {
            return false;
        }

        proxyPlayer.createConnectionRequest(targetServer.get()).fireAndForget();
        return true;
    }

    @Override
    public void registerCommand(String name, CommandHandler handler) {
        server.getCommandManager().register(
                server.getCommandManager().metaBuilder(name).build(),
                new SimpleCommand() {
                    @Override
                    public void execute(Invocation invocation) {
                        handler.execute(invocation.source());
                    }
                });
    }

    private static final class Slf4jLogger implements MiniLogger {

        private final Logger delegate;

        private Slf4jLogger(Logger delegate) {
            this.delegate = delegate;
        }

        @Override
        public void info(String message) {
            delegate.info(message);
        }

        @Override
        public void warn(String message) {
            delegate.warn(message);
        }

        @Override
        public void error(String message) {
            delegate.error(message);
        }

        @Override
        public void error(String message, Throwable throwable) {
            delegate.error(message, throwable);
        }
    }
}