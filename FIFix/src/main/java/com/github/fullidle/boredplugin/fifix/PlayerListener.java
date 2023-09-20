package com.github.fullidle.boredplugin.fifix;

import com.aystudio.core.forge.event.ForgeEvent;
import com.pixelmonmod.pixelmon.api.events.PokeballImpactEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.github.fullidle.boredplugin.fifix.Main.main;

public class PlayerListener implements Listener {
    /*<-forge的事件->*/
    @EventHandler
    public void onForge(ForgeEvent event){
        /*<-碰撞的事件->*/
        if (event.getForgeEvent() instanceof PokeballImpactEvent){
            PokeballImpactEvent e = (PokeballImpactEvent) event.getForgeEvent();
            if (!e.isEmptyBall) {
                if ((e.getEntityHit()) == null || !(e.getEntityHit() instanceof EntityPixelmon)) {return;}
                EntityPixelmon ep = (EntityPixelmon) e.getEntityHit();
                /*<-判断是否在战斗||判断是否对战种玩家是空的->*/
                if (ep.battleController == null || ep.battleController.getPlayers().isEmpty()) {
                    return;
                }
                /*<-在对战中就会且玩家是空的就会丢给原版处理,如果在对战中且有玩家就需要插件处理了->*/
                Player p = (Player) e.pokeball.field_70192_c.getBukkitEntity();
                p.sendMessage(main.getConfig().getString("PokeCannotBeBattled"));
                e.setCanceled(true);
            }
        }
    }
    /*<-原版的事件->*/
}
