package com.github.fullidle.boredplugin.offlinepokectrl.command;

import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineParticipant;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflinePokeCtrlAPI;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartBattleCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()){
            return false;
        }
        if (args.length < 4){
            sender.sendMessage("用法: /offctrltest [离线玩家名] [背包精灵[1-6]] [在线玩家名] [背包精灵[1-6]]");
            return false;
        }
        String name = args[0];
        String name2 = args[2];
        int slot = Integer.parseInt(args[1]);
        int slot2 = Integer.parseInt(args[3]);
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        OfflinePlayer player2 = Bukkit.getOfflinePlayer(name2);
        if (player == null) {
            sender.sendMessage("玩家1不存在");
            return false;
        }
        if (player2 == null) {
            sender.sendMessage("玩家1不存在");
            return false;
        }
        if (player.isOnline()) {
            sender.sendMessage("玩家1不可以是在线玩家");
            return false;
        }
        if (!player2.isOnline()){
            sender.sendMessage("玩家2不在线");
            return false;
        }
        PlayerPartyStorage pps = Pixelmon.storageManager.getParty(player.getUniqueId());
        PlayerPartyStorage pps2 = Pixelmon.storageManager.getParty(player2.getUniqueId());
        Pokemon pokemon = pps.get(slot - 1);
        Pokemon pokemon2 = pps2.get(slot2 - 1);
        EntityPixelmon ep = pokemon.getOrSpawnPixelmon(pps2.getPlayer());
        EntityPixelmon ep2 = pokemon2.getOrSpawnPixelmon(pps2.getPlayer());
        OfflineParticipant olp = new OfflineParticipant(player,ep);
        PlayerParticipant pp = new PlayerParticipant(pps2.getPlayer(),ep2);
        OfflinePokeCtrlAPI.startBattle(olp,pp);
        return false;
    }
}
