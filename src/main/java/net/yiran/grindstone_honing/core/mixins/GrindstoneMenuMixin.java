package net.yiran.grindstone_honing.core.mixins;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.yiran.grindstone_honing.GrindstoneHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.mickelus.tetra.items.modular.IModularItem;

@Mixin(GrindstoneMenu.class)
public class GrindstoneMenuMixin {
    @Mixin(targets = "net/minecraft/world/inventory/GrindstoneMenu$4")
    public abstract static class NewSlot extends Slot {

        @Shadow
        @Final
        GrindstoneMenu this$0;

        public NewSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        @Inject(method = "onTake", at = @At(value = "HEAD"))
        private void onTake(Player player, ItemStack stack, CallbackInfo ci) {
            if (player instanceof ServerPlayer serverPlayer && stack.getItem() instanceof IModularItem modularItem) {
                var inputSlot = this$0.repairSlots;
                var item = inputSlot.getItem(1);
                if (!inputSlot.getItem(0).isEmpty()) return;
                if (!item.getItem().equals(modularItem)) return;
                GrindstoneHandler.showInfo(serverPlayer, item, stack, modularItem);
            }
        }
    }
}
