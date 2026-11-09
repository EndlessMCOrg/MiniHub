package org.endlessmc.minihub;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class LanguageManager {

    private static final String DEFAULT_LANGUAGE = "zh_cn";
    private static final String RESOURCE_DIRECTORY = "lang";

    private final Path langDirectory;
    private final String language;
    private final Logger logger;
    private Map<String, Object> messages = new HashMap<>();

    public LanguageManager(Path dataDirectory, String language, Logger logger) {
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
                logger.warn("语言文件 {} 不存在，回退到默认语言 {}", language, DEFAULT_LANGUAGE);
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

    private void copyMissingLanguageFiles() throws IOException, URISyntaxException {
        for (String resourceFile : getResourceLanguageFiles()) {
            String fileName = resourceFile.substring(resourceFile.lastIndexOf('/') + 1);
            Path targetFile = langDirectory.resolve(fileName);

            if (Files.exists(targetFile)) {
                continue;
            }

            try (InputStream input = getClass().getResourceAsStream("/" + resourceFile)) {
                if (input == null) {
                    continue;
                }
                Files.copy(input, targetFile);
                logger.info("已复制语言文件: {}", fileName);
            }
        }
    }

    private List<String> getResourceLanguageFiles() throws IOException, URISyntaxException {
        List<String> langFiles = new ArrayList<>();
        ClassLoader classLoader = getClass().getClassLoader();

        Enumeration<URL> resources = classLoader.getResources(RESOURCE_DIRECTORY);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();

            if ("jar".equals(resource.getProtocol())) {
                String path = resource.getPath();
                String jarPath = path.substring(5, path.indexOf('!'));
                try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                    jar.stream()
                            .filter(entry -> !entry.isDirectory())
                            .map(JarEntry::getName)
                            .filter(name -> name.startsWith(RESOURCE_DIRECTORY + "/") && name.endsWith(".yml"))
                            .forEach(langFiles::add);
                }
            } else if ("file".equals(resource.getProtocol())) {
                try (Stream<Path> files = Files.list(Path.of(resource.toURI()))) {
                    files.filter(file -> file.toString().endsWith(".yml"))
                            .forEach(file -> langFiles.add(RESOURCE_DIRECTORY + "/" + file.getFileName()));
                }
            }
        }

        if (langFiles.isEmpty()) {
            langFiles.add(RESOURCE_DIRECTORY + "/" + DEFAULT_LANGUAGE + ".yml");
        }

        return langFiles;
    }

    private Map<String, Object> loadBundledDefaultMessages() throws IOException {
        try (InputStream input = getClass().getResourceAsStream(
                "/" + RESOURCE_DIRECTORY + "/" + DEFAULT_LANGUAGE + ".yml")) {
            if (input == null) {
                logger.error("插件内置语言文件 {} 不存在", DEFAULT_LANGUAGE);
                return null;
            }
            return loadMessages(input);
        }
    }

    private Map<String, Object> loadMessages(InputStream input) {
        Map<String, Object> loaded = new Yaml().load(input);
        return loaded != null ? loaded : new HashMap<>();
    }

    public Component getMessage(String key) {
        return LegacyComponentSerializer.legacySection().deserialize(getMessageString(key));
    }

    public Component getMessage(String key, Map<String, String> replacements) {
        return LegacyComponentSerializer.legacySection()
                .deserialize(applyReplacements(getMessageString(key), replacements));
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
