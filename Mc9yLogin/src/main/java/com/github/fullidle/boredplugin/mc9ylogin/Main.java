package com.github.fullidle.boredplugin.mc9ylogin;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.SubPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.mc9ylogin.mc9y.Mc9yAccount;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

@SubPlugin(load = "register")
public class Main extends FiPlugin {
    private static Mc9yAccount myAccount;
    @SneakyThrows
    @Override
    public void onLoad() {
        super.onLoad();
        register();
    }

    @Override
    public void onEnable() {
        getLogger().info("§a插件已启用!");
    }
    public static void register() {
        FileConfiguration config = CommonData.getMainPlugin().getConfig(CommonData.SubPlugin.MC9YLOGIN, "config.yml").getConfiguration();
        myAccount = new Mc9yAccount(config.getString("account"),config.getString("password"));
        Bukkit.getLogger().info("§3正在登入ing...");
        if (!myAccount.login()) {
            Bukkit.getLogger().info("§3登入失败!");
            Bukkit.getLogger().info("§3正在停止服务器");
            Bukkit.getServer().shutdown();
            return;
        }
        Bukkit.getLogger().info("§a登入成功!");
        Bukkit.getLogger().info("§3以下登入后的信息↓");
        int id = myAccount.getId();
        String name = myAccount.getName();
        String designation = myAccount.getDesignation();
        String level = myAccount.getLevel();
        String account = myAccount.getAccount();
        Bukkit.getLogger().info("§3account:" + account);
        Bukkit.getLogger().info("§3id:" + id);
        Bukkit.getLogger().info("§3name:" + name);
        Bukkit.getLogger().info("§3level:" + level);
        Bukkit.getLogger().info("§3designation:" + designation);
        Bukkit.getLogger().info("§3以下账号插件资源");
        int length = myAccount.getResource().length;
        Bukkit.getLogger().info("§3资源插件共有:" +length+"个");
    }
}