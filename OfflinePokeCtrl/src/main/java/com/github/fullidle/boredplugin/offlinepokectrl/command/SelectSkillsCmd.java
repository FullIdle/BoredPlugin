package com.github.fullidle.boredplugin.offlinepokectrl.command;

import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineAI;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineBattleCtrl;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflineParticipant;
import com.github.fullidle.boredplugin.offlinepokectrl.api.OfflinePokeCtrlAPI;
import com.pixelmonmod.pixelmon.battles.controller.ai.MoveChoice;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class SelectSkillsCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()){
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage("用法: /selectskilltest [正在对战的离线玩家] [技能[1-4](不写则列出技能名)]");
            return false;
        }
        if (args.length == 1){
            String name = args[0];
            OfflinePlayer player = Bukkit.getOfflinePlayer(name);
            OfflineBattleCtrl ctrl = OfflinePokeCtrlAPI.getOfflineBattleCtrl(player);
            if (ctrl == null) {
                sender.sendMessage("玩家没有参加任何对局");
                return false;
            }
            OfflineParticipant opp = ctrl.getOfflinePlayer(name);
            OfflineAI ai = opp.getBattleAI();
            PixelmonWrapper wrapper = null;
            for (PixelmonWrapper wr : opp.bc.getActivePokemon()) {
                if (wr.getParticipant() == opp){
                    wrapper = wr;
                }
            }
            ArrayList<MoveChoice> aCho = ai.getACho(wrapper);
            for (int i = 0; i < aCho.size(); i++) {
                sender.sendMessage((i+1) + "."+aCho.get(i).attack.getMove().getLocalizedName());
            }
        }
        if (args.length > 1){
            String name = args[0];
            int slot = Integer.parseInt(args[1]);
            OfflinePlayer player = Bukkit.getOfflinePlayer(name);
            OfflineBattleCtrl ctrl = OfflinePokeCtrlAPI.getOfflineBattleCtrl(player);
            if (ctrl == null) {
                sender.sendMessage("玩家没有参加任何对局");
                return false;
            }
            OfflineParticipant opp = ctrl.getOfflinePlayer(name);
            OfflineAI ai = opp.getBattleAI();
            PixelmonWrapper wrapper = null;
            for (PixelmonWrapper wr : opp.bc.getActivePokemon()) {
                if (wr.getParticipant() == opp){
                    wrapper = wr;
                }
            }
            ArrayList<MoveChoice> aCho = ai.getACho(wrapper);
            ai.setToBeUsedMoveChoice(aCho.get(slot-1));
        }
        return false;
    }
}
