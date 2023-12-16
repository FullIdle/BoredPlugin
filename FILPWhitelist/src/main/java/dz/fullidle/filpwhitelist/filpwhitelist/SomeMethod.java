package dz.fullidle.filpwhitelist.filpwhitelist;

import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class SomeMethod {
    public static void processCmd(Player player,List<String> cmds){
        for (String s : cmds) {
            String cmd = PlaceholderAPI.setPlaceholders(player, s);
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),cmd);
        }
    }
    public static boolean checkNodeInNonAdminGroup(Node node){
        for (String groupName : Main.nonAdminGroup) {
            Group group = Main.lpApi.getGroupManager().getGroup(groupName);
            if (group == null) {
                Main.plugin.getLogger().info("§c尝试检测配置非管理组 \""+groupName+"\" 发现不存在!");
                continue;
            }
            return group.getCachedData().getPermissionData().checkPermission(node.getKey()).asBoolean();
        }
        return false;
    }
    public static boolean checkNodesHasInNonAdminGroup(Node... nodes){
        for (Node node : nodes) {
            if (checkNodeInNonAdminGroup(node)) {
                return true;
            }
        }
        return false;
    }
}
