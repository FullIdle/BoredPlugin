package com.github.fullidle.boredplugin.bioprompt;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import org.bukkit.Bukkit;

@SubPlugin(methodName = "register")
public class Main extends FiPlugin {
    @Override
    public void onLoad() {
        CommonData.setMainPlugin(this);
    }
    @Override
    public void onEnable() {
        CommonData.getMainPlugin().saveDefaultConfig();
        register();
        getLogger().info("§a插件已启用!");
    }
    public static void register(){
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(),CommonData.getMainPlugin());
    }
}