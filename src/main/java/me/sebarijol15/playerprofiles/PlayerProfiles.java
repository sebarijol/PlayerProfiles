package me.sebarijol15.playerprofiles;

import me.sebarijol15.playerprofiles.Events.InventoryEvents;
import me.sebarijol15.playerprofiles.Events.PlayerEvents;
import me.sebarijol15.playerprofiles.Inventories.ProfileGUI;
import me.sebarijol15.playerprofiles.Util.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PlayerProfiles extends JavaPlugin implements CommandExecutor {
    private static Player targetPlayer;
    @Override
    public void onEnable() {
        File playersFolder = new File(getDataFolder(), "Players");
        PlayerDataHandler playerDataHandler = new PlayerDataHandler(playersFolder);

        new PlaceholderManager(this).register();

        getServer().getPluginManager().registerEvents(new PlayerEvents(playerDataHandler), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(new EconomyManager(), getTargetPlayer()), this);

        try {
            Bukkit.getConsoleSender().sendMessage(getTargetPlayer().toString());
        } catch (NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage("Player not found.");
        }

        FileManager fileManager = new FileManager(this);
        fileManager.setupConfig();

        getCommand("perfil").setExecutor(this);

        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
        }
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;
        targetPlayer = null;

        if (strings.length > 0) {
            // Get the specified player
            targetPlayer = Bukkit.getPlayer(strings[0]);
        }

        ProfileGUI profileGUI;
        if (targetPlayer != null) {
            // Open the profile of the specified player
            profileGUI = new ProfileGUI(targetPlayer);
        } else {
            // Open the profile of the command sender
            profileGUI = new ProfileGUI(player);
        }

        player.openInventory(profileGUI.getInventory());
        return true;
    }

    public static Player getTargetPlayer() {
        return targetPlayer;
    }
}
