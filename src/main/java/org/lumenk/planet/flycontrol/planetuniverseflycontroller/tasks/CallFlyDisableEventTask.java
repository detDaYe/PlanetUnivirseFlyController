package org.lumenk.planet.flycontrol.planetuniverseflycontroller.tasks;

import org.bukkit.Bukkit;
import org.lumenk.planet.flycontrol.planetuniverseflycontroller.contents.GeneralConfigProtoType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CallFlyDisableEventTask implements Runnable{
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(p -> p.setAllowFlight(false));
    }

    private static long toNext(){
        final GeneralConfigProtoType config = GeneralConfigProtoType.getGenConfig();
        if(config.effective())return 0L;

        final LocalTime now = LocalTime.now();
        if(config.getBattlePeriodStartAt().isAfter(now))
            return Duration.between(now, config.getBattlePeriodStartAt()).toMillis();
        else if(config.getBattlePeriodEndAt().isBefore(now)){
            final Duration d1 = Duration.between(config.getBattlePeriodStartAt(), config.getBattlePeriodEndAt());
            final Duration d2 = Duration.between(config.getBattlePeriodEndAt(), now);
            final Duration r = d1.plus(d2);

            return r.toMillis();
        }
        return 0;

    }
}
