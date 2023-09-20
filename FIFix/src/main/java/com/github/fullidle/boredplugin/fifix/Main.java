package com.github.fullidle.boredplugin.fifix;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main main;
    @Override
    public void onEnable() {
        main =this;
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }
}
