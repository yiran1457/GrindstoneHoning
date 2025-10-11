package net.yiran.grindstone_honing.core.mixins;

import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.mickelus.tetra.module.schematic.requirement.IntegerPredicate;

import java.util.Objects;

@Mixin(value = IntegerPredicate.class, remap = false)
public class IntegerPredicateMixin {
    @Shadow
    Integer min;

    @Shadow
    Integer max;


    @Inject(method = "toBuffer", at = @At("HEAD"), cancellable = true)
    public void toBuffer(FriendlyByteBuf buffer, CallbackInfo ci) {
        buffer.writeVarInt(Objects.requireNonNullElse(min, Integer.MIN_VALUE));
        buffer.writeVarInt(Objects.requireNonNullElse(max, Integer.MIN_VALUE));
        ci.cancel();
    }
}
