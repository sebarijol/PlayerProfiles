package me.sebarijol15.playerprofiles.Inventories;

import me.clip.placeholderapi.PlaceholderAPI;
import me.sebarijol15.playerprofiles.PlayerProfiles;
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
    private Player targetPlayer;
    private Inventory inventory;
    private HexUtil hexUtil = new HexUtil();
    public ProfileGUI(Player player) {
        this.targetPlayer = PlayerProfiles.getTargetPlayer();
        this.inventory = Bukkit.createInventory(player,9,"Perfil de " + player.getDisplayName());
        addItems(player);
    }

    public void addItems(Player player) {
        new InventoryItem(Material.PLAYER_HEAD)
                .setDisplayName("&#bda253" + player.getDisplayName())
                .addLoreLine("&#c9b67dClic para ver estadísticas.")
                .setLocalizedName("open_statistics_menu")
                .addToInventory(inventory,0);

        if (!player.equals(targetPlayer)) {
            new InventoryItem(Material.GOLD_BLOCK)
                    .setDisplayName(hexUtil.translateHexCodes("&#bda253Rango"))
                    .setLocalizedName("open_rank_menu")
                    .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#c9b67dTu rango actual es &#bda253%playerprofiles_rank%"))
                    .addLoreLine("&#c9b67dClic para ver opciones.")
                    .addToInventory(inventory, 4);

            Material material = getPlayerGender(player) ? Material.PINK_CONCRETE : Material.BLUE_CONCRETE;
            new InventoryItem(material)
                    .setDisplayName(hexUtil.translateHexCodes("&#bda253Género"))
                    .setLocalizedName("switch_gender")
                    .addLoreLine(PlaceholderAPI.setPlaceholders(player, "&#c9b67dTu género actual es &#bda253%playerprofiles_gender%."))
                    .addLoreLine("&#c9b67dClic para cambiar.")
                    .addToInventory(inventory,3);
        }
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
