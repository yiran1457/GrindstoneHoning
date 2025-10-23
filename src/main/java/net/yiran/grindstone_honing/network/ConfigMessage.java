package net.yiran.grindstone_honing.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.yiran.grindstone_honing.Config;
import net.yiran.grindstone_honing.GrindstoneHoning;

import java.util.function.Supplier;

public class ConfigMessage {
    public boolean ignore;
    public boolean useDurability;

    public ConfigMessage() {
        this.ignore = Config.IgnoreUnbreakingEnchantment.get();
        this.useDurability = Config.UseDurability.get();
    }

    public void toBuffer(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(ignore);
        friendlyByteBuf.writeBoolean(useDurability);
    }

    public static ConfigMessage fromBuffer(FriendlyByteBuf friendlyByteBuf) {
        ConfigMessage configMessage = new ConfigMessage();
        configMessage.ignore = friendlyByteBuf.readBoolean();
        configMessage.useDurability = friendlyByteBuf.readBoolean();
        return configMessage;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            NetworkConfig.ignoreEnchantment = ignore;
            NetworkConfig.useDurability = useDurability;
        });
        context.get().setPacketHandled(true);
    }

    public void sendToClient(ServerPlayer player) {
        GrindstoneHoning.NETWORK.sendTo(this,player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
