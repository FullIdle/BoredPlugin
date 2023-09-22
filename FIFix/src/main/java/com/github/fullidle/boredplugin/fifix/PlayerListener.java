package com.github.fullidle.boredplugin.fifix;

import com.aystudio.core.forge.event.ForgeEvent;
import com.github.fullidle.boredplugin.data.CommonData;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.PokeballImpactEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.BattleRegistry;
import com.pixelmonmod.pixelmon.battles.controller.BattleControllerBase;
import com.pixelmonmod.pixelmon.battles.controller.participants.PixelmonWrapper;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.entities.EntityDen;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static com.github.fullidle.boredplugin.fifix.Main.main;

public class PlayerListener implements Listener {
    @EventHandler
    public void onForge(ForgeEvent event){
        /*<-碰撞的事件->*/
        if (event.getForgeEvent() instanceof PokeballImpactEvent){
            PokeballImpactEvent e = (PokeballImpactEvent) event.getForgeEvent();
            /*判断是否是空球，也就是球有没有精灵,R和右键出去的区别*/
            if (!e.isEmptyBall) {
                /*判断是否砸中生物,且属于宝可梦*/
                if ((e.getEntityHit()) == null || !(e.getEntityHit() instanceof EntityPixelmon)) {return;}
                EntityPixelmon ep = (EntityPixelmon) e.getEntityHit();
                Player p = (Player) e.pokeball.field_70192_c.getBukkitEntity();
                PlayerPartyStorage pps = Pixelmon.storageManager.getParty(p.getUniqueId());
                /*判断技能是否为空的bug*/
                for (Pokemon pokemon : pps.getAll()) {
                    if (pokemon != null) {
                        if (pokemon.getMoveset().isEmpty()) {
                            e.setCanceled(true);
                            p.sendMessage(main.getConfig(CommonData.SubPlugin.FIFIX,"config.yml").getConfiguration().getString("SkillIsEmpty",true));
                            return;
                        }
                    }
                }
                /*在主线程内执行*/
                Bukkit.getScheduler().runTask(main,()->{
                    BattleControllerBase bc = ep.battleController;
                    /*判断是否有对战*/
                    if (bc != null) {
                        if (p.getPlayer() == null) {
                            return;
                        }
                        /*判断这个对战是否有投掷者,有就不管了(被判断为了第一个进行对战的了)*/
                        for (PlayerParticipant player : bc.getPlayers()) {
                            if (player.player.getBukkitEntity().getUniqueId().equals(p.getUniqueId())) {
                                return;
                            }
                        }
                        /*如果没有投掷者,就获取投掷者的对局,对局不存在就不管*/
                        BattleControllerBase battle = BattleRegistry.getBattle(((CraftPlayer) p).getHandle());
                        if (battle == null) {
                            return;
                        }
                        /*对局存在,获取他对局中的对手是否是投掷到的对象,是就结束并提示*/
                        PlayerParticipant par = battle.getPlayer(p.getName());
                        if (par.player.getBukkitEntity().getUniqueId().equals(p.getUniqueId())) {
                            for (PixelmonWrapper wrapper : par.getOpponentPokemon()) {
                                if (wrapper.pokemon.getUUID().equals(ep.getBukkitEntity().getUniqueId())) {
                                    p.sendMessage(main.getConfig(CommonData.SubPlugin.FIFIX,"config.yml").getConfiguration().getString("ThatPokemonIsAlreadyFighting",true));
                                    battle.endBattle();
                                }
                            }
                        }
                    }
                });
            }
        }
    }
    /*<-原版的事件->*/
    @EventHandler
    public void onInteractiveBlocks(PlayerInteractEntityEvent e){
        if (!(((CraftEntity) e.getRightClicked()).getHandle() instanceof EntityDen)){
            return;
        }
        Player player = e.getPlayer();
        PlayerPartyStorage pps = Pixelmon.storageManager.getParty(player.getUniqueId());
        for (Pokemon pokemon : pps.getAll()) {
            if (pokemon != null) {
                if (pokemon.getMoveset().isEmpty()) {
                    player.sendMessage(main.getConfig(CommonData.SubPlugin.FIFIX,"config.yml").getConfiguration().getString("SkillIsEmpty",true));
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
