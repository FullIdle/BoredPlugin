package com.github.fullidle.boredplugin.util;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class FileUtil {
    public static Map<File,FileUtil> ALL_DATA = new HashMap<>();
    private FileConfig configuration;
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
        this.configuration = FileConfig.loadConfiguration(file);
        ALL_DATA.put(file,this);
    }

    public static FileUtil getInstance(File file, boolean isNew) {
        if (isNew || !ALL_DATA.containsKey(file)) {
            ALL_DATA.put(file, new FileUtil(file));
        }
        return ALL_DATA.get(file);
    }

    @SneakyThrows
    public void save() {
        this.configuration.save(this.file);
    }

    public void reload(){
        this.configuration = FileConfig.loadConfiguration(file);
    }
}
