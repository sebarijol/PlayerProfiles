package me.sebarijol15.playerprofiles.Inventories;

import me.sebarijol15.playerprofiles.Util.HexUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class InventoryItem {
    HexUtil hexUtil = new HexUtil();
    private ItemStack itemStack;
    private String displayName;
    private String localizedName;
    private String permission;
    private List<String> lore;
    public InventoryItem(Material material) {
        this.itemStack = new ItemStack(material);
        this.lore = new ArrayList<>();
    }

    public InventoryItem setDisplayName(String displayName) {
        this.displayName = hexUtil.translateHexCodes(displayName);
        return this;
    }

    public InventoryItem setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
        return this;
    }

    public InventoryItem addLoreLine(String loreLine) {
        this.lore.add(hexUtil.translateHexCodes(loreLine));
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

    public void addToInventory(Inventory inventory, Player player, String permission) {
        if (!(player.hasPermission(permission))) {
            return;
        }
        ItemStack itemStack = toItemStack();
        if (inventory != null) {
            inventory.setItem(inventory.firstEmpty(), itemStack);
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