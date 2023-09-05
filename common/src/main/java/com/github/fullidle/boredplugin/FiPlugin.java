package com.github.fullidle.boredplugin;

import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class FiPlugin extends JavaPlugin {
    public FileUtil getConfig(CommonData.SubPlugin subPlugin,String configName) {
        return FileUtil.getInstance(subPlugin.getFiles().get(configName), false);
    }
}
