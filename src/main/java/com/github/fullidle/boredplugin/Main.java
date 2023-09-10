package com.github.fullidle.boredplugin;

import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import com.github.fullidle.boredplugin.util.MethodUtil;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;

public class Main extends FiPlugin {
    private final List<Class<? extends FiPlugin>> subPCls = new ArrayList<>();
    @SneakyThrows
    @Override
    public void onLoad() {
        saveDefaultConfig();

        CommonData.setMainPlugin(this);
        getLogger().info("§a加载子插件数据中ing...");
        ArrayList<JarEntry> jarEntries = MethodUtil.getJarFileResourceName(getFile(),"com/github/fullidle/boredplugin",".class");
        for (JarEntry jarEntry : jarEntries) {
            String name = jarEntry.getName();
            String className = name.substring(0, name.lastIndexOf(".")).replace("/", ".");
            if (!className.equalsIgnoreCase("module-info")) {
                Class<?> aClass = Class.forName(className);
                if (aClass.isAnnotationPresent(SubPlugin.class)) {
                    subPCls.add((Class<? extends FiPlugin>) aClass);
                }
            }
        }
    }
    @SneakyThrows
    @Override
    public void onEnable() {
        getLogger().info("§3插件已启用↓");
        /*<加载了的功能>*/
        for (CommonData.SubPlugin value : CommonData.SubPlugin.values()) {
            getLogger().info("§3"+value.getName());
        }
        for (Map.Entry<String, File> entry : CommonData.SubPlugin.MC9YLOGIN.getFiles().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getName());
        }
        /*<子模块的注册>*/
        for (Class<? extends JavaPlugin> subPcl : subPCls) {
            String methodName = subPcl.getAnnotation(SubPlugin.class).methodName();
            Method method = subPcl.getDeclaredMethod(methodName);
            method.invoke(subPcl);
        }
        /*<提示>*/
        getLogger().info("§b可以自己去§a"+getDescription().getWebsite()+"§b上将功能单独构建出来");
    }

    @Override
    public void saveDefaultConfig() {
        /*<清理所有数据>*/
        FileUtil.ALL_DATA.clear();
        /*<原插件config>*/
        super.saveDefaultConfig();
        val jarFileResourceName = MethodUtil.getJarFileResourceName(getFile(),null,".yml");
        System.out.println("§3======="+jarFileResourceName.size());
        for (JarEntry jarEntry : jarFileResourceName) {
            var name = jarEntry.getName();
            saveResource(name,false);
            var file = new File(getDataFolder(), name);
            name = file.getName();
            val split = name.split("-");
            val subPluginName = split[0].replace("-","");
            var fileName = (String)null;
            if (split.length > 1) {
                fileName = split[1].replace("-", "");
            }

            var subPlugin = (CommonData.SubPlugin)null;

            try {
                subPlugin = CommonData.SubPlugin.valueOf(subPluginName.toUpperCase());
            } catch (IllegalArgumentException ignored) {
            }

            if (subPlugin != null){
                if (fileName == null){
                    CommonData.SubPlugin.BOREDPLUGIN.getFiles().put(name,file);
                }else{
                    subPlugin.getFiles().put(fileName, file);
                }
            }else{
                CommonData.SubPlugin.BOREDPLUGIN.getFiles().put(name,file);
            }
            FileUtil.getInstance(file,true);
        }
    }
}