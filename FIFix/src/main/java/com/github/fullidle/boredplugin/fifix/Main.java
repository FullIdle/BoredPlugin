package com.github.fullidle.boredplugin.fifix;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;

@SubPlugin(enable = "register")
public class Main extends FiPlugin {
    public static FiPlugin main;
    @Override
    public void onEnable() {
        register();
    }

    public static void register(){
        main = CommonData.getMainPlugin();
        main.saveDefaultConfig();
        main.getServer().getPluginManager().registerEvents(new PlayerListener(), main);
    }
}
