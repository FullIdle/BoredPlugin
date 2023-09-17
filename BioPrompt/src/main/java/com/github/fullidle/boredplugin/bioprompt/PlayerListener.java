package com.github.fullidle.boredplugin.bioprompt;

import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {
    public static Map<UUID, String> map = new HashMap<>();
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (e.getFrom().distance(e.getTo()) <= 0) {
            return;
        }
        World world = e.getTo().getWorld();
        if (!Main.worldSet.containsKey(world)){
            return;
        }
        Boolean b = Main.worldSet.get(world);
        /*<玩家区块更新提示>*/
        Player player = e.getPlayer();
        UUID uniqueId = player.getUniqueId();
        String biomeName;
        if (Main.hasForge){
            biomeName = forgeGet(player);
        }else{
            biomeName = player.getLocation().getBlock().getBiome().name();
        }
        String oldBiome = map.get(uniqueId);
        if (oldBiome == null){
            map.put(uniqueId,biomeName);
            title(player,biomeName,b);
            return;
        }
        if (oldBiome.equals(biomeName)){
            return;
        }
        map.replace(uniqueId,biomeName);
        title(player,biomeName,b);
    }
    public void title(Player player,String biome,boolean b){
        FileUtil config = CommonData.getMainPlugin().getConfig(CommonData.SubPlugin.BIOPROMPT, "config.yml");
        String name = config.getConfiguration().getString("translateName." + biome);
        if (name != null){
            biome = name;
        }
        String msg = config.getConfiguration().getString("format").replace('&','§');
        msg = msg.replace("{biome}",biome);
        if (b)player.sendTitle(msg,"",7, config.getConfiguration().getInt("time")*20,7);
        else player.sendTitle("",msg,7, config.getConfiguration().getInt("time")*20,7);
    }

    public String forgeGet(Player player){
        net.minecraft.entity.player.EntityPlayer p = ((CraftPlayer) player).getHandle();
        net.minecraft.world.biome.Biome biome = p.field_70170_p.func_180494_b(new net.minecraft.util.math.BlockPos(p));
        return biome.getRegistryName().func_110623_a().toUpperCase();
    }
}
