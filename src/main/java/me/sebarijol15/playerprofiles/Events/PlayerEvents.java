package me.sebarijol15.playerprofiles.Events;

import me.sebarijol15.playerprofiles.PlayerData;
import me.sebarijol15.playerprofiles.Util.PlayerDataHandler;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    public PlayerData playerData = new PlayerData();
    private PlayerDataHandler playerDataHandler;

    public PlayerEvents(PlayerDataHandler playerDataHandler) {
        this.playerDataHandler = playerDataHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        playerData.setPlayerName(player.getName());
        playerData.setPlayerUniqueId(player.getUniqueId());
        playerData.setFemale(false);

        playerDataHandler.savePlayerData(playerData);
    }
}
