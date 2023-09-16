package com.github.fullidle.boredplugin.custompapi;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.data.CommonData;

import javax.script.ScriptEngineManager;

public class Main extends FiPlugin {
    public static ScriptEngineManager scriptEngineManager;
    @Override
    public void onEnable() {
        CommonData.getMainPlugin().saveDefaultConfig();
        register();
        getLogger().info("§a插件已启用!");
    }

    public static void register(){
        scriptEngineManager = new ScriptEngineManager();
        new MyPapi().register();
    }
}