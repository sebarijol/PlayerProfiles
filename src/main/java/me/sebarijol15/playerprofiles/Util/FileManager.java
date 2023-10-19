package me.sebarijol15.playerprofiles.Util;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.track.Track;
import net.luckperms.api.track.TrackManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileManager {
    private final JavaPlugin plugin;
    private static File configFile;
    private static FileConfiguration config;

    public FileManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(),"ranks.yml");
        this.config = YamlConfiguration.loadConfiguration(configFile);
        listRanksInTrack("rangos");
    }

    /**
     * Sets up the configuration.
     */
    public void setupConfig() {
        // Check if the config file exists
        if (!configFile.exists()) {
            // Create parent directories if they don't exist
            configFile.getParentFile().mkdirs();
            // Save the resource to the config file
            plugin.saveResource("ranks.yml", false);
        }
    }

    /**
     * Returns the configuration file.
     *
     * @return The configuration file.
     */
    public static FileConfiguration getConfig() {
        return config;
    }

    /**
     * Lists ranks in a track.
     *
     * @param trackName the name of the track
     */
    public void listRanksInTrack(String trackName) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        TrackManager trackManager = luckPerms.getTrackManager();
        Track track = trackManager.getTrack(trackName);

        if (track != null) {
            List<String> groups = track.getGroups();

            for (int i = 1; i < groups.size(); i++) {
                String group = groups.get(i);
                if (config.getConfigurationSection(group) == null) {
                    ConfigurationSection rank = config.createSection(group);
                    rank.set("price", 50);
                }
            }
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
