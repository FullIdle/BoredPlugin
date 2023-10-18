package com.github.fullidle.boredplugin.storagebag;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class StorageBagItemStack{
    protected net.minecraft.server.v1_12_R1.ItemStack itemStack;
    public StorageBagItemStack(ItemStack itemStack){
        if (!StorageBagHolder.isStorageBag(itemStack)) {
            throw new RuntimeException("Must be a storage bag item!");
        }
        this.itemStack = CraftItemStack.asNMSCopy(itemStack);
    }

    /*<-用来获取唯一的物品->*/
    public ItemStack getItemStack(){
        net.minecraft.server.v1_12_R1.ItemStack itemStack1 = this.itemStack.cloneItemStack();
        NBTTagCompound tag = itemStack1.getTag();
        NBTTagCompound bag = (NBTTagCompound) tag.get("FIStorageBag");
        bag.setString("UUID", UUID.randomUUID().toString());
        tag.set("FIStorageBag",bag);
        itemStack1.setTag(tag);
        return CraftItemStack.asBukkitCopy(itemStack1);
    }
}
