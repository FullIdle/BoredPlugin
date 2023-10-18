package com.github.fullidle.boredplugin.storagebag;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import static com.github.fullidle.boredplugin.storagebag.Main.getColorMsg;

public class StorageBagHolder implements InventoryHolder {
    @Getter
    private ItemStack itemStack;
    private final Inventory inv;
    @Getter
    private final Player player;

    public StorageBagHolder(ItemStack itemStack, Player player){
        if (!isStorageBag(itemStack)) {
            throw new RuntimeException("Must be a storage bag item!");
        }
        this.player = player;
        this.itemStack = itemStack;
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound nbt = (NBTTagCompound) item.getTag().get("FIStorageBag");
        int row = nbt.getInt("row");
        String title = itemStack.getItemMeta().getDisplayName();
        if (title == null || title.equals("")){
            title = getColorMsg("defaultBagName");
        }
        inv = Bukkit.createInventory(this, row*9,title);
        NBTTagList list = nbt.getList("inventory",10);
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                NBTTagCompound invItemData = list.get(i);
                int slot = invItemData.getInt("slot");
                NBTTagCompound invItem = invItemData.getCompound("itemStack");
                ItemStack bukkitItem = CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(invItem));
                inv.setItem(slot,bukkitItem);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public void saveInv(){
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(this.itemStack);
        NBTTagCompound tag = item.getTag();
        NBTTagCompound bag = (NBTTagCompound) tag.get("FIStorageBag");
        bag.remove("inventory");
        NBTTagList inventory = bag.getList("inventory", 10);
        for (int i = 0; i < this.inv.getSize(); i++) {
            ItemStack item1 = this.inv.getItem(i);
            if (item1 != null&& !item1.getType().equals(Material.AIR)){
                NBTTagCompound itemData = new NBTTagCompound();
                itemData.setInt("slot",i);
                itemData.set("itemStack",CraftItemStack.asNMSCopy(item1).save(new NBTTagCompound()));
                inventory.add(itemData);
            }
        }
        bag.set("inventory",inventory);
        tag.set("FIStorageBag",bag);
        item.setTag(tag);
        replaceItem(CraftItemStack.asBukkitCopy(item));
    }
    private void replaceItem(ItemStack itemStack){
        int i = this.player.getInventory().first(this.itemStack);
        if (i == -1) {
            throw new RuntimeException("The player's backpack no longer has the original ItemStack!");
        }
        this.player.getInventory().setItem(i,itemStack);
    }

    public static boolean isStorageBag(ItemStack itemStack){
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        if (item.getTag() == null) {
            return false;
        }
        return item.getTag().hasKey("FIStorageBag");
    }
}
