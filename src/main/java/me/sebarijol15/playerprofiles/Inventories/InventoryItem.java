package me.sebarijol15.playerprofiles.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class InventoryItem {
    private ItemStack itemStack;
    private String displayName;
    private String localizedName;
    private List<String> lore;
    public InventoryItem(Material material) {
        this.itemStack = new ItemStack(material);
        this.lore = new ArrayList<>();
    }

    public InventoryItem setDisplayName(String displayName) {
        this.displayName = ChatColor.translateAlternateColorCodes('&',displayName);
        return this;
    }

    public InventoryItem setLocalizedName(String localizedName) {
        this.localizedName = ChatColor.translateAlternateColorCodes('&',localizedName);
        return this;
    }

    public InventoryItem addLoreLine(String loreLine) {
        this.lore.add(ChatColor.translateAlternateColorCodes('&',loreLine));
        return this;
    }

    public ItemStack toItemStack() {
        ItemMeta meta = itemStack.getItemMeta();
        if (displayName != null) {
            meta.setDisplayName(displayName);
        }
        if (localizedName != null) {
            meta.setLocalizedName(localizedName);
        }
        if (!lore.isEmpty()) {
            meta.setLore(lore);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void addToInventory(Inventory inventory, int slot) {
        ItemStack itemStack = toItemStack();
        if (inventory != null && slot >= 0 && slot < inventory.getSize()) {
            inventory.setItem(slot, itemStack);
        }
    }

    public static void fillEmptySlots(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, createEmptyItem());
            }
        }
    }

    private static ItemStack createEmptyItem() {
        return new InventoryItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .setDisplayName(" ")
                .setLocalizedName("static_item")
                .toItemStack();
    }
}