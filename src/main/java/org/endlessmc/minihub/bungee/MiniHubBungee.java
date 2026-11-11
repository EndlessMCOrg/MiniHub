package org.endlessmc.minihub.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import org.endlessmc.minihub.MiniHubCore;

public final class MiniHubBungee extends Plugin {

    @Override
    public void onEnable() {
        new MiniHubCore(new BungeePlatform(this)).enable();
    }
}