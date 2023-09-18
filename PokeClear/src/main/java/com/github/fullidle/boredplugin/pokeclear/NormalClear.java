package com.github.fullidle.boredplugin.pokeclear;

import com.github.fullidle.boredplugin.FiPlugin;
import com.github.fullidle.boredplugin.data.CommonData;
import com.github.fullidle.boredplugin.util.FileUtil;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

import static com.github.fullidle.boredplugin.pokeclear.Data.*;

public class NormalClear implements Listener {
    int time;
    boolean le;
    boolean ul;
    boolean shiny;
    boolean boss;
    boolean canDeSpawn;
    Map<Integer, String> msg = new HashMap();

    public NormalClear(FiPlugin INSTANCE) {
        INSTANCE.getServer().getPluginManager().registerEvents(this, INSTANCE);
        load();
        INSTANCE.getServer().getScheduler().scheduleSyncRepeatingTask(INSTANCE, () -> {
            if (this.time <= 0) {
                clearPokemon();
                this.time = INSTANCE.getConfig(CommonData.SubPlugin.POKECLEAR,"config.yml").getConfiguration().getInt("normal.time");
                return;
            }
            this.time--;
            if (this.msg.containsKey(this.time) && this.time != 0) {
                Bukkit.getServer().broadcastMessage(this.msg.get(this.time));
            }
        }, 20L, 20L);
        INSTANCE.getLogger().info("成功加载清理模块: NormalClear");
    }

    public void load() {
        FileUtil fileUtil = CommonData.getMainPlugin().getConfig(CommonData.SubPlugin.POKECLEAR, "config.yml");
        FileConfiguration config = fileUtil.getConfiguration();
        this.time = config.getInt("normal.time");
        this.le = config.getBoolean("normal.le");
        this.ul = config.getBoolean("normal.ul");
        this.shiny = config.getBoolean("normal.shiny");
        this.boss = config.getBoolean("normal.boss");
        this.canDeSpawn = config.getBoolean("normal.canDeSpawn");
        config.getConfigurationSection("normal.message").getKeys(false).forEach(it -> {
            try {
                this.msg.put(Integer.valueOf(Integer.parseInt(it)), config.getString("normal.message." + it).replace("&", "§"));
            } catch (Exception e) {
            }
        });
    }

    public void clearPokemon() {
        int sum = 0;
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
                                        System.out.println("待删除,"+epm.getPokemon().getSpecies().getDex());
                                        if (!unClearDexID.contains(epm.getPokemon().getSpecies().getDex()) && (this.canDeSpawn || !epm.canDespawn)) {
                                            if (!epm.hasOwner() && epm.battleController == null) {
                                                epm.unloadEntity();
                                                sum++;
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
        Bukkit.getServer().broadcastMessage(this.msg.get(0).replace("%num%", String.valueOf(sum)));
    }
}
