package com.github.fullidle.boredplugin.offlinepokectrl;

import com.aystudio.core.forge.event.ForgeEvent;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineAI;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineBattleCtrl;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineParticipant;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflinePokeCtrlAPI;
import com.pixelmonmod.pixelmon.api.events.battles.SetBattleAIEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener {
    @EventHandler
    public void onForge(ForgeEvent event){
        if (event.getForgeEvent() instanceof SetBattleAIEvent) {
            SetBattleAIEvent e = (SetBattleAIEvent) event.getForgeEvent();
            if (e.getAI() instanceof OfflineAI) {
                return;
            }
            if (e.participant instanceof OfflineParticipant) {
                e.setAI(new OfflineAI(e.participant));
                OfflineBattleCtrl bc = (OfflineBattleCtrl) e.bc;
                if (!OfflinePokeCtrlAPI.list.contains(bc)) {
                    OfflinePokeCtrlAPI.list.add(bc);
                }
            }
        }
    }
}
