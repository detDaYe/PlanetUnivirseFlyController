package org.lumenk.planet.flycontrol.planetuniverseflycontroller.contents;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.lumenk.planet.flycontrol.planetuniverseflycontroller.utils.YamlResourceUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class GeneralConfigProtoType {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static final String MASTER_ENABLED_STR = "master.enabled";
    private static final String MASTER_NOW_OVERRIDE_STR = "master.overriding";
    private static final String MASTER_OVERRIDE_VALUE_STR = "master.overridingValue";
    private static final String BATTLE_START_STR = "battle.start";
    private static final String BATTLE_END_STR = "battle.end";
    private static final String SYSTEM_EFFECTIVE_WORDS_STR = "master.effectiveWorlds";

    @Getter
    private static final GeneralConfigProtoType genConfig = new GeneralConfigProtoType("BattleFlyConfig");

    @Getter
    @Setter
    private boolean enabled = true;

    @Getter
    @Setter
    private boolean nowOverriding = true;

    @Getter
    @Setter
    private  boolean overridingValue = true;

    @Getter
    @Setter
    private LocalTime battlePeriodStartAt;

    @Getter
    @Setter
    private LocalTime battlePeriodEndAt;

    @Getter
    private List<World> effectiveWorlds;


    private final YamlResourceUtil resource;


    public GeneralConfigProtoType(String fileName){
        resource = new YamlResourceUtil(fileName);
    }

    public boolean effective(){
        if(nowOverriding)
            return overridingValue;
        final LocalTime now = LocalTime.now();

        return now.isAfter(battlePeriodStartAt) && now.isBefore(battlePeriodEndAt);
    }
    public void load(){
        resource.load();
        YamlConfiguration configuration = resource.yamlConfiguration;

        enabled = configuration.getBoolean(MASTER_ENABLED_STR, true);
        nowOverriding = configuration.getBoolean(MASTER_NOW_OVERRIDE_STR, false);
        overridingValue = configuration.getBoolean(MASTER_OVERRIDE_VALUE_STR, true);

        String startStr = configuration.getString(BATTLE_START_STR, LocalTime.of(20,30,0).format(formatter));
        String endStr = configuration.getString(BATTLE_END_STR, LocalTime.of(22,30,0).format(formatter));

        battlePeriodStartAt = LocalTime.parse(startStr, formatter);
        battlePeriodEndAt = LocalTime.parse(endStr, formatter);

        List<String> stringList = configuration.getStringList(SYSTEM_EFFECTIVE_WORDS_STR);
        ArrayList<World> worlds = new ArrayList<>();
        stringList.forEach(s -> worlds.add(Objects.requireNonNull(Bukkit.getWorld(s))));
        effectiveWorlds = worlds;

    }

    public void save(){
        YamlConfiguration configuration = resource.yamlConfiguration;

        configuration.set(MASTER_ENABLED_STR, enabled);
        configuration.set(MASTER_NOW_OVERRIDE_STR, nowOverriding);
        configuration.set(MASTER_OVERRIDE_VALUE_STR, overridingValue);

        configuration.set(BATTLE_START_STR, battlePeriodStartAt);
        configuration.set(BATTLE_END_STR, battlePeriodEndAt);

        List<String> temp = new ArrayList<>();
        effectiveWorlds.forEach(w -> temp.add(w.getName()));
        configuration.set(SYSTEM_EFFECTIVE_WORDS_STR, temp);
        resource.save();
    }
}