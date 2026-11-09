package org.endlessmc.minihub;

import org.slf4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConfigManager {

    private final Path configFile;
    private final Logger logger;
    private Map<String, Object> config = new HashMap<>();

    public ConfigManager(Path dataDirectory, Logger logger) {
        this.configFile = dataDirectory.resolve("config.yml");
        this.logger = logger;
    }

    public boolean loadConfig() {
        try {
            if (Files.notExists(configFile)) {
                saveDefaultConfig();
            }

            try (InputStream input = Files.newInputStream(configFile)) {
                Map<String, Object> loaded = new Yaml().load(input);
                config = loaded != null ? loaded : new HashMap<>();
            }

            return true;
        } catch (IOException | RuntimeException e) {
            logger.error("无法加载配置文件: {}", configFile, e);
            return false;
        }
    }

    private void saveDefaultConfig() throws IOException {
        Map<String, Object> defaultConfig = new LinkedHashMap<>();
        defaultConfig.put("language", "zh_cn");

        Map<String, Object> servers = new LinkedHashMap<>();
        servers.put("lobby", Map.of("commands", Arrays.asList("hub", "lobby")));
        servers.put("survival", Map.of("commands", Arrays.asList("survival", "srv")));
        servers.put("creative", Map.of("commands", Arrays.asList("creative", "crea")));
        defaultConfig.put("servers", servers);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(2);

        String header = "# MiniHub 插件配置文件\n\n"
                + "# 使用的语言\n"
                + "# 语言文件位于 plugins/MiniHub/lang/ 目录下\n\n"
                + "# 服务器命令配置\n"
                + "# 格式: 服务器名称 -> 命令列表\n";

        String content = header + new Yaml(options).dump(defaultConfig);
        Files.writeString(configFile, content, StandardCharsets.UTF_8);
    }

    public String getLanguage() {
        Object language = config.get("language");
        return language != null ? language.toString() : "zh_cn";
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getServers() {
        Object servers = config.get("servers");
        if (servers instanceof Map<?, ?>) {
            return (Map<String, Object>) servers;
        }
        return Collections.emptyMap();
    }
}
