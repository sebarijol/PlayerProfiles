package me.sebarijol15.playerprofiles.Inventories;

import me.clip.placeholderapi.PlaceholderAPI;
import me.sebarijol15.playerprofiles.Util.FileManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RankupGUI implements InventoryHolder {
    private Inventory inventory;
    public RankupGUI(Player player) {
        this.inventory = Bukkit.createInventory(player, 9,"Opciones de Rango");
        addItems(player);
        InventoryItem.fillEmptySlots(this.getInventory());
    }
    public void addItems(Player player) {
        new InventoryItem(Material.ARROW)
                .setDisplayName("&#ccccccVolver al Perfil")
                .setLocalizedName("go_back_to_profile")
                .addToInventory(this.getInventory(),0);

        new InventoryItem(Material.COAL_BLOCK)
                .setDisplayName("&#ccccccSiguiente rango")
                .setLocalizedName("static_item")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#ccccccEl siguiente rango es: &#bda253%luckperms_next_group_on_track_rangos%"))
                .addToInventory(this.getInventory(),3);

        ConfigurationSection nextRank = FileManager.getConfig().getConfigurationSection(PlaceholderAPI.setPlaceholders(player, "%luckperms_next_group_on_track_rangos%"));

        new InventoryItem(Material.GREEN_CONCRETE)
                .setDisplayName("&#ccccccComprar")
                .setLocalizedName("buy_next_rank")
                .addLoreLine("&#ccccccComprar el siguiente rango te costaría &#bda253$" + nextRank.getInt("price") + " Reales&#cccccc.")
                .addToInventory(this.getInventory(), 4);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
