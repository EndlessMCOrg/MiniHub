package org.endlessmc.minihub.platform;

public interface MiniLogger {

    void info(String message);

    void warn(String message);

    void error(String message);

    void error(String message, Throwable throwable);
}