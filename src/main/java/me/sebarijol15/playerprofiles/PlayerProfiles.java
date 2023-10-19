package me.sebarijol15.playerprofiles;

import me.sebarijol15.playerprofiles.Events.InventoryEvents;
import me.sebarijol15.playerprofiles.Events.PlayerEvents;
import me.sebarijol15.playerprofiles.Util.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PlayerProfiles extends JavaPlugin {
    @Override
    public void onEnable() {
        File playersFolder = new File(getDataFolder(), "Players");
        PlayerDataHandler playerDataHandler = new PlayerDataHandler(playersFolder);

        new PlaceholderManager(this).register();

        getServer().getPluginManager().registerEvents(new PlayerEvents(playerDataHandler), this);

        FileManager fileManager = new FileManager(this);
        fileManager.setupConfig();

        getServer().getPluginManager().registerEvents(new InventoryEvents(new EconomyManager()), this);
        this.getCommand("profile").setExecutor(new ProfileCommand());

        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
        }
    }
}
