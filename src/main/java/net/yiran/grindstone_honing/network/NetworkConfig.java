package net.yiran.grindstone_honing.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.yiran.grindstone_honing.Config;

public class NetworkConfig {
    public static boolean ignoreEnchantment = false;
    @SubscribeEvent
    public static void onLogged(PlayerEvent.PlayerLoggedInEvent event){
        new ConfigMessage(Config.IgnoreUnbreakingEnchantment.get()).sendToClient((ServerPlayer) event.getEntity());
    }
}
