package me.sebarijol15.playerprofiles.Events;

import me.sebarijol15.playerprofiles.Util.PlayerData;
import me.sebarijol15.playerprofiles.Util.PlayerDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

    // Instance of the PlayerData class to hold player data
    public PlayerData playerData = new PlayerData();

    // Instance of the PlayerDataHandler class to handle player data operations
    private PlayerDataHandler playerDataHandler;

    /**
     * Constructor for the PlayerEvents class.
     *
     * @param playerDataHandler The PlayerDataHandler object to handle player data operations.
     */
    public PlayerEvents(PlayerDataHandler playerDataHandler) {
        this.playerDataHandler = playerDataHandler;
    }

    /**
     * Event handler for when a player joins the game.
     *
     * @param event The PlayerJoinEvent object representing the player join event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Set player data properties
        playerData.setPlayerName(player.getName());
        playerData.setPlayerUniqueId(player.getUniqueId());
        playerData.setFemale(false);

        // Save player data using the player data handler
        playerDataHandler.savePlayerData(playerData);
    }
}