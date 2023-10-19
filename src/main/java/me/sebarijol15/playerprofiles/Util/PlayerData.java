package me.sebarijol15.playerprofiles.Util;

import java.util.UUID;

/**
 * Represents the data associated with a player.
 */
public class PlayerData {

    private String playerName;
    private UUID playerUniqueId;
    private boolean female;
    private String currentGroup;

    /**
     * Get the unique ID of the player.
     *
     * @return The player's unique ID.
     */
    public UUID getPlayerUniqueId() {
        return playerUniqueId;
    }

    /**
     * Set the unique ID of the player.
     *
     * @param playerUniqueId The unique ID of the player.
     */
    public void setPlayerUniqueId(UUID playerUniqueId) {
        this.playerUniqueId = playerUniqueId;
    }

    /**
     * Check if the player is female.
     *
     * @return True if the player is female, false otherwise.
     */
    public boolean isFemale() {
        return female;
    }

    /**
     * Set the player's gender.
     *
     * @param female True if the player is female, false otherwise.
     */
    public void setFemale(boolean female) {
        this.female = female;
    }

    /**
     * Get the name of the player.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Set the name of the player.
     *
     * @param playerName The player's name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Set the current group of the player.
     *
     * @param currentGroup The player's current group.
     */
    public void setCurrentGroup(String currentGroup) {
        this.currentGroup = currentGroup;
    }

    /**
     * Get the current group of the player.
     *
     * @return The player's current group.
     */
    public String getCurrentGroup() {
        return currentGroup;
    }
}