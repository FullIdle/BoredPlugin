package com.github.fullidle.boredplugin.fifix;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;

@SubPlugin(methodName = "register")
public class Main extends FiPlugin {
    public static FiPlugin main = CommonData.getMainPlugin();
    @Override
    public void onEnable() {
        register();
    }

    public static void register(){
        main.saveDefaultConfig();
        main.getServer().getPluginManager().registerEvents(new PlayerListener(), main);
    }
}
