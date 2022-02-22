package org.lumenk.planet.flycontrol.planetuniverseflycontroller.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.lumenk.planet.flycontrol.planetuniverseflycontroller.contents.GeneralConfigProtoType;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!GeneralConfigProtoType.getGenConfig().effective())return;
        event.getPlayer().setAllowFlight(false);
    }
}
