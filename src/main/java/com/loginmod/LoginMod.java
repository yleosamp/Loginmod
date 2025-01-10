package com.loginmod;

import com.loginmod.data.PlayerData;
import com.loginmod.events.LoginEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("loginmod")
public class LoginMod {
    public static final String MOD_ID = "loginmod";
    
    public LoginMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(new LoginEvents());
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        PlayerData.INSTANCE.loadFromFile();
    }
}
