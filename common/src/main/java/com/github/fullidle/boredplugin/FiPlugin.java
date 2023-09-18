package com.github.fullidle.boredplugin;

import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;

public class FiPlugin extends JavaPlugin {
    @SneakyThrows
    public void onRegisterCommand(FiPlugin plugin,String... commandsName){
        if (commandsName == null)return;
        Field commandMapF = SimplePluginManager.class.getDeclaredField("commandMap");
        commandMapF.setAccessible(true);
        CommandMap commandMap = (CommandMap) commandMapF.get(Bukkit.getServer().getPluginManager());
        Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
        constructor.setAccessible(true);
        for (String s : commandsName) {
            PluginCommand cmd = constructor.newInstance(s, plugin);
            commandMap.register(s,cmd);
        }
    }

    @Override
    public void onLoad() {
        CommonData.setMainPlugin(this);
    }

    public FileUtil getConfig(CommonData.SubPlugin subPlugin, String configName) {
        return FileUtil.getInstance(subPlugin.getFiles().get(configName), false);
    }
    @Override
    public void saveDefaultConfig() {
        super.saveDefaultConfig();
        String name = CommonData.getMainPlugin().getName().toUpperCase();
        File file = new File(CommonData.getMainPlugin().getDataFolder(), "config.yml");
        if (!name.equals(CommonData.SubPlugin.BOREDPLUGIN.name())){
            Map<String, File> files = CommonData.SubPlugin.valueOf(name).getFiles();
            files.remove("config.yml");
            files.put("config.yml",file);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
