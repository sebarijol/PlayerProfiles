package me.sebarijol15.playerprofiles.Inventories;

import me.clip.placeholderapi.PlaceholderAPI;
import me.sebarijol15.playerprofiles.Util.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RankupGUI implements InventoryHolder {
    private Player player;
    private Inventory inventory;
    public RankupGUI(Player player) {
        this.player = player;
        this.inventory = Bukkit.createInventory(player, 9,"Opciones de Rango");
        addItems(player);
    }
    public void addItems(Player player) {
        new InventoryItem(Material.ARROW)
                .setDisplayName("&#bda253Volver")
                .setLocalizedName("go_back")
                .addToInventory(inventory,0);

        new InventoryItem(Material.COAL_BLOCK)
                .setDisplayName("&#bda253Siguiente rango")
                .setLocalizedName("static_item")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#c9b67dEl siguiente rango es: &#bda253%luckperms_next_group_on_track_rangos%"))
                .addToInventory(inventory,3);

        ConfigurationSection nextRank = FileManager.getConfig().getConfigurationSection(PlaceholderAPI.setPlaceholders(player, "%luckperms_next_group_on_track_rangos%"));

        new InventoryItem(Material.GREEN_CONCRETE)
                .setDisplayName("&#bda253Comprar")
                .setLocalizedName("buy_next_rank")
                .addLoreLine("&#c9b67dComprar el siguiente rango te costaría &#bda253$" + nextRank.getInt("price") + " Reales&#c9b67d.")
                .addToInventory(inventory, 4);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
