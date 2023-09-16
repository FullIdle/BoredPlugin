package com.github.fullidle.boredplugin;

import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public class FiPlugin extends JavaPlugin {
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
        File file = new File(CommonData.getMainPlugin().getDataFolder(), "config.yml");
        String name = CommonData.getMainPlugin().getName().toUpperCase();
        Map<String, File> files = CommonData.SubPlugin.valueOf(name).getFiles();
        files.remove("config.yml");
        files.put("config.yml",file);
    }
}
