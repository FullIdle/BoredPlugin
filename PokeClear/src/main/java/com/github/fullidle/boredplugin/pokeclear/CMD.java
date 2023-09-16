package com.github.fullidle.boredplugin.pokeclear;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import java.util.ArrayList;

import static com.github.fullidle.boredplugin.pokeclear.Data.*;

public class CMD extends Command {
    public CMD(){
        super("pokeclear","pokeclear",null,new ArrayList<>());
    }

    @Override
    public boolean execute(CommandSender sender,String label,String[] args) {
        FiPlugin plugin = CommonData.getMainPlugin();
        if (sender.isOp() && args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            FileUtil fileUtil = plugin.getConfig(CommonData.SubPlugin.POKECLEAR, "config.yml");
            fileUtil.reload();
            FileConfiguration config = fileUtil.getConfiguration();
            world = config.getStringList("world");
            unClearDexID = config.getIntegerList("unClearDexID");
            if (config.getString("clearType").equalsIgnoreCase("normal")) {
                normalClear.load();
            } else {
                waitClear.load();
            }
            sender.sendMessage("重载完成");
            Bukkit.getScheduler().runTaskAsynchronously(plugin, AD::new);
        }
        if (sender.isOp() && args.length == 1 && args[0].equalsIgnoreCase("clear")) {
            for (World bukkitWorld : Bukkit.getServer().getWorlds()) {
                for (Entity entity : bukkitWorld.getEntities()) {
                    net.minecraft.world.entity.Entity en = ((CraftEntity) entity).getHandle();
                    if (en instanceof PixelmonEntity) {
                        PixelmonEntity pe = (PixelmonEntity) en;
                        pe.unloadEntity();
                    }
                }
            }
            return false;
        }
        return false;
    }
}
