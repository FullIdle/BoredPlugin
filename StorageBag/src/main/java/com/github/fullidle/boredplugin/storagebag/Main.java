package com.github.fullidle.boredplugin.storagebag;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.command.PluginCommand;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SubPlugin(enable = "register")
public class Main extends FiPlugin {
    public static Map<String, StorageBagItemStack> map = new HashMap<>();

    public static FiPlugin main;
    public static FileUtil config;

    @Override
    public void onEnable() {
        CommonData.getMainPlugin().saveDefaultConfig();
        register();
    }
    public static void register(){
        reload();
        regMyCmd();

        main.getServer().getPluginManager().registerEvents(new PlayerListener(),main);


        main.getLogger().info("§aPlugin enabled!");
    }

    public static void reload(){
        main = CommonData.getMainPlugin();
        main.saveDefaultConfig();
        /*==============*/
        config = main.getConfig(CommonData.SubPlugin.STORAGEBAG, "config.yml");
        CmdE.help = config.getConfiguration().getStringList("message.help").toArray(new String[0]);

        map.clear();
        main.getLogger().info("Loading ItemStack...");
        for (String s : config.getConfiguration().getStringList("itemsID")) {
            ItemStack configItem = getConfigItem(s);

            net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(configItem);
            NBTTagCompound nbt = item.getTag() == null ? new NBTTagCompound():item.getTag();

            NBTTagCompound value = new NBTTagCompound();
            value.setInt("row",config.getConfiguration().getInt("itemList."+s+".row"));
            nbt.set("FIStorageBag", value);

            item.setTag(nbt);
            configItem = CraftItemStack.asBukkitCopy(item);
            map.put(s, new StorageBagItemStack(configItem));
            main.getLogger().info("ItemsID:" + s);
        }
        main.getLogger().info("ItemStack loading completed!");
    }
    public static ItemStack getConfigItem(String id){
        return config.getConfiguration().getItemStack("itemList."+id+".itemStack");
    }

    public static String getColorMsg(String path){
        return config.getConfiguration().getString(path).replace("&","§");
    }

    public static void regMyCmd(){
        main.onRegisterCommand(main, new ArrayList<>(Arrays.asList("storagebag", "stb")));
        PluginCommand command = main.getCommand("storagebag");
        command.setExecutor(new CmdE());
        command.setTabCompleter(new CmdT());
    }
}