package com.github.fullidle.boredplugin.util;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class FileUtil {
    public static Map<File,FileUtil> ALL_DATA = new HashMap<>();
    private final FileConfiguration configuration;
    private final File file;

    private FileUtil(File file){
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.file = file;
        this.configuration = YamlConfiguration.loadConfiguration(file);
        ALL_DATA.put(file,this);
    }

    public static FileUtil getInstance(File file,boolean isNew) {
        if (isNew) {
            ALL_DATA.remove(file);
        }

        return ALL_DATA.computeIfAbsent(file, FileUtil::new);
    }

    @SneakyThrows
    public void save() {
        this.configuration.save(this.file);
    }
}
