package com.github.fullidle.boredplugin.pokeclear;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;

import static com.github.fullidle.boredplugin.pokeclear.Data.*;

@SubPlugin(methodName = "register")
public class Main extends FiPlugin {
    public static final String cmdName = "pokeclear";
    @Override
    public void onLoad() {
        super.onLoad();
        registerCommand(cmdName);
    }

    public void onEnable() {
        register();
    }
    public static void register(){
        FiPlugin plugin = CommonData.getMainPlugin();
        plugin.saveDefaultConfig();
        FileUtil fileUtil = plugin.getConfig(CommonData.SubPlugin.POKECLEAR, "config.yml");
        FileConfiguration config = fileUtil.getConfiguration();
        world = config.getStringList("world");
        unClearDexID = config.getIntegerList("unClearDexID");
        if (config.getString("clearType").equalsIgnoreCase("normal")) {
            normalClear = new NormalClear(CommonData.getMainPlugin());
        } else {
            waitClear = new WaitClear(CommonData.getMainPlugin());
        }

        PluginCommand command = plugin.getCommand(cmdName);
        CMD executor = new CMD();
        command.setExecutor(executor);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, AD::new);
        for (Species species : PixelmonSpecies.getAll()) {
            if (species.isUltraBeast()) {
                ultrabeasts.add(species.getName());
            } else if (species.isLegendary()) {
                legendaries.add(species.getName());
            }
        }
    }

    @SneakyThrows
    public static PluginCommand registerCommand(String name) {
        Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
        constructor.setAccessible(true);
        return constructor.newInstance(name, CommonData.getMainPlugin());
    }
}
