package me.sebarijol15.playerprofiles.Inventories;

import me.clip.placeholderapi.PlaceholderAPI;
import me.sebarijol15.playerprofiles.Util.HexUtil;
import me.sebarijol15.playerprofiles.Util.PlayerDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import java.io.File;

public class ProfileGUI implements InventoryHolder {
    private Inventory inventory;
    HexUtil hexUtil = new HexUtil();
    public ProfileGUI(Player player) {
        this.inventory = Bukkit.createInventory(player,9,"Perfil de " + player.getDisplayName());
        addItems(player);
        InventoryItem.fillEmptySlots(this.getInventory());
    }

    public void addItems(Player player) {
        new InventoryItem(Material.PLAYER_HEAD)
                .setDisplayName("&#cccccc" + player.getDisplayName())
                .setLocalizedName("static_item")
                .addToInventory(this.getInventory(),0);

        new InventoryItem(Material.GOLD_BLOCK)
                .setDisplayName(hexUtil.translateHexCodes("&#ccccccRango"))
                .setLocalizedName("open_rank_menu")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#ccccccTu rango actual es &#bda253%playerprofiles_rank%"))
                .addLoreLine("&#ccccccClic para ver opciones.")
                .addToInventory(this.getInventory(), 4);

        Material material = getPlayerGender(player) ? Material.PINK_CONCRETE : Material.BLUE_CONCRETE;
        new InventoryItem(material)
                .setDisplayName(hexUtil.translateHexCodes("&#ccccccGénero"))
                .setLocalizedName("switch_gender")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player, "&#ccccccTu género actual es &#bda253%playerprofiles_gender%."))
                .addLoreLine("&#ccccccClic para cambiar.")
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
