package me.sebarijol15.playerprofiles;

import me.sebarijol15.playerprofiles.Inventories.ProfileGUI;
import me.sebarijol15.playerprofiles.Util.PlayerDataHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {
    private PlayerDataHandler playerDataHandler;

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
