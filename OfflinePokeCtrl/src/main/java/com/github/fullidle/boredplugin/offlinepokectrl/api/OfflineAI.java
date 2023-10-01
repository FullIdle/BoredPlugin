package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.battles.controller.ai.MoveChoice;
import com.pixelmonmod.pixelmon.battles.controller.ai.TacticalAI;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class OfflineAI extends TacticalAI {
    private MoveChoice ToBeUsedMoveChoice;
    private UUID UUIDToBeSelected;
    public OfflineAI(BattleParticipant participant) {
        super(participant);
    }

    @Override
    public MoveChoice getNextMove(PixelmonWrapper pixelmonWrapper) {
        MoveChoice cho = ToBeUsedMoveChoice;
        ToBeUsedMoveChoice = null;
        return cho;
    }

    @Override
    public UUID getNextSwitch(PixelmonWrapper pixelmonWrapper) {
        UUID uuid = UUIDToBeSelected;
        UUIDToBeSelected = null;
        return uuid;
    }

    public ArrayList<MoveChoice> getACho(PixelmonWrapper pw) {
        return super.getAttackChoices(pw);
    }
}

