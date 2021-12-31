package me.nefarious.httpchat;

import me.nefarious.httpchat.listeners.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        new PlayerChatEvent(this);
    }
}
