package com.github.fullidle.boredplugin.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MethodUtil {
    /**
     * @param file jar类型的文件
     * @param suffix 筛选指定后缀 如{.yml .txt .png}
     * @return 所有JarEntry的列表对象，这里面不会有config.yml和plugin.yml两个文件
     */
    public static ArrayList<JarEntry> getJarFileResourceName(File file,String folder,String... suffix){
        ArrayList<JarEntry> list = new ArrayList<>();
        try {
            JarFile jarFile = new JarFile(file.getAbsolutePath());
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                boolean b = !entry.isDirectory() && !"plugin.yml".equals(name) && !"config.yml".equals(name);
                if (b) {
                    for (String s : suffix) {
                        if (name.endsWith(s)) {
                            if (folder == null){
                                list.add(entry);
                            }else{
                                if (name.startsWith(folder)){
                                    list.add(entry);
                                }
                            }
                            break;
                        }
                    }
                }
            }
            jarFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
