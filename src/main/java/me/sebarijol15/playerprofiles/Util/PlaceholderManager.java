package me.sebarijol15.playerprofiles.Util;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.sebarijol15.playerprofiles.PlayerProfiles;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class PlaceholderManager extends PlaceholderExpansion {
    private final PlayerProfiles plugin;

    public PlaceholderManager(PlayerProfiles plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "playerprofiles";
    }

    @Override
    public @NotNull String getAuthor() {
        return "example";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Retrieves information about a player from their player file.
     *
     * @param player The player whose information is being retrieved.
     * @param params The parameter specifying the type of information to retrieve.
     * @return The requested information, or null if the parameter is invalid.
     */
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        // Get the player's file
        File playerFile = new File(PlayerDataHandler.getPlayersFolder(), player.getUniqueId() + ".yml");

        // Load the configuration
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        // Retrieve the player's gender
        if (params.equalsIgnoreCase("gender")) {
            return playerConfig.getBoolean("isFemale") ? "Mujer" : "Hombre";
        }

        // Retrieve the player's rank
        if (params.equalsIgnoreCase("rank")) {
            return PlaceholderAPI.setPlaceholders(player, "%luckperms_current_group_on_track_rangos%");
        }

        // Retrieve the player's next rank
        if (params.equalsIgnoreCase("next_rank")) {
            return PlaceholderAPI.setPlaceholders(player, "%luckperms_next_group_on_track_rangos%");
        }

        // Invalid parameter, return null
        return null;
    }
}
