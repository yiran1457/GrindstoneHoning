package net.yiran.grindstone_honing.core.mixins;

import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
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
        buffer.writeInt(Objects.requireNonNullElse(min, -1));
        buffer.writeInt(Objects.requireNonNullElse(max, -1));
        ci.cancel();
    }

    @Inject(method = "fromBuffer", at = @At("HEAD"), cancellable = true)
    private static void fromBuffer(FriendlyByteBuf buffer, CallbackInfoReturnable<IntegerPredicate> cir) {
        int tierMin = buffer.readInt();
        int tierMax = buffer.readInt();
        cir.setReturnValue(tierMin != -1 || tierMax != -1
                ? new IntegerPredicate(tierMin != -1 ? tierMin : null, tierMax != -1 ? tierMax : null)
                : null
        );
    }


}
