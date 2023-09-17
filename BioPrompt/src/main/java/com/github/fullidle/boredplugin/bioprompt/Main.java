package com.github.fullidle.boredplugin.bioprompt;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;

@SubPlugin(methodName = "register")
public class Main extends FiPlugin {
    public static boolean hasForge;
    @Override
    public void onEnable() {
        CommonData.getMainPlugin().saveDefaultConfig();
        register();
        getLogger().info("§a插件已启用!");
    }
    @SneakyThrows
    public static void register(){
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