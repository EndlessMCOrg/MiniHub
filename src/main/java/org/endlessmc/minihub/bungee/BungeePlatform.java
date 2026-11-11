package org.endlessmc.minihub.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import org.endlessmc.minihub.platform.MiniHubPlatform;
import org.endlessmc.minihub.platform.MiniLogger;

import java.nio.file.Path;
import java.util.logging.Level;

public final class BungeePlatform implements MiniHubPlatform {

    private final Plugin plugin;
    private final MiniLogger logger;

    public BungeePlatform(Plugin plugin) {
        this.plugin = plugin;
        this.logger = new JulLogger(plugin.getLogger());
    }

    @Override
    public MiniLogger logger() {
        return logger;
    }

    @Override
    public Path dataDirectory() {
        return plugin.getDataFolder().toPath();
    }

    @Override
    public boolean isPlayer(Object source) {
        return source instanceof ProxiedPlayer;
    }

    @Override
    public void sendMessage(Object source, String message) {
        if (source instanceof CommandSender commandSender) {
            commandSender.sendMessage(message);
        }
    }

    @Override
    public boolean connectPlayer(Object player, String serverName) {
        if (!(player instanceof ProxiedPlayer proxiedPlayer)) {
            return false;
        }

        ServerInfo targetServer = plugin.getProxy().getServerInfo(serverName);
        if (targetServer == null) {
            return false;
        }

        proxiedPlayer.connect(targetServer);
        return true;
    }

    @Override
    public void registerCommand(String name, CommandHandler handler) {
        plugin.getProxy().getPluginManager().registerCommand(plugin, new Command(name) {
            @Override
            public void execute(CommandSender sender, String[] args) {
                handler.execute(sender);
            }
        });
    }

    private static final class JulLogger implements MiniLogger {

        private final java.util.logging.Logger delegate;

        private JulLogger(java.util.logging.Logger delegate) {
            this.delegate = delegate;
        }

        @Override
        public void info(String message) {
            delegate.info(message);
        }

        @Override
        public void warn(String message) {
            delegate.warning(message);
        }

        @Override
        public void error(String message) {
            delegate.severe(message);
        }

        @Override
        public void error(String message, Throwable throwable) {
            delegate.log(Level.SEVERE, message, throwable);
        }
    }
}