package org.lumenk.planet.flycontrol.planetuniverseflycontroller;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.lumenk.planet.flycontrol.planetuniverseflycontroller.listeners.PlayerJoinListener;
import org.lumenk.planet.flycontrol.planetuniverseflycontroller.tasks.CallFlyDisableEventTask;

public final class PlanetUniverseFlyController extends JavaPlugin {

    private static PlanetUniverseFlyController instance;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getLogger().info("플래닛 어스의 플라이 컨트롤러가  활성화 중입니다");
        getServer().getScheduler().runTaskLater(this, new CallFlyDisableEventTask(), CallFlyDisableEventTask.toNext());

        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinListener(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlanetUniverseFlyController getInstance() {
        return instance;
    }
}
