package com.loginmod.events;

import com.loginmod.data.PlayerData;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event.Result;

public class LoginEvents {
    
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            String playerName = player.getName().getString();
            
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, Integer.MAX_VALUE, 255, false, false));
            
            if (!PlayerData.INSTANCE.hasPassword(playerName)) {
                player.sendMessage(new TextComponent("§6[Login] Use /register <senha> <senha> para se registrar!"), player.getUUID());
            } else {
                player.sendMessage(new TextComponent("§6[Login] Use /login <senha> para fazer login!"), player.getUUID());
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            String playerName = player.getName().getString();
            PlayerData.INSTANCE.setLoggedOut(playerName);
        }
    }

    @SubscribeEvent
    public void onItemDrop(ItemTossEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            if (!PlayerData.INSTANCE.isLoggedIn(player.getName().getString())) {
                event.setCanceled(true);
                player.getInventory().add(event.getEntityItem().getItem());
                player.sendMessage(new TextComponent("§c[Login] Você precisa fazer login primeiro!"), player.getUUID());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            if (!PlayerData.INSTANCE.isLoggedIn(player.getName().getString())) {
                event.setUseBlock(Result.DENY);
                event.setUseItem(Result.DENY);
                player.sendMessage(new TextComponent("§c[Login] Você precisa fazer login primeiro!"), player.getUUID());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteractItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            if (!PlayerData.INSTANCE.isLoggedIn(player.getName().getString())) {
                event.setCanceled(true);
                player.sendMessage(new TextComponent("§c[Login] Você precisa fazer login primeiro!"), player.getUUID());
            }
        }
    }
}
