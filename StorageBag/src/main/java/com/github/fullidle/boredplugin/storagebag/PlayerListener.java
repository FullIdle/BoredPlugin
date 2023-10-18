package com.github.fullidle.boredplugin.storagebag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    /*事件部分*/
    @EventHandler
    public void playerOpen(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack == null || !StorageBagHolder.isStorageBag(itemStack)){
            return;
        }
        e.setCancelled(true);
        StorageBagHolder st = new StorageBagHolder(itemStack,player);
        player.openInventory(st.getInventory());
    }
    @EventHandler
    public void onCloseInv(InventoryCloseEvent e){
        if (!(e.getInventory().getHolder() instanceof StorageBagHolder)) {
            return;
        }
        StorageBagHolder holder = (StorageBagHolder) e.getInventory().getHolder();
        /*正式内容*/
        holder.saveInv();
    }

    @EventHandler
    public void onClickItem(InventoryClickEvent e){
        if (!(e.getInventory().getHolder() instanceof StorageBagHolder)) {
            return;
        }
        if (e.getSlot() == -999)return;
        StorageBagHolder holder = (StorageBagHolder) e.getInventory().getHolder();
        if (holder.getItemStack().equals(e.getCurrentItem())){
            e.setCancelled(true);
        }
    }
}
