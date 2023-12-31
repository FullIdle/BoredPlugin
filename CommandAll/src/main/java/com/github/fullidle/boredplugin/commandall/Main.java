package com.github.fullidle.boredplugin.commandall;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.sun.tools.javac.util.List;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Collections;


@SubPlugin
public class Main extends FiPlugin {
    public static FiPlugin main;
    @Override
    public void onEnable() {
        enable();
        getLogger().info("§a插件已启用!");
    }

    public static void enable(){
        main = CommonData.getMainPlugin();
/*
        main.saveDefaultConfig();
*/
        main.onRegisterCommand(main, new ArrayList<>(List.of("commandall")));
        PluginCommand cmd = main.getCommand("commandall");
        cmd.setAliases(Collections.singletonList("cmdall"));
        main.getServer().getPluginManager().addPermission(new Permission("commandall.cmd"));
        cmd.setExecutor(new Cmd());
    }
}