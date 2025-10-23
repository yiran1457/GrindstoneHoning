package net.yiran.grindstone_honing.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NetworkConfig {
    public static boolean ignoreEnchantment = false;
    public static boolean useDurability = true;

    @SubscribeEvent
    public static void onLogged(PlayerEvent.PlayerLoggedInEvent event) {
        new ConfigMessage().sendToClient((ServerPlayer) event.getEntity());
    }
}
