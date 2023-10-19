package me.sebarijol15.playerprofiles.Util;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {
    private Economy economy;

    public EconomyManager() {
        if (!setupEconomy()) {
            Bukkit.getLogger().severe("Vault or an economy plugin was not found.");
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        }
        return (economy != null);
    }

    public double getPlayerBalance(Player player) {
        if (economy != null) {
            return economy.getBalance(player);
        }
        return 0.0;
    }

    public boolean hasEnough(Player player, double amount) {
        if (economy != null) {
            return economy.getBalance(player) >= amount;
        }
        return false;
    }

    public EconomyResponse withdraw(Player player, double amount) {
        if (economy != null) {
            return economy.withdrawPlayer(player, amount);
        }
        return null;
    }
}