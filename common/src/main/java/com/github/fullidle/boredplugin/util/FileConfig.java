package com.github.fullidle.boredplugin.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class FileConfig extends YamlConfiguration {
    public FileConfig() {
        super();
    }

    public static FileConfig loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");
        FileConfig config = new FileConfig();

        try {
            config.load(file);
        } catch (FileNotFoundException ignored) {
        } catch (IOException | InvalidConfigurationException var4) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, var4);
        }

        return config;
    }

    public String getString(String path,boolean replaceColorCode) {
        String string = super.getString(path);
        if (string != null&&replaceColorCode) string = string.replace("§","&");
        return string;
    }
}
