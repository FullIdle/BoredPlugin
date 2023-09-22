package com.github.fullidle.boredplugin.commandall;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("commandall.cmd")){
            sender.sendMessage("§cyou don't have permission");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage("§a没有参数\n用法: /cmdall [cmd] (cmd中的可以包含变量)");
        }
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append(arg).append(" ");
        }
        String cmd = builder.toString();
        for (Player player : Bukkit.getOnlinePlayers()) {
            boolean op = player.isOp();
            player.setOp(true);
            Bukkit.dispatchCommand(player, PlaceholderAPI.setPlaceholders(player,cmd));
            player.setOp(op);
        }
        return false;
    }
}
