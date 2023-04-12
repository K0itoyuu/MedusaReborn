package com.gladurbad.medusa.listener;

import com.gladurbad.medusa.Medusa;
import com.gladurbad.medusa.util.anticheat.AlertUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class JoinQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        Medusa.INSTANCE.getPlayerDataManager().add(event.getPlayer());

        if (event.getPlayer().hasPermission("medusa.alerts")) {
            AlertUtil.toggleAlerts(Medusa.INSTANCE.getPlayerDataManager().getPlayerData(event.getPlayer()));
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        Medusa.INSTANCE.getPlayerDataManager().remove(event.getPlayer());
    }
}
