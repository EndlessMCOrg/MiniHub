package org.endlessmc.minihub.platform;

import java.nio.file.Path;

public interface MiniHubPlatform {

    MiniLogger logger();

    Path dataDirectory();

    boolean isPlayer(Object source);

    void sendMessage(Object source, String message);

    boolean connectPlayer(Object player, String serverName);

    void registerCommand(String name, CommandHandler handler);

    @FunctionalInterface
    interface CommandHandler {
        void execute(Object source);
    }
}