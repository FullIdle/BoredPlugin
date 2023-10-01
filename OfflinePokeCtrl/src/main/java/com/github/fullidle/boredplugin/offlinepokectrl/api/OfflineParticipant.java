package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.TrainerParticipant;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class OfflineParticipant extends TrainerParticipant {
    public boolean WaitForTheNextUUIDToBeSelected = false;
    public OfflineParticipant(OfflineTrainer trainer, EntityPlayer opponent, int numPokemon) throws IllegalStateException {
        super(trainer, opponent, numPokemon);
    }

    public OfflineParticipant(OfflineTrainer trainer, EntityPlayer opponent, int numPokemon, List<Pokemon> teamSelection) throws IllegalStateException {
        super(trainer, opponent, numPokemon, teamSelection);
    }

    public OfflineParticipant(OfflineTrainer trainer, int numPokemon) throws IllegalStateException {
        super(trainer, numPokemon);
    }

    @Override
    public OfflineAI getBattleAI() {
        return (OfflineAI) super.getBattleAI();
    }

    public OfflineTrainer getTrainer(){
        return (OfflineTrainer) this.trainer;
    }
}
