package com.loginmod.commands;

import com.loginmod.data.PlayerData;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LoginCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
            Commands.literal("register")
                .then(Commands.argument("senha", StringArgumentType.string())
                .then(Commands.argument("confirmarSenha", StringArgumentType.string())
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    String playerName = player.getName().getString();
                    String senha = StringArgumentType.getString(context, "senha");
                    String confirmarSenha = StringArgumentType.getString(context, "confirmarSenha");
                    
                    if (PlayerData.INSTANCE.hasPassword(playerName)) {
                        player.sendMessage(new TextComponent("§c[Login] Você já está registrado!"), player.getUUID());
                        return 0;
                    }
                    
                    if (!senha.equals(confirmarSenha)) {
                        player.sendMessage(new TextComponent("§c[Login] As senhas não coincidem!"), player.getUUID());
                        return 0;
                    }
                    
                    PlayerData.INSTANCE.savePassword(playerName, senha);
                    PlayerData.INSTANCE.setLoggedIn(playerName);
                    player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                    player.sendMessage(new TextComponent("§a[Login] Registro realizado com sucesso!"), player.getUUID());
                    return 1;
                })))
        );
        
        event.getDispatcher().register(
            Commands.literal("login")
                .then(Commands.argument("senha", StringArgumentType.string())
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    String playerName = player.getName().getString();
                    String senha = StringArgumentType.getString(context, "senha");
                    
                    if (!PlayerData.INSTANCE.hasPassword(playerName)) {
                        player.sendMessage(new TextComponent("§c[Login] Você precisa se registrar primeiro!"), player.getUUID());
                        return 0;
                    }
                    
                    if (PlayerData.INSTANCE.checkPassword(playerName, senha)) {
                        PlayerData.INSTANCE.setLoggedIn(playerName);
                        String ip = player.connection.connection.getRemoteAddress().toString();
                        PlayerData.INSTANCE.saveSession(playerName, ip);
                        player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                        player.sendMessage(new TextComponent("§a[Login] Login realizado com sucesso!"), player.getUUID());
                        return 1;
                    } else {
                        player.sendMessage(new TextComponent("§c[Login] Senha incorreta!"), player.getUUID());
                        return 0;
                    }
                }))
        );
        
        event.getDispatcher().register(
            Commands.literal("removepass")
                .requires(source -> source.hasPermission(4))
                .then(Commands.argument("player", EntityArgument.player())
                .executes(context -> {
                    ServerPlayer target = EntityArgument.getPlayer(context, "player");
                    String targetName = target.getName().getString();
                    PlayerData.INSTANCE.removePassword(targetName);
                    context.getSource().sendSuccess(new TextComponent("§a[Login] Senha removida com sucesso!"), true);
                    target.sendMessage(new TextComponent("§c[Login] Sua senha foi removida por um administrador!"), target.getUUID());
                    return 1;
                }))
        );
    }
}
