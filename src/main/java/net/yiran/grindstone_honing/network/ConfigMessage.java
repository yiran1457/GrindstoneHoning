package net.yiran.grindstone_honing.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.yiran.grindstone_honing.GrindstoneHoning;

import java.util.function.Supplier;

public class ConfigMessage {
    public boolean ignore;

    private ConfigMessage() {

    }

    public ConfigMessage(boolean ignore) {
        this.ignore = ignore;
    }

    public void toBuffer(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(ignore);
    }

    public static ConfigMessage fromBuffer(FriendlyByteBuf friendlyByteBuf) {
        ConfigMessage configMessage = new ConfigMessage();
        configMessage.ignore = friendlyByteBuf.readBoolean();
        return configMessage;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            NetworkConfig.ignoreEnchantment = ignore;
        });
        context.get().setPacketHandled(true);
    }

    public void sendToClient(ServerPlayer player) {
        GrindstoneHoning.NETWORK.sendTo(this,player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}
