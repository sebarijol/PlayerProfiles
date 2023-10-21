package me.sebarijol15.playerprofiles.Inventories;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class StatisticsGUI implements InventoryHolder {
    private Inventory inventory;
    public StatisticsGUI(Player player) {
        this.inventory = Bukkit.createInventory(player, 9,"Estadísticas del jugador " + player.getDisplayName());
        addItems(player);
    }

    public void addItems(Player player) {
        new InventoryItem(Material.ARROW)
                .setDisplayName("&#bda253Volver")
                .setLocalizedName("go_back")
                .addToInventory(inventory,0);

        new InventoryItem(Material.PLAYER_HEAD)
                .setDisplayName("&#bda253" + player.getDisplayName())
                .setLocalizedName("static_item")
                .addToInventory(inventory,1);

        new InventoryItem(Material.SKELETON_SKULL)
                .setDisplayName("&#bda253Estadísticas de Batalla")
                .setLocalizedName("static_item")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#c9b67dHa matado &#bda253%statistic_player_kills% &#c9b67dmobs"))
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#c9b67dHa muerto &#bda253%statistic_deaths% &#c9b67dveces"))
                .addToInventory(inventory,2);

        new InventoryItem(Material.COMPASS)
                .setDisplayName("&#bda253Tiempo jugado")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#c9b67dTiempo jugado: &#bda253%statistic_time_played%"))
                .setLocalizedName("static_item")
                .addToInventory(inventory,3);

        new InventoryItem(Material.COMMAND_BLOCK)
                .setDisplayName("&#bda253Herramientas de Admin")
                .addLoreLine(PlaceholderAPI.setPlaceholders(player,"&#c9b67dDirección IP: &#bda253%player_ip%"))
                .setLocalizedName("static_item")
                .addToInventory(inventory,player,"playerprofiles_admin");
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
