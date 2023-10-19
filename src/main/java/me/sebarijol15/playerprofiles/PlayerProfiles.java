package me.sebarijol15.playerprofiles;

import me.sebarijol15.playerprofiles.Events.InventoryEvents;
import me.sebarijol15.playerprofiles.Events.PlayerEvents;
import me.sebarijol15.playerprofiles.Inventories.ProfileGUI;
import me.sebarijol15.playerprofiles.Util.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PlayerProfiles extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        File playersFolder = new File(getDataFolder(), "Players");
        PlayerDataHandler playerDataHandler = new PlayerDataHandler(playersFolder);

        new PlaceholderManager(this).register();

        getServer().getPluginManager().registerEvents(new PlayerEvents(playerDataHandler), this);

        FileManager fileManager = new FileManager(this);
        fileManager.setupConfig();

        getServer().getPluginManager().registerEvents(new InventoryEvents(new EconomyManager()), this);
        this.getCommand("profile").setExecutor(this);

        if (!playersFolder.exists()) {
            playersFolder.mkdirs();
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        ProfileGUI profileGUI = new ProfileGUI(player);
        player.openInventory(profileGUI.getInventory());
        return true;
    }
}
