package me.sebarijol15.playerprofiles.Util;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerDataHandler {
    private static File playersFolder;

    public PlayerDataHandler(File playersFolder) {
        this.playersFolder = playersFolder;
    }

    /**
     * Save player data to a file.
     *
     * @param playerData The player data to be saved.
     */
    public static void savePlayerData(PlayerData playerData) {
        // Create a file for the player
        File playerFile = new File(playersFolder, playerData.getPlayerUniqueId() + ".yml");

        // Get the LuckPerms instance
        LuckPerms luckPerms = LuckPermsProvider.get();

        // Get the user from LuckPerms
        User user = luckPerms.getUserManager().getUser(playerData.getPlayerUniqueId());

        // Load the player data from the file
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        // Set the player name in the config
        config.set("playerName", playerData.getPlayerName());

        // Set the player's gender in the config
        config.set("isFemale", playerData.isFemale());

        // Set the player's rank in the config
        config.set("rank", user.getPrimaryGroup());

        try {
            // Save the config to the file
            config.save(playerFile);
        } catch (IOException e) {
            // Print the stack trace if an exception occurs
            e.printStackTrace();
        }
    }

    public static File getPlayersFolder() {
        return playersFolder;
    }
}