package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.BattleStartedEvent;
import com.pixelmonmod.pixelmon.api.events.PokedexEvent;
import com.pixelmonmod.pixelmon.api.events.npc.NPCEvent;
import com.pixelmonmod.pixelmon.api.storage.PartyStorage;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.TrainerParticipant;
import com.pixelmonmod.pixelmon.battles.rules.BattleRules;
import com.pixelmonmod.pixelmon.enums.battle.EnumBattleType;
import com.pixelmonmod.pixelmon.pokedex.EnumPokedexRegisterStatus;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Iterator;

public class OfflinePokeCtrlAPI{
    public static ArrayList<OfflineBattleCtrl> list = new ArrayList<>();

    public static OfflineBattleCtrl getOfflineBattleCtrl(OfflinePlayer player) {
        for (OfflineBattleCtrl ctrl : list) {
            if (ctrl.getOfflinePlayer(player.getName()) != null) {
                return ctrl;
            }
        }
        return null;
    }

    public static OfflineBattleCtrl startBattle(BattleParticipant[] team1, BattleParticipant[] team2, BattleRules rules) {
        boolean b = true;
        for (BattleParticipant bp : team1) {
            if (bp instanceof OfflineParticipant) {
                b = false;
                break;
            }
        }
        if (b) {
            for (BattleParticipant bp : team2) {
                if (bp instanceof OfflineParticipant) {
                    b = false;
                    break;
                }
            }
        }
        if (b){
            throw new RuntimeException("There must be an object that is an offline participant");
        }

        OfflineBattleCtrl battle = new OfflineBattleCtrl(team1, team2, rules);
        Iterator<BattleParticipant> var4 = battle.participants.iterator();

        BattleParticipant bp2;
        while(var4.hasNext()) {
            bp2 = var4.next();
            if (bp2 instanceof PlayerParticipant && BattleRegistry.getBattle(((PlayerParticipant)bp2).player) != null) {
                return null;
            }

            PartyStorage party = bp2.getStorage();
            if (party != null && party.findOne((pokemon) -> {
                return pokemon.getPixelmonIfExists() != null && pokemon.getPixelmonIfExists().isEvolving();
            }) != null) {
                return null;
            }
        }

        if (battle.participants.size() == 2) {
            BattleParticipant bp1 = battle.participants.get(0);
            bp2 = battle.participants.get(1);
            if (bp1 instanceof PlayerParticipant && bp2 instanceof TrainerParticipant || bp2 instanceof PlayerParticipant && bp1 instanceof TrainerParticipant) {
                NPCEvent.StartBattle npcStartBattleEvent = new NPCEvent.StartBattle(battle.participants);
                Pixelmon.EVENT_BUS.post(npcStartBattleEvent);
                if (npcStartBattleEvent.isCanceled()) {
                    return null;
                }
            }
        }

        BattleStartedEvent battleStartedEvent = new BattleStartedEvent(battle, team1, team2);
        Pixelmon.EVENT_BUS.post(battleStartedEvent);
        if (battleStartedEvent.isCanceled()) {
            return null;
        } else {
            BattleRegistry.registerBattle(battle);

            for (PlayerParticipant playerParticipant : battle.getPlayers()) {
                BattleParticipant p = playerParticipant;
                EntityPlayerMP player = (EntityPlayerMP) p.getEntity();

                for (BattleParticipant p2 : battle.participants) {
                    if (p2 != p) {
                        PixelmonWrapper pix = p2.allPokemon[0];
                        PlayerPartyStorage storage = Pixelmon.storageManager.getParty(player.func_110124_au());
                        if (!Pixelmon.EVENT_BUS.post(new PokedexEvent(player.func_110124_au(), pix.pokemon, EnumPokedexRegisterStatus.seen, "pokedexKey"))) {
                            storage.pokedex.set(pix.pokemon, EnumPokedexRegisterStatus.seen);
                            storage.pokedex.update();
                            if (player != null) {
                                storage.pokedex.update();
                            }
                        }
                    }
                }
            }

            return battle;
        }
    }

    public static OfflineBattleCtrl startBattle(BattleParticipant[] team1, BattleParticipant[] team2, EnumBattleType type) {
        return startBattle(team1, team2, new BattleRules(type));
    }

    public static OfflineBattleCtrl startBattle(BattleParticipant team1, BattleParticipant team2) {
        return startBattle(new BattleParticipant[]{team1}, new BattleParticipant[]{team2}, new BattleRules(EnumBattleType.Single));
    }
}
