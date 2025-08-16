package net.yiran.grindstone_honing;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.TetraMod;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.module.ItemModuleMajor;
import se.mickelus.tetra.module.improvement.HonePacket;
import se.mickelus.tetra.module.improvement.SettlePacket;

public class GrindstoneHandler {
    @SubscribeEvent
    public static void onPlace(GrindstoneEvent.OnPlaceItem event) {
        if (!event.getTopItem().isEmpty()) return;
        var stack = event.getBottomItem();
        var item = stack.getItem();
        if (!(item instanceof IModularItem iModularItem)) return;
        if (IModularItem.isHoneable(stack)) return;
        var result = getResultItem(stack, iModularItem);
        event.setOutput(result);
        event.setXp(0);
    }

    public static void showInfo(ServerPlayer serverPlayer, ItemStack origin, ItemStack target, IModularItem item) {
        if (!IModularItem.isHoneable(origin) && IModularItem.isHoneable(target)) {
            TetraMod.packetHandler.sendTo(new HonePacket(target), serverPlayer);
        }
        for (String key : item.getMajorModuleKeys(target)) {
            var originLevel = ((ItemModuleMajor) item.getModuleFromSlot(origin, key)).getImprovementLevel(origin, "settled");
            var targetLevel = ((ItemModuleMajor) item.getModuleFromSlot(target, key)).getImprovementLevel(target, "settled");
            if (targetLevel > originLevel) {
                TetraMod.packetHandler.sendTo(new SettlePacket(target, key), serverPlayer);
            }
        }
    }

    public static ItemStack getResultItem(ItemStack stack, IModularItem item) {
        int damage = Integer.MAX_VALUE;
        int i = stack.getEnchantmentLevel(Enchantments.UNBREAKING) + 1;
        if (stack.isDamageableItem()) {
            damage = (stack.getMaxDamage() - 1 - stack.getDamageValue() )* i;
        }
        var progress = Math.min(getHoningProgress(stack, item), damage);
        var result = stack.copy();
        result.setDamageValue(result.getDamageValue() + progress / i);
        item.tickProgression(null, result, progress);
        return result;
    }

    public static int getHoningProgress(ItemStack stack, IModularItem item) {
        var nbt = stack.getOrCreateTag();
        if (!nbt.contains("honing_progress")) return item.getHoningLimit(stack);
        return nbt.getInt("honing_progress");
    }
}
