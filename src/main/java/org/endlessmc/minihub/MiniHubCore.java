package org.endlessmc.minihub;

import org.endlessmc.minihub.platform.MiniHubPlatform;
import org.endlessmc.minihub.platform.MiniLogger;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class MiniHubCore {

    private final MiniHubPlatform platform;
    private ConfigManager configManager;
    private LanguageManager languageManager;
    private Map<String, String> serverCommands = Collections.emptyMap();

    public MiniHubCore(MiniHubPlatform platform) {
        this.platform = platform;
    }

    public void enable() {
        MiniLogger logger = platform.logger();

        try {
            Files.createDirectories(platform.dataDirectory());
        } catch (IOException e) {
            logger.error("无法创建插件数据目录", e);
            return;
        }

        configManager = new ConfigManager(platform.dataDirectory(), logger);
        if (!configManager.loadConfig()) {
            logger.error("无法加载配置文件，插件将禁用");
            return;
        }

        languageManager = new LanguageManager(platform.dataDirectory(), configManager.getLanguage(), logger);
        if (!languageManager.loadLanguage()) {
            logger.error("无法加载语言文件，插件将禁用");
            return;
        }

        loadServerCommands();
        registerCommands();

        logger.info("MiniHub plugin enabled");
    }

    private void loadServerCommands() {
        MiniLogger logger = platform.logger();
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

        logger.info("已加载 " + serverCommands.size() + " 个命令映射");
    }

    private void registerCommands() {
        MiniLogger logger = platform.logger();

        for (String command : serverCommands.keySet()) {
            try {
                platform.registerCommand(command, source -> execute(command, source));
                logger.info("注册命令: /" + command);
            } catch (RuntimeException e) {
                logger.warn("命令 /" + command + " 已被其他插件注册，跳过");
            }
        }
    }

    private void execute(String command, Object source) {
        if (!platform.isPlayer(source)) {
            platform.sendMessage(source, languageManager.getMessage("command.player_only"));
            return;
        }

        String targetServerName = serverCommands.get(command);
        if (targetServerName == null) {
            platform.sendMessage(source, languageManager.getMessage("command.server_not_found"));
            return;
        }

        if (!platform.connectPlayer(source, targetServerName)) {
            platform.sendMessage(source, languageManager.getMessage("command.server_offline"));
            return;
        }

        platform.sendMessage(source,
                languageManager.getMessage("command.teleporting", Map.of("server", targetServerName)));
    }
}