package org.endlessmc.minihub;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Plugin(
        id = "minihub",
        name = "MiniHub",
        // 插件运行时元数据以 velocity-plugin.json 为准，此处的版本号仅作为源码注解信息
        version = "1.1-SNAPSHOT",
        description = "简单的命令到服务器的实现。",
        url = "https://github.com/EndlessMCOrg/MiniHub/",
        authors = {"OutHimic", "EndlessMCOrg"}
)
public class MiniHub {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;

    private ConfigManager configManager;
    private LanguageManager languageManager;
    private Map<String, String> serverCommands;

    @Inject
    public MiniHub(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        try {
            Files.createDirectories(dataDirectory);
        } catch (IOException e) {
            logger.error("无法创建插件数据目录", e);
            return;
        }

        configManager = new ConfigManager(dataDirectory, logger);
        if (!configManager.loadConfig()) {
            logger.error("无法加载配置文件，插件将禁用");
            return;
        }

        languageManager = new LanguageManager(dataDirectory, configManager.getLanguage(), logger);
        if (!languageManager.loadLanguage()) {
            logger.error("无法加载语言文件，插件将禁用");
            return;
        }

        loadServerCommands();
        registerCommands();

        logger.info("MiniHub plugin enabled");
    }

    private void loadServerCommands() {
        serverCommands = new HashMap<>();

        for (Map.Entry<String, Object> entry : configManager.getServers().entrySet()) {
            if (!(entry.getValue() instanceof Map<?, ?> serverData)) {
                continue;
            }

            if (!(serverData.get("commands") instanceof List<?> commands)) {
                continue;
            }

            for (Object commandObject : commands) {
                if (commandObject == null) {
                    continue;
                }

                String command = commandObject.toString().trim();
                if (command.isEmpty()) {
                    continue;
                }

                serverCommands.put(command.toLowerCase(Locale.ROOT), entry.getKey());
            }
        }

        logger.info("已加载 {} 个命令映射", serverCommands.size());
    }

    private void registerCommands() {
        for (Map.Entry<String, String> entry : serverCommands.entrySet()) {
            String command = entry.getKey();
            try {
                server.getCommandManager().register(
                        server.getCommandManager().metaBuilder(command).build(),
                        new HubCommand(command));
                logger.info("注册命令: /{}", command);
            } catch (IllegalArgumentException e) {
                logger.warn("命令 /{} 已被其他插件注册，跳过", command);
            }
        }
    }

    private final class HubCommand implements SimpleCommand {

        private final String command;

        private HubCommand(String command) {
            this.command = command;
        }

        @Override
        public void execute(Invocation invocation) {
            CommandSource source = invocation.source();

            if (!(source instanceof Player player)) {
                source.sendMessage(languageManager.getMessage("command.player_only"));
                return;
            }

            String targetServerName = serverCommands.get(command);
            if (targetServerName == null) {
                player.sendMessage(languageManager.getMessage("command.server_not_found"));
                return;
            }

            Optional<RegisteredServer> targetServer = server.getServer(targetServerName);
            if (targetServer.isEmpty()) {
                player.sendMessage(languageManager.getMessage("command.server_offline"));
                return;
            }

            player.createConnectionRequest(targetServer.get()).fireAndForget();
            player.sendMessage(languageManager.getMessage("command.teleporting", Map.of("server", targetServerName)));
        }
    }
}
