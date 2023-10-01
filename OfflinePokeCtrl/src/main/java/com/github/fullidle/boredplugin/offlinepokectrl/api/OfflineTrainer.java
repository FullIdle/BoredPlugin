package com.github.fullidle.boredplugin.offlinepokectrl.api;

import com.pixelmonmod.pixelmon.entities.npcs.NPCTrainer;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.World;
import org.bukkit.OfflinePlayer;

@Getter
public class OfflineTrainer extends NPCTrainer {
    @Setter
    OfflinePlayer offlinePlayer;
    public OfflineTrainer(OfflinePlayer player,World par1World) {
        super(par1World);
        this.offlinePlayer = player;
    }
}
