package com.github.fullidle.boredplugin.bioprompt;

import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListener implements Listener {
    public static Map<UUID, Biome> map = new HashMap<>();
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (e.getFrom().distance(e.getTo()) <= 0) {
            return;
        }
        /*<玩家区块更新提示>*/
        Player player = e.getPlayer();
        UUID uniqueId = player.getUniqueId();
        Biome biome = player.getLocation().getBlock().getBiome();
        Biome oldBiome = map.get(uniqueId);
        if (oldBiome == null){
            map.put(uniqueId,biome);
            title(player,biome);
            return;
        }
        if (oldBiome == biome){
            return;
        }
        map.replace(uniqueId,biome);
        title(player,biome);
    }
    public void title(Player player,Biome biome){
        String name = biome.name();
        FileUtil config = CommonData.getMainPlugin().getConfig(CommonData.SubPlugin.BIOPROMPT, "config.yml");
        name = config.getConfiguration().getString("translateName."+name);
        String msg = config.getConfiguration().getString("format").replace('&','§');
        msg = msg.replace("{biome}",name);
        player.sendTitle(msg,"",7, config.getConfiguration().getInt("time")*20,7);
    }
}
