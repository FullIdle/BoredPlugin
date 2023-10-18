package com.github.fullidle.boredplugin.pokeclear;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import com.pixelmonmod.pixelmon.api.pokemon.species.Species;
import com.pixelmonmod.pixelmon.api.registries.PixelmonSpecies;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.github.fullidle.boredplugin.pokeclear.Data.*;

@SubPlugin(enable = "register")
public class Main extends FiPlugin {
    public static List<String> cmdName = List.of("pokeclear");
    public void onEnable() {
        register();
    }
    public static void register(){
        FiPlugin plugin = CommonData.getMainPlugin();
        plugin.saveDefaultConfig();
        plugin.onRegisterCommand(plugin,new ArrayList<>(cmdName));
        FileUtil fileUtil = plugin.getConfig(CommonData.SubPlugin.POKECLEAR, "config.yml");
        FileConfiguration config = fileUtil.getConfiguration();
        world = config.getStringList("world");
        unClearDexID = config.getIntegerList("unClearDexID");
        if (config.getString("clearType").equalsIgnoreCase("normal")) {
            normalClear = new NormalClear(CommonData.getMainPlugin());
        } else {
            waitClear = new WaitClear(CommonData.getMainPlugin());
        }

        plugin.getCommand(cmdName.get(0)).setExecutor(new CMD());

        Bukkit.getScheduler().runTaskAsynchronously(plugin, AD::new);
        for (Species species : PixelmonSpecies.getAll()) {
            if (species.isUltraBeast()) {
                ultrabeasts.add(species.getName());
            } else if (species.isLegendary()) {
                legendaries.add(species.getName());
            }
        }
    }
}
