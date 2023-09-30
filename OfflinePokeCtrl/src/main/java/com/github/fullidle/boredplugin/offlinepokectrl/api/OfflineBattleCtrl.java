package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static com.github.fullidle.boredplugin.offlinepokectrl.Main.plugin;

public class OfflineBattleCtrl extends BattleControllerBase{
    public OfflineParticipant getOfflinePlayer(String name) {
        for (BattleParticipant participant : this.participants) {
            if (participant instanceof OfflineParticipant) {
                OfflineParticipant par = (OfflineParticipant) participant;
                if (name.equals(par.player.getName())) return par;
            }
        }
        return null;
    }
    public List<OfflineParticipant> getOfflinePlayers(){
        List<OfflineParticipant> list = new ArrayList<>();
        for (BattleParticipant participant : this.participants) {
            if (participant instanceof OfflineParticipant) {
                list.add((OfflineParticipant) participant);
            }
        }
        return list;
    }

    public OfflineBattleCtrl(BattleParticipant[] team1, BattleParticipant[] team2, BattleRules rules) {
        super(team1, team2, rules);
    }

    @Override
    protected void initBattle() throws Exception {
        super.initBattle();
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (!OfflineBattleCtrl.this.battleEnded){
                    for (BattleParticipant participant : OfflineBattleCtrl.this.participants) {
                        if (participant instanceof OfflineParticipant) {
                            OfflineParticipant par = (OfflineParticipant) participant;
                            par.wait = par.getBattleAI().getToBeUsedMoveChoice() == null;
                        }
                    }
                }else{
                    OfflinePokeCtrlAPI.list.remove(OfflineBattleCtrl.this);
                    this.cancel();
                }
            }
        };
        runnable.runTaskTimer(plugin,0,0);
    }
}
