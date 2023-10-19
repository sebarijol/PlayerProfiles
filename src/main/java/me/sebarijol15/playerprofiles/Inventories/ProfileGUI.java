package me.sebarijol15.playerprofiles.Inventories;

import me.clip.placeholderapi.PlaceholderAPI;
import me.sebarijol15.playerprofiles.Util.PlayerDataHandler;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import java.io.File;

public class ProfileGUI implements InventoryHolder {
    private Inventory inventory;

    public ProfileGUI(Player player) {
        this.inventory = Bukkit.createInventory(player,9,"Perfil de " + player.getDisplayName());
        addItems(player);
        InventoryItem.fillEmptySlots(this.getInventory());
    }

    public void addItems(Player player) {
        new InventoryItem(Material.PLAYER_HEAD)
                .setDisplayName(player.getDisplayName())
                .setLocalizedName("static_item")
                .addToInventory(this.getInventory(),0);

        new InventoryItem(Material.GOLD_BLOCK)
                .setDisplayName("&6Rango")
                .setLocalizedName("open_rank_menu")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&fTu rango actual es &e%playerprofiles_rank%"))
                .addLoreLine("&fClic para ver opciones.")
                .addToInventory(this.getInventory(), 4);

        Material material = getPlayerGender(player) ? Material.PINK_CONCRETE : Material.BLUE_CONCRETE;
        new InventoryItem(material)
                .setDisplayName("&6Género")
                .setLocalizedName("switch_gender")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player, "&fTu género actual es &e%playerprofiles_gender%."))
                .addLoreLine("&fClic para cambiar.")
                .addToInventory(this.getInventory(),3);
    }

    private boolean getPlayerGender(Player player) {
        File playersFolder = PlayerDataHandler.getPlayersFolder();
        File playerFile = new File(playersFolder, player.getUniqueId() + ".yml");
        YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        return playerConfig.getBoolean("isFemale");
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
