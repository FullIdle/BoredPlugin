package com.github.fullidle.boredplugin.util;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

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
        }
    }

/*    private boolean isPluginType(Class<?> cls){
        if (cls == null) {
            return false;
        }
        if (cls.equals(JavaPlugin.class)) {
            return true;
        }else{
            return isPluginType(cls.getSuperclass());
        }
    }*/
}
