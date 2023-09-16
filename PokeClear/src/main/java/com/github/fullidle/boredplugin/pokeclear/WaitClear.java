package com.github.fullidle.boredplugin.pokeclear;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import com.mohistmc.api.event.BukkitHookForgeEvent;
import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.fullidle.boredplugin.pokeclear.Data.*;
public class WaitClear implements Listener {
    int time;
    int time_;
    int wait;
    boolean le;
    boolean ul;
    boolean shiny;
    boolean boss;
    boolean canDeSpawn;
    Map<Integer, String> msg = new HashMap();
    Map<UUID, Long> waitEntity = new HashMap();
    FiPlugin INSTANCE;
    @EventHandler
    public void onSpawn(BukkitHookForgeEvent event) {
        if (event.getEvent() instanceof SpawnEvent) {
            SpawnEvent e = (SpawnEvent) event.getEvent();
            if (e.isCanceled()) {
                return;
            }
            if (e.action.getOrCreateEntity() instanceof PixelmonEntity) {
                PixelmonEntity ep = (PixelmonEntity) e.action.getOrCreateEntity();
                if (!this.boss && ep.isBossPokemon()) {
                    return;
                }
                if (!this.shiny && ep.getPokemon().isShiny()) {
                    return;
                }
                if (!this.le && legendaries.contains(ep.getSpecies().getName())) {
                    return;
                }
                if ((!this.ul && ultrabeasts.contains(ep.getSpecies().getName())) || unClearDexID.contains(ep.getPokemon().getSpecies().getDex())) {
                    return;
                }
                this.waitEntity.put(ep.getBukkitEntity().getUniqueId(),System.currentTimeMillis() + (this.wait * 1000L));
            }
        }
    }

    public WaitClear(FiPlugin INSTANCE) {
        this.INSTANCE = INSTANCE;
        INSTANCE.getServer().getPluginManager().registerEvents(this, INSTANCE);
        load();
        INSTANCE.getServer().getScheduler().scheduleSyncRepeatingTask(INSTANCE, () -> {
            if (this.time <= 0) {
                clearPokemon();
                this.time = this.time_;
                return;
            }
            this.time--;
            if (this.msg.containsKey(this.time) && this.time != 0) {
                Bukkit.getServer().broadcastMessage(this.msg.get(this.time));
            }
        }, 20L, 20L);
        INSTANCE.getLogger().info("成功加载清理模块: WaitClear");
        for (World bukkitWorld : Bukkit.getServer().getWorlds()) {
            if (world.contains(bukkitWorld.getName())) {
                for (Entity entity : bukkitWorld.getEntities()) {
                    net.minecraft.world.entity.Entity en = ((CraftEntity) entity).getHandle();
                    if (en instanceof PixelmonEntity) {
                        PixelmonEntity epm = (PixelmonEntity) en;
                        if (this.ul || !ultrabeasts.contains(epm.getPokemon().getSpecies().getName())) {
                            if (this.le || !legendaries.contains(epm.getPokemon().getSpecies().getName())) {
                                if (this.shiny || !epm.getPokemon().isShiny()) {
                                    if (this.boss || !epm.isBossPokemon()) {
                                        if (!unClearDexID.contains(epm.getPokemon().getSpecies().getDex()) && (this.canDeSpawn || !epm.canDespawn)) {
                                            if (!epm.hasOwner() && epm.battleController == null) {
                                                this.waitEntity.put(epm.getBukkitEntity().getUniqueId(),System.currentTimeMillis() + (this.wait * 1000L));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void load() {
        FileUtil fileUtil = this.INSTANCE.getConfig(CommonData.SubPlugin.POKECLEAR, "config.yml");
        FileConfiguration config = fileUtil.getConfiguration();
        this.time = config.getInt("wait.time.cycle");
        this.time_ = config.getInt("wait.time.cycle");
        this.wait = config.getInt("wait.time.wait");
        this.le = config.getBoolean("wait.le");
        this.ul = config.getBoolean("wait.ul");
        this.shiny = config.getBoolean("wait.shiny");
        this.boss = config.getBoolean("wait.boss");
        this.canDeSpawn = config.getBoolean("wait.canDeSpawn");
        config.getConfigurationSection("wait.message").getKeys(false).forEach(it -> {
            try {
                this.msg.put(Integer.valueOf(Integer.parseInt(it)), config.getString("wait.message." + it).replace("&", "§"));
            } catch (Exception e) {
            }
        });
    }

    public void clearPokemon() {
        int sum = 0;
        int wt = 0;
        for (World bukkitWorld : Bukkit.getServer().getWorlds()) {
            if (world.contains(bukkitWorld.getName())) {
                for (Entity entity : bukkitWorld.getEntities()) {
                    net.minecraft.world.entity.Entity en = ((CraftEntity) entity).getHandle();
                    if (en instanceof PixelmonEntity) {
                        PixelmonEntity epm = (PixelmonEntity) en;
                        if (this.waitEntity.containsKey(epm.getBukkitEntity().getUniqueId())) {
                            if (System.currentTimeMillis() < this.waitEntity.get(epm.getBukkitEntity().getUniqueId())) {
                                if (System.currentTimeMillis() + (this.time_ * 1000L) > this.waitEntity.get(epm.getBukkitEntity().getUniqueId())) {
                                    wt++;
                                }
                            } else if (!epm.hasOwner() && epm.battleController == null) {
                                epm.unloadEntity();
                                sum++;
                            }
                        }
                    }
                }
            }
        }
        Bukkit.getServer().broadcastMessage(this.msg.get(0).replace("%num%", String.valueOf(sum)).replace("%wait%", String.valueOf(wt)));
    }
}
