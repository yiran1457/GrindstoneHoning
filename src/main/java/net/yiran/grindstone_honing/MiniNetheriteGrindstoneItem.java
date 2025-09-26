package net.yiran.grindstone_honing;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import se.mickelus.tetra.items.modular.IModularItem;

import javax.annotation.Nullable;
import java.util.List;

import static net.yiran.grindstone_honing.GrindstoneHandler.getHoningProgress;

public class MiniNetheriteGrindstoneItem extends Item {
    public MiniNetheriteGrindstoneItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(Component.translatable("tooltip.miniNetheriteGrindstone.info"));
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (action.equals(ClickAction.SECONDARY)) {
            var target = slot.getItem();
            if (target.getItem() instanceof IModularItem modularItem) {
                var origin = target.copy();
                modularItem.tickProgression(null, target, getHoningProgress(target, modularItem));
                GrindstoneHandler.showInfo(player, origin, target, modularItem);
                return true;
            }
        }
        return super.overrideStackedOnOther(stack, slot, action, player);
    }
}
