package com.github.fullidle.boredplugin.util;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SubPluginUtil {
    private SubPlugin subPlugin;
    Class<? extends FiPlugin> aClass;
    public SubPluginUtil(SubPlugin subPlugin,Class<? extends FiPlugin> aClass){
        this.subPlugin = subPlugin;
        this.aClass = aClass;
    }
    public void onLoad(){
        execute(subPlugin.load());
    }

    public void onEnable(){
        execute(subPlugin.enable());
    }

    public void onDisable(){
        execute(subPlugin.disable());
    }

    @SneakyThrows
    private void execute(String name){
        if (!"".equals(name)) {
            aClass.getDeclaredMethod(name).invoke(aClass);
            for (Method m : aClass.getDeclaredMethods()) {
                List<Object> list = new ArrayList<>();
                for (Class<?> cls : m.getParameterTypes()) {
                    if (isPluginType(cls)) {
                        list.add(CommonData.getMainPlugin());
                    }else{
                        list.add(null);
                    }
                }
                m.invoke(aClass,list.toArray());
            }
        }
    }

    private boolean isPluginType(Class<?> cls){
        if (cls == null) {
            return false;
        }
        if (cls.equals(JavaPlugin.class)) {
            return true;
        }else{
            return isPluginType(cls.getSuperclass());
        }
    }
}
