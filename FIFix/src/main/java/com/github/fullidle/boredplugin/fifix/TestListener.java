package com.github.fullidle.boredplugin.fifix;

import com.aystudio.core.forge.event.ForgeEvent;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.battles.SetBattleAIEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.fullidle.boredplugin.fifix.Main.main;

public class TestListener implements Listener {
    List<UUID> uuidList = new ArrayList<>();
    @EventHandler
    public void onForge(ForgeEvent event){
        /*<-开始战斗后自然会设置ai->*/
        if (event.getForgeEvent() instanceof SetBattleAIEvent) {
            SetBattleAIEvent e = (SetBattleAIEvent) event.getForgeEvent();
            /*排除掉一些不需要的情况*/
            if (!(e.participant instanceof WildPixelmonParticipant) || e.bc.playerNumber < 1) {
                return;
            }
            /*判断是否之前记录过，没有就添加，有就提示并结束掉,取消事件对于这次修复没有用*/
            UUID uuid = e.participant.getEntity().getBukkitEntity().getUniqueId();
            if (!uuidList.contains(uuid)) {
                uuidList.add(uuid);
                return;
            }else{
                for (PlayerParticipant player : e.bc.getPlayers()) {
                    player.player.getBukkitEntity().sendMessage(main.getConfig().getString("PokeCannotBeBattled"));
                }
                e.bc.endBattle();
            }
        }
        /*<-结束战斗后->*/
        if (event.getForgeEvent() instanceof BattleEndEvent){
            BattleEndEvent e = (BattleEndEvent) event.getForgeEvent();
            /*排除没有用的东西*/
            if (e.bc.playerNumber < 1) {
                return;
            }
            /*获取这个对局中所有的野生精灵,毕竟会出现bug的也只有野生的了*/
            for (BattleParticipant participant : e.bc.participants) {
                if (participant instanceof WildPixelmonParticipant) {
                    /*删除被记录的条件*/
                    UUID uuid = participant.getEntity().getBukkitEntity().getUniqueId();
                    uuidList.remove(uuid);
                }
            }
        }
    }
}
