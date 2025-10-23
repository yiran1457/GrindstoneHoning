package net.yiran.grindstone_honing.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.yiran.grindstone_honing.Config;

public class NetworkConfig {
    public static boolean ignoreEnchantment = false;
    public static boolean useDurability = true;

    public static void initConfig(){
        ignoreEnchantment = Config.IgnoreUnbreakingEnchantment.get();
        useDurability = Config.UseDurability.get();
    }

    @SubscribeEvent
    public static void onLogged(PlayerEvent.PlayerLoggedInEvent event) {
        new ConfigMessage().sendToClient((ServerPlayer) event.getEntity());
    }
}
