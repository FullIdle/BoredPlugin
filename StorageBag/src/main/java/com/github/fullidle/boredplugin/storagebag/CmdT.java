package com.github.fullidle.boredplugin.storagebag;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.fullidle.boredplugin.storagebag.CmdE.subCmd;

public class CmdT implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return subCmd;
        if (args.length == 1){return subCmd.stream().filter(s->s.startsWith(args[0])).collect(Collectors.toList());}
        switch (args[0]) {
            case "upload": {
                if (args.length == 3){
                    return Arrays.asList("1","2","3","4","5","6");
                }
                break;
            }
            case "give": {
                if (args.length == 2){
                    return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(s->s.startsWith(args[1])).collect(Collectors.toList());
                }
                if (args.length == 3){
                    return Main.map.keySet().stream().filter(s->s.startsWith(args[2])).collect(Collectors.toList());
                }
                break;
            }
        }
        return new ArrayList<>();
    }
}
