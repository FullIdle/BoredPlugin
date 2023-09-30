package com.github.fullidle.boredplugin.bioprompt;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

@SubPlugin(enable = "register")
public class Main extends FiPlugin {
    public static boolean hasForge;
    public static Map<World,Boolean> worldSet = new HashMap<>();
    @Override
    public void onEnable() {
        CommonData.getMainPlugin().saveDefaultConfig();
        register();
        getLogger().info("§a插件已启用!");
    }
    @SneakyThrows
    public static void register(){
        FileUtil config = CommonData.getMainPlugin().getConfig(CommonData.SubPlugin.BIOPROMPT, "config.yml");
        for (String s : config.getConfiguration().getStringList("worlds")) {
            String[] split = s.split(":");
            String wn = split[0].replace(":", "");
            boolean b = Boolean.parseBoolean(split[1].replace(":", ""));
            World world = Bukkit.getServer().getWorld(wn);
            worldSet.put(world,b);
        }

        hasForge = hasForge();
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(),CommonData.getMainPlugin());
    }

    public static boolean hasForge(){
        String s = "net.minecraftforge.common.ForgeVersion";
        try {
            Class.forName(s);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}