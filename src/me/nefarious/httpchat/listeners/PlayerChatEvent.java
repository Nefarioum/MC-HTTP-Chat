package me.nefarious.httpchat.listeners;

import me.nefarious.httpchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static org.bukkit.Bukkit.*;

public class PlayerChatEvent implements Listener {
    private Main plugin;

    public PlayerChatEvent(Main plugin){
        this.plugin = plugin;

        getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent Event){
        Player player = Event.getPlayer();
        String Message = Event.getMessage();
        String APIKey = "#####";

        try {
            URL ConnectionURL = new URL("http://#####/api");
            HttpURLConnection HTTP = (HttpURLConnection) ConnectionURL.openConnection();

            HTTP.setRequestMethod("POST");

            HTTP.setDoOutput(true);
            HTTP.setConnectTimeout(5000);
            HTTP.setUseCaches(false);

            String JSONPreEncode = "{\"ApiKey\":\"" + APIKey + "\",\"User\":\"" + player.getName() + "\",\"Message\":\"" + Message + "\"}";
            byte[] JSONEncode = JSONPreEncode.getBytes(StandardCharsets.UTF_8);

            int length = JSONEncode.length;

            HTTP.setFixedLengthStreamingMode(length);
            HTTP.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            HTTP.connect();

            try(OutputStream OPStream = HTTP.getOutputStream()) {
                OPStream.write(JSONEncode);

                OPStream.flush();
            }

            HTTP.disconnect();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
