package com.github.fullidle.fimissionsystem.Main;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {

    }

    public void reload(){
        saveDefaultConfig();
    }
}