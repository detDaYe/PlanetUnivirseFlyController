package org.lumenk.planet.flycontrol.planetuniverseflycontroller;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.lumenk.planet.flycontrol.planetuniverseflycontroller.contents.GeneralConfigProtoType;

import java.util.List;

public class ConfigurationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /*
         * mpvp reload
         */

        if(args.length == 0){
            sender.sendMessage("잘못된 입력입니다. 상세한 사항은 문서를 참고하여 주십시오");
            return false;
        }

        GeneralConfigProtoType config = GeneralConfigProtoType.getGenConfig();

        switch (args[0]){
            case "load" -> config.load();

            case "save" -> config.save();

            case "override" -> {
                if(args.length == 1){
                    if(config.isNowOverriding()){
                        sender.sendMessage("overriding... ENABLED");
                        sender.sendMessage("value... " + config.isOverridingValue());
                    }else{
                        sender.sendMessage("overriding... disabled");
                    }

                    return true;
                }else{
                    switch (args[1]){
                        case "on" ->{
                            config.setNowOverriding(true);
                            sender.sendMessage("Overriding is now ENABLED");
                            sender.sendMessage("overriding value : " + config.isOverridingValue());
                            return true;
                        }

                        case "off" -> {
                            config.setNowOverriding(false);
                            sender.sendMessage("Overriding is now disabled");
                            sender.sendMessage("overriding value : " + config.isOverridingValue());
                            return true;
                        }

                        case "true" -> {
                            config.setOverridingValue(true);
                            sender.sendMessage("overriding value is now set to true");
                            return true;
                        }

                        case "false" -> {
                            config.setOverridingValue(false);
                            sender.sendMessage("overriding value is now set to false");
                            return true;
                        }

                        default -> {
                            sender.sendMessage("Invalid input");
                            return false;
                        }
                    }
                }

                //case "on"
            }

            case "master" -> {
                if(args.length == 1){
                    if(config.isEnabled()){
                        sender.sendMessage("본 플러그인에서 컨트롤러에서 플라이를 제어중입니다");
                    }else{
                        sender.sendMessage("본 플러그인은 더 이상 플라이를 제어하지 않습니다");
                    }

                    return true;
                }else{
                    switch (args[1]){
                        case "on" -> {
                            config.setEnabled(true);
                            sender.sendMessage("본 플러그인에서 컨트롤러에서 플라이를 제어중입니다");
                            return true;
                        }

                        case "off" -> {
                            config.setEnabled(false);
                            sender.sendMessage("본 플러그인은 더 이상 플라이를 제어하지 않습니다");
                            return true;
                        }

                        default -> {
                            sender.sendMessage("Invalid input");
                            return false;
                        }
                    }
                }
            }

            case "worlds" ->{
                if(args.length == 1){
                    sender.sendMessage("Invalid Input");
                    return false;
                }

                switch (args[1]){
                    case "list" -> {
                        List<World> effectiveWorlds = config.getEffectiveWorlds();
                        sender.sendMessage("===이하의 월드에서는 정해진 시간에 플라이가 비활성화됩니다===");
                        effectiveWorlds.forEach(world -> sender.sendMessage("- " + world.getName()));
                        return true;
                    }

                    case "add" -> {
                        if(args.length == 2){
                            sender.sendMessage("추가할 월드의 이름을 입력하여 주십시오");
                            return false;
                        }

                        String worldName = args[2];
                        World world = Bukkit.getWorld(worldName);

                        if(null == world){
                            sender.sendMessage("주어진 월드명 " + worldName + "을 찾지 못하였습니다");
                            return false;
                        }

                        if(config.getEffectiveWorlds().contains(world)){
                            sender.sendMessage("주어진 월드 " + world.getName() + "은(는) 이미 특정 시간대에 PvP가 허용되는 월드입니다");
                            return false;
                        }

                        config.getEffectiveWorlds().add(world);
                        sender.sendMessage(world.getName() + "는 이제 특정 시간대에 플라이가 비활성화 됩니다");
                        return true;

                    } case "remove" -> {
                        if(args.length == 2){
                            sender.sendMessage("제거할 월드의 이름을 입력하여 주십시오");
                            return false;
                        }

                        String worldName = args[2];
                        World world = Bukkit.getWorld(worldName);

                        if(null == world){
                            sender.sendMessage("주어진 월드명 " + worldName + "을 찾지 못하였습니다");
                            return false;
                        }

                        if(!config.getEffectiveWorlds().contains(world)){
                            sender.sendMessage("주어진 월드 " + world.getName() + "은(는) 특정 시간대에 플라이가 비뢍설화되는 월드 목록에 있지 아니합니다");
                            return false;
                        }

                        config.getEffectiveWorlds().remove(world);
                        sender.sendMessage("주어진 월드 " + world.getName() + "은(는) 더이상 특정 시간대에 플라이가 비활성화되지 아니합니다");
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
