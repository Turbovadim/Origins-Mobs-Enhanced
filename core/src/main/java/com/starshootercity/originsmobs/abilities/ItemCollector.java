package com.starshootercity.originsmobs.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ItemCollector implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You have a larger item pickup radius.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Item Collector", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("moborigins:item_collector");
    }

    private void pickUpItem(Player player, Item item) {
        EntityPickupItemEvent pickupItemEvent = new EntityPickupItemEvent(player, item, 0);
        if (!pickupItemEvent.callEvent()) return;
        Map<Integer, ItemStack> remainingItems = player.getInventory().addItem(item.getItemStack());
        if (remainingItems.isEmpty()) {
            player.playPickupItemAnimation(item);
            item.remove();
        } else {
            for (Integer i : remainingItems.keySet()) {
                int remaining = item.getItemStack().getAmount() - remainingItems.get(i).getAmount();
                player.playPickupItemAnimation(item, remaining);
                item.setItemStack(remainingItems.get(i));
            }
        }
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        //      TODO() optimize this function
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                List<Entity> entities = player.getNearbyEntities(2.5, 2.5, 2.5);
                for (Entity entity : entities) {
                    if (entity instanceof Item item) {
                        if (!item.canPlayerPickup()) continue;
                        if (item.getPickupDelay() > 0) continue;
                        pickUpItem(player, item);
                    }
                }
            });
        }
    }
}
