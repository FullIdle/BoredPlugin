package com.github.fullidle.boredplugin.storagebag;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class CmdE implements CommandExecutor {
    public static String[] help;
    public static final List<String> subCmd = Arrays.asList("help","give","rename","upload","check","reload");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = ((Player) sender);
        }
        if (args.length < 1) {
            sender.sendMessage(help);
            return false;
        }
        String sub = args[0];
        switch (sub){
            default:{
                sender.sendMessage(help);
                break;
            }
            case "give":{
                if (args.length < 3){
                    sender.sendMessage(help);
                    return false;
                }
                /*====================*/
                Player other = Bukkit.getPlayer(args[1]);
                if (other == null){
                    sender.sendMessage(Main.getColorMsg("message.msg.PlayerIsNotOnline"));
                    return false;
                }
                ItemStack itemStack = Main.map.get(args[2]).getItemStack();
                if (other.getInventory().firstEmpty() == -1) {
                    other.getWorld().dropItem(other.getLocation(),itemStack);
                    String info = Main.getColorMsg("message.msg.NotEnoughSpaceInBackpack");
                    sender.sendMessage(info);
                    other.sendMessage(info);
                }else {
                    other.getInventory().addItem(itemStack);
                    String info = Main.getColorMsg("message.msg.TipsForSuccess");
                    sender.sendMessage(info);
                }
                break;
            }
            case "rename":{
                if (args.length < 2){
                    sender.sendMessage(help);
                    return false;
                }
                if (player == null){
                    sender.sendMessage(Main.getColorMsg("message.msg.NonPlayerExecution"));
                    return false;
                }
                ItemStack item = player.getInventory().getItemInMainHand();
                if (!StorageBagHolder.isStorageBag(item)){
                    sender.sendMessage(Main.getColorMsg("message.msg.NonStorageBag"));
                    return false;
                }
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(args[1].replace("&","§"));
                item.setItemMeta(meta);
                sender.sendMessage(Main.getColorMsg("message.msg.TipsForSuccess"));
                break;
            }
            case "upload":{
                if (player == null){
                    sender.sendMessage(Main.getColorMsg("message.msg.NonPlayerExecution"));
                    return false;
                }
                if (args.length < 3){
                    sender.sendMessage(help);
                    return false;
                }
                String id = args[1];
                int row;
                try {
                    row = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(Main.getColorMsg("message.msg.NonNumeric"));
                    return false;
                }
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item == null || StorageBagHolder.isStorageBag(item)){
                    sender.sendMessage(Main.getColorMsg("message.msg.ThisItemCannotBeAdded"));
                    return false;
                }

                List<String> itemsID = Main.config.getConfiguration().getStringList("itemsID");
                itemsID.add(id);
                Main.config.getConfiguration().set("itemList."+id+".itemStack",item);
                Main.config.getConfiguration().set("itemList."+id+".row",row);
                Main.config.getConfiguration().set("itemsID",itemsID);
                Main.config.save();
                sender.sendMessage(Main.getColorMsg("message.msg.ReloadReminder"));
                break;
            }
            case "check": {
                sender.sendMessage("§aID:");
                sender.sendMessage(Main.map.keySet().toArray(new String[0]));
                break;
            }
            case "reload":{
                Main.reload();
                sender.sendMessage("§aReloaded!");
                break;
            }
        }
        return false;
    }
}
