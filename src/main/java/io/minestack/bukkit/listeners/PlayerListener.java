package io.minestack.bukkit.listeners;

import io.minestack.bukkit.Zombie;
import io.minestack.doublechest.DoubleChest;
import io.minestack.doublechest.model.server.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class PlayerListener implements Listener {

    private final Zombie plugin;

    public PlayerListener(Zombie plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        updatePlayerCount();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        updatePlayerCount();
    }

    public void updatePlayerCount() {
        Server server = plugin.getMinestackServer();
        if (server == null) {
            return;
        }
        server.setPlayers(plugin.getServer().getOnlinePlayers().size());
        server.setUpdated_at(new Date(System.currentTimeMillis()));
        DoubleChest.INSTANCE.getMongoDatabase().getServerRepository().saveModel(server);
    }
}
