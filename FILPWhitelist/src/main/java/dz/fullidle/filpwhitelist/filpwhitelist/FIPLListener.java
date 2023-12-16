package dz.fullidle.filpwhitelist.filpwhitelist;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import org.bukkit.Bukkit;

public class FIPLListener {
    public FIPLListener(LuckPerms api){
        api.getEventBus().subscribe(Main.plugin, NodeMutateEvent.class, e -> {
            if (!e.isUser()){return;}
            User target = (User) e.getTarget();
            if (Main.notCheckPlayer.contains(target.getUsername().toLowerCase())){return;}
            Node[] array = e.getDataAfter().stream().filter(it -> (it.getType().equals(NodeType.PERMISSION) || it.getType().equals(NodeType.REGEX_PERMISSION))).toArray(Node[]::new);
            if (array.length<1) {return;}
            if (!SomeMethod.checkNodesHasInNonAdminGroup(array)){
                SomeMethod.processCmd(Bukkit.getPlayer(target.getUniqueId()),Main.processCmd);
            }
        });
    }
}
