package org.lumenk.planet.flycontrol.planetuniverseflycontroller.tabcomplters;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.lumenk.planet.flycontrol.planetuniverseflycontroller.contents.GeneralConfigProtoType;

import java.util.ArrayList;
import java.util.List;

public class ConfigCmdTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> result = new ArrayList<>();
        final GeneralConfigProtoType config = GeneralConfigProtoType.getGenConfig();
        if(args.length == 1){
            result = new ArrayList<>(List.of("load", "save", "worlds", "override", "master"));
        }else{
            switch(args[0]){

                case "override" ->{
                    result = new ArrayList<>(List.of("on", "off", "true", "false"));
                }

                case "master" -> {
                    result = new ArrayList<>(List.of("on", "off"));
                }

                case "worlds" -> {
                    if(args.length == 2){
                        result = new ArrayList<>(List.of("add", "remove", "list"));
                    }else{
                        switch (args[1]){
                            case "add" ->{
                                for (World world : Bukkit.getWorlds()) {
                                    if(!config.getEffectiveWorlds().contains(world))
                                        result.add(world.getName());
                                }
                            } case "remove" -> {
                                for(World world : Bukkit.getWorlds()){
                                    if(config.getEffectiveWorlds().contains(world))
                                        result.add(world.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
        result.removeIf(s -> !s.toLowerCase().startsWith(args[args.length - 1]));
        return result;
    }
}
