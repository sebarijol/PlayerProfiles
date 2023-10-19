package me.sebarijol15.playerprofiles.Events;

import me.clip.placeholderapi.PlaceholderAPI;
import me.sebarijol15.playerprofiles.Inventories.ProfileGUI;
import me.sebarijol15.playerprofiles.Inventories.RankupGUI;
import me.sebarijol15.playerprofiles.Util.EconomyManager;
import me.sebarijol15.playerprofiles.Util.FileManager;
import me.sebarijol15.playerprofiles.Util.HexUtil;
import me.sebarijol15.playerprofiles.Util.PlayerDataHandler;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.track.Track;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.io.IOException;

public class InventoryEvents implements Listener {
    // Reference to the EconomyManager used for handling player balances
    private EconomyManager economyManager;

    // Reference to the LuckPerms instance for permission management
    private LuckPerms luckPerms;
    private HexUtil hexUtil = new HexUtil();

    /**
     * Constructs an EventManager object with the specified EconomyManager.
     *
     * @param economyManager The EconomyManager used for handling player balances.
     */
    public InventoryEvents(EconomyManager economyManager) {
        this.economyManager = economyManager;

        // Initialize the LuckPerms instance
        this.luckPerms = LuckPermsProvider.get();
    }

    /**
     * Handles the click event on an inventory.
     *
     * @param event The InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Check if the clicked item is null
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        String clickedItemLocalizedName = event.getCurrentItem().getItemMeta().getLocalizedName();

        // Handle different cases based on the clicked item localized name
        switch (clickedItemLocalizedName) {
            case "open_rank_menu":
                // Open the rankup GUI
                RankupGUI rankupGUI = new RankupGUI(player);
                player.openInventory(rankupGUI.getInventory());
                break;
            case "static_item":
                event.setCancelled(true);
                break;
            case "switch_gender":
                // Toggle player's gender
                boolean gender = getPlayerGender(player);
                gender = !gender;
                String playerGender = gender ? hexUtil.translateHexCodes("&#ff57fc>MUJER") : "&#4297ffHOMBRE";
                player.sendMessage(hexUtil.translateHexCodes("&#ccccccTu género se ha establecido a " + playerGender + "&#cccccc."));
                savePlayerGender(player, gender);
                player.closeInventory();
                break;
            case "go_back_to_profile":
                // Go back to the profile GUI
                ProfileGUI profileGUI = new ProfileGUI(player);
                player.openInventory(profileGUI.getInventory());
                break;
            case "buy_next_rank":
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                Track track = luckPerms.getTrackManager().getTrack("rangos");
                ConfigurationSection nextRank = FileManager.getConfig().getConfigurationSection(PlaceholderAPI.setPlaceholders(player,"%luckperms_next_group_on_track_rangos%"));
                double requiredAmount = nextRank.getInt("price");

                // Check if the player has enough money to buy the next rank
                if (!economyManager.hasEnough(player, requiredAmount)) {
                    player.sendMessage(hexUtil.translateHexCodes("&#cccccc¡No tienes suficiente dinero para comprarte el siguiente rango!"));
                    player.sendMessage(hexUtil.translateHexCodes("&#ccccccTe faltan &#bda253" + requiredAmount + " Reales&#cccccc."));
                    event.setCancelled(true);
                    return;
                }

                // Withdraw the required amount from the player's balance
                economyManager.withdraw(player, requiredAmount);

                // Promote the player to the next rank on the track
                track.promote(user, ImmutableContextSet.empty());
                luckPerms.getUserManager().saveUser(user);

                // Notify the player about the rank purchase
                String newRankMessage = PlaceholderAPI.setPlaceholders(player,"&#ccccccTu nuevo rango es &#bda253%playerprofiles_rank%.&#cccccc>");
                player.sendMessage(hexUtil.translateHexCodes(newRankMessage));
                player.sendMessage("&#cccccc¡Has pagado &#bda253$" + requiredAmount + " Reales &#ccccccy has ascendido de rango!");

                // Notify other players about the rank purchase
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (!onlinePlayer.equals(player)) {
                        onlinePlayer.sendMessage(PlaceholderAPI.setPlaceholders(player, hexUtil.translateHexCodes("&#cccccc¡El jugador &#bda253" + player.getName() + " &#ccccccha comprado el rango &#bda253%playerprofiles_rank% &#cccccc por &#bda253" + requiredAmount + " Reales&#cccccc!")));
                    }
                }

                // Save the new rank to the file
                savePlayerRank(player,PlaceholderAPI.setPlaceholders(player,"%luckperms_current_group_on_track_rangos%"));

                // Open the profile GUI
                profileGUI = new ProfileGUI(player);
                player.openInventory(profileGUI.getInventory());
                break;
        }
    }

    /**
     * Retrieves the gender of a player from their player configuration file.
     *
     * @param player The player for which to retrieve the gender.
     * @return true if the player is female, false if the player is male.
     */
    private boolean getPlayerGender(Player player) {
        // Get the file for the player's configuration
        File playerFile = new File(PlayerDataHandler.getPlayersFolder(), player.getUniqueId() + ".yml");

        // Load the player's configuration from the file
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        // Retrieve the gender from the player's configuration
        return playerConfig.getBoolean("isFemale");
    }

    /**
     * Saves the gender of a player in their player file.
     *
     * @param player The player whose gender is being saved.
     * @param gender The gender value to be saved (true for female, false for male).
     */
    private void savePlayerGender(Player player, boolean gender) {
        // Get the folder where player files are stored
        File playersFolder = PlayerDataHandler.getPlayersFolder();

        // Create a file for the player using their UUID as the filename
        File playerFile = new File(playersFolder, player.getUniqueId() + ".yml");

        // Load the player's configuration from the file
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        // Set the "isFemale" key in the player's configuration to the gender value
        playerConfig.set("isFemale", gender);

        try {
            // Save the player's configuration to the file
            playerConfig.save(playerFile);
        } catch (IOException e) {
            // Log a severe error message indicating if an error occurs while saving the file
            Bukkit.getLogger().severe("An error occurred while saving the player's gender.");
        }
    }

    /**
     * Saves the given player's rank to their player file.
     *
     * @param player The player whose rank to save.
     * @param rank   The rank to save.
     */
    private void savePlayerRank(Player player, String rank) {
        // Get the folder where player data is stored
        File playersFolder = PlayerDataHandler.getPlayersFolder();

        // Create a file object for the player's data file
        File playerFile = new File(playersFolder, player.getUniqueId() + ".yml");

        // Load the player's data file into a YAML configuration
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        // Set the player's rank in the configuration
        playerConfig.set("rank", rank);

        try {
            // Save the configuration to the player's data file
            playerConfig.save(playerFile);
        } catch (IOException e) {
            // Log a severe error message indicating that an error occurred while saving the player's rank
            Bukkit.getLogger().severe("An error occurred while saving the player's rank.");
        }
    }
}