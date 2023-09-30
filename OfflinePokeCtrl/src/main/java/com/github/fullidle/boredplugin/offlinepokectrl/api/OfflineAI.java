package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.battles.controller.ai.BattleAIBase;
import com.pixelmonmod.pixelmon.battles.controller.ai.MoveChoice;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

public class OfflineAI extends BattleAIBase {
    @Setter
    @Getter
    private MoveChoice ToBeUsedMoveChoice;
    public OfflineAI(BattleParticipant participant) {
        super(participant);
    }

    @Override
    public MoveChoice getNextMove(PixelmonWrapper pixelmonWrapper) {
        return ToBeUsedMoveChoice;
    }

    @Override
    public UUID getNextSwitch(PixelmonWrapper pixelmonWrapper) {
        return null;
    }

    public ArrayList<MoveChoice> getACho(PixelmonWrapper pw) {
        return super.getAttackChoices(pw);
    }
}

