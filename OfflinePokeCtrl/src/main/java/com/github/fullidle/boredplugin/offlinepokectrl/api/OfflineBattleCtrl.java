package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;

import java.util.ArrayList;
import java.util.List;

public class OfflineBattleCtrl extends BattleControllerBase{
    public OfflineParticipant getOfflinePlayer(String name) {
        for (BattleParticipant participant : this.participants) {
            if (participant instanceof OfflineParticipant) {
                OfflineParticipant par = (OfflineParticipant) participant;
                if (name.equals(par.getTrainer().getOfflinePlayer().getName())) return par;
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
}
