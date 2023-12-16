package dz.fullidle.filpwhitelist.filpwhitelist;

import lombok.Getter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Main extends JavaPlugin {
    public static Main plugin;
    public static LuckPerms lpApi;

    public static List<String> processCmd;
    public static List<String> notCheckPlayer;
    public static List<String> nonAdminGroup;

    @Override
    public void onEnable() {
        lpApi = LuckPermsProvider.get();

        plugin = this;
        reloadConfig();

        new FIPLListener(lpApi);
        getCommand(getName().toLowerCase()).setExecutor(new CMD());

        getLogger().info("§a插件已载入(待收入无聊插件内(可能会忘记))!");
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
        processCmd = getConfig().getStringList("处理指令");
        notCheckPlayer = getConfig().getStringList("不检测的玩家").stream().map(String::toLowerCase).collect(Collectors.toList());
        nonAdminGroup = getConfig().getStringList("非管理组");
    }
}
