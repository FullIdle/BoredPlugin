package com.github.fullidle.boredplugin.offlinepokectrl.command;

import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineBattleCtrl;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineParticipant;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflinePokeCtrlAPI;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class SelectNextPokeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp())return false;
        if (args.length < 1){
            sender.sendMessage("用法: /selectnextpoketest [正在战斗的离线玩家名] [1-6(不填则显示)]");
            return false;
        }
        OfflineBattleCtrl opc = OfflinePokeCtrlAPI.getOfflineBattleCtrl(Bukkit.getOfflinePlayer(args[0]));
        
        if (args.length == 1){
            OfflineParticipant op = opc.getOfflinePlayer(args[0]);
            ArrayList<PixelmonWrapper> clone = (ArrayList<PixelmonWrapper>) op.getTeamPokemon().clone();
            clone.remove(op.numControlledPokemon);
            for (PixelmonWrapper wrapper : clone) {
                sender.sendMessage(clone.indexOf(wrapper)+"."+wrapper.getPokemonName());
            }
        }
        if (args.length > 1){
            OfflineParticipant op = opc.getOfflinePlayer(args[0]);
            int i = Integer.parseInt(args[1]);
            ArrayList<PixelmonWrapper> clone = (ArrayList<PixelmonWrapper>) op.getTeamPokemon().clone();
            clone.remove(op.numControlledPokemon);
            for (PixelmonWrapper wrapper : clone) {
                sender.sendMessage(clone.indexOf(wrapper)+"."+wrapper.getPokemonName());
            }
            op.getBattleAI().setUUIDToBeSelected(clone.get(i).getPokemonUUID());
            op.WaitForTheNextUUIDToBeSelected = true;
        }
        return false;
    }
}
