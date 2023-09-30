package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import org.bukkit.OfflinePlayer;

public class OfflineParticipant extends WildPixelmonParticipant {
    OfflinePlayer player;

    public OfflineParticipant(OfflinePlayer player,boolean isGrassBattleParticipant, EntityPixelmon... pixelmon) {
        super(isGrassBattleParticipant, pixelmon);
        this.player = player;
    }

    public OfflineParticipant(OfflinePlayer player,EntityPixelmon... pixelmon) {
        super(pixelmon);
        this.player = player;
    }

    @Override
    public OfflineAI getBattleAI() {
        return (OfflineAI) super.getBattleAI();
    }
}
