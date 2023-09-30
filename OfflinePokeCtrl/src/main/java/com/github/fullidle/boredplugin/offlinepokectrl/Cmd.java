package com.github.fullidle.boredplugin.offlinepokectrl;

import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineAI;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineBattleCtrl;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineParticipant;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflinePokeCtrlAPI;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

import static com.github.fullidle.boredplugin.offlinepokectrl.Main.plugin;

public class Cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        UUID uuid = UUID.fromString("44e0d255-ef8b-33e0-8459-40c348a8c344");
        PlayerPartyStorage pps = Pixelmon.storageManager.getParty(uuid);
        Pokemon pPoke = pps.get(0);
        PlayerPartyStorage gsq = Pixelmon.storageManager.getParty(Bukkit.getPlayer("GSQ_Lin").getUniqueId());
        Pokemon pokemon = gsq.get(0);
        OfflineBattleCtrl ctrl = OfflinePokeCtrlAPI.startBattle(new OfflineParticipant(null, pPoke.getOrSpawnPixelmon(gsq.getPlayer())), new PlayerParticipant(gsq.getPlayer(), pokemon.getOrSpawnPixelmon(gsq.getPlayer())));
        Bukkit.getScheduler().runTaskLater(plugin,()->{
            for (OfflineParticipant opp : ctrl.getOfflinePlayers()) {
                OfflineAI ai = opp.getBattleAI();
                ai.setToBeUsedMoveChoice(ai.getACho(opp.controlledPokemon.get(0)).get(0));
            }
        },20);
        return false;
    }
}
