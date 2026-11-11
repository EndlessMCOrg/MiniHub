package org.endlessmc.minihub;

import org.endlessmc.minihub.platform.MiniLogger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager {

    private static final String DEFAULT_LANGUAGE = "zh_cn";
    private static final String RESOURCE_DIRECTORY = "lang";
    private static final List<String> BUNDLED_LANGUAGES = List.of("zh_cn", "zh_hk", "zh_tw", "en_us");

    private final Path langDirectory;
    private final String language;
    private final MiniLogger logger;
    private Map<String, Object> messages = new HashMap<>();

    public LanguageManager(Path dataDirectory, String language, MiniLogger logger) {
        this.langDirectory = dataDirectory.resolve(RESOURCE_DIRECTORY);
        this.language = language;
        this.logger = logger;
    }

    public boolean loadLanguage() {
        try {
            Files.createDirectories(langDirectory);
            copyMissingLanguageFiles();

            Path languageFile = langDirectory.resolve(language + ".yml");
            if (Files.notExists(languageFile)) {
                logger.warn("语言文件 " + language + " 不存在，回退到默认语言 " + DEFAULT_LANGUAGE);
                languageFile = langDirectory.resolve(DEFAULT_LANGUAGE + ".yml");
            }

            if (Files.exists(languageFile)) {
                try (InputStream input = Files.newInputStream(languageFile)) {
                    messages = loadMessages(input);
                }
            } else {
                messages = loadBundledDefaultMessages();
                if (messages == null) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            logger.error("无法加载语言文件", e);
            return false;
        }
    }

    private void copyMissingLanguageFiles() throws IOException {
        for (String bundledLanguage : BUNDLED_LANGUAGES) {
            String fileName = bundledLanguage + ".yml";
            Path targetFile = langDirectory.resolve(fileName);

            if (Files.exists(targetFile)) {
                continue;
            }

            try (InputStream input = getClass().getResourceAsStream("/" + RESOURCE_DIRECTORY + "/" + fileName)) {
                if (input == null) {
                    continue;
                }
                Files.copy(input, targetFile);
                logger.info("已复制语言文件: " + fileName);
            }
        }
    }

    private Map<String, Object> loadBundledDefaultMessages() throws IOException {
        try (InputStream input = getClass().getResourceAsStream(
                "/" + RESOURCE_DIRECTORY + "/" + DEFAULT_LANGUAGE + ".yml")) {
            if (input == null) {
                logger.error("插件内置语言文件 " + DEFAULT_LANGUAGE + " 不存在");
                return null;
            }
            return loadMessages(input);
        }
    }

    private Map<String, Object> loadMessages(InputStream input) {
        Map<String, Object> loaded = new Yaml().load(input);
        return loaded != null ? loaded : new HashMap<>();
    }

    public String getMessage(String key) {
        return getMessageString(key);
    }

    public String getMessage(String key, Map<String, String> replacements) {
        return applyReplacements(getMessageString(key), replacements);
    }

    private String getMessageString(String key) {
        Object message = messages.get(key);
        return message != null ? message.toString() : "§cMessage not found: " + key;
    }

    private String applyReplacements(String message, Map<String, String> replacements) {
        if (replacements == null) {
            return message;
        }

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            message = message.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return message;
    }
}