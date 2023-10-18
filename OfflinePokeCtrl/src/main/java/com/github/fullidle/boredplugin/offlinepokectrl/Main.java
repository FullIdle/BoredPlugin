package com.github.fullidle.boredplugin.offlinepokectrl;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.offlinepokectrl.command.SelectSkillsCmd;
import com.github.fullidle.boredplugin.offlinepokectrl.command.StartBattleCmd;

import java.util.ArrayList;
import java.util.List;

@SubPlugin
public class Main extends FiPlugin {
    public static FiPlugin plugin;
    @Override
    public void onEnable() {
/*
        this.saveDefaultConfig();
*/
        enable(this);
    }

    public static void enable(FiPlugin plugin){
        Main.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(),plugin);

        plugin.onRegisterCommand(plugin, new ArrayList<>(List.of("startbattletest")), new ArrayList<>(List.of("selectskilltest")));
        plugin.getCommand("startbattletest").setExecutor(new StartBattleCmd());
        plugin.getCommand("selectskilltest").setExecutor(new SelectSkillsCmd());
    }
}