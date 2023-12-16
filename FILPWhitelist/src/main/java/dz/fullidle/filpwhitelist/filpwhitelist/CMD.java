package dz.fullidle.filpwhitelist.filpwhitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Main.plugin.reloadConfig();
        sender.sendMessage("§a插件已重载!");
        return false;
    }
}
