package com.loginmod.events;

import com.loginmod.data.PlayerData;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LoginEvents {
    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            if (!PlayerData.INSTANCE.hasPassword(player.getUUID())) {
                player.sendMessage(new TextComponent("§6[Login] Use /register <senha> <senha> para se registrar!"), player.getUUID());
            } else {
                player.sendMessage(new TextComponent("§6[Login] Use /login <senha> para fazer login!"), player.getUUID());
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerData.INSTANCE.setLoggedOut(event.getPlayer().getUUID());
    }

    // Cancela qualquer movimento do jogador
    @SubscribeEvent
    public void onPlayerMove(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof ServerPlayer player) {
            if (!PlayerData.INSTANCE.isLoggedIn(player.getUUID())) {
                player.setDeltaMovement(0, 0, 0);
            }
        }
    }

    // Impede interações com o mundo
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!PlayerData.INSTANCE.isLoggedIn(event.getPlayer().getUUID())) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onPlayerChat(ServerChatEvent event) {
        if (!PlayerData.INSTANCE.isLoggedIn(event.getPlayer().getUUID())) {
            if (!event.getMessage().startsWith("/login") && !event.getMessage().startsWith("/register")) {
                event.setCanceled(true);
                event.getPlayer().sendMessage(new TextComponent("§c[Login] Você precisa fazer login primeiro!"), event.getPlayer().getUUID());
            }
        }
    }
}
