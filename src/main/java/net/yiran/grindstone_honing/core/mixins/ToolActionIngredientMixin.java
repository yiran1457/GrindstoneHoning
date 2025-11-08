package net.yiran.grindstone_honing.core.mixins;

import com.google.gson.JsonObject;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.mickelus.tetra.crafting.ToolActionIngredient;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.module.schematic.requirement.IntegerPredicate;

import java.util.stream.Stream;

@Mixin(ToolActionIngredient.class)
public abstract class ToolActionIngredientMixin extends Ingredient {
    protected ToolActionIngredientMixin(Stream<? extends Value> values) {
        super(values);
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void onInit(ToolAction toolAction, IntegerPredicate tier, CallbackInfo ci) {
        var showStack = new ItemStack(Items.DEBUG_STICK);
        this.values = new ItemValue[]{new ItemValue(showStack)};
        StringTag tip = null;
        JsonObject predicate = tier.serialize().getAsJsonObject();
        if (predicate.has("min") && predicate.has("max")) {
            tip = StringTag.valueOf(Component.Serializer.toJson(Component.translatable("tooltip.tetra.toolaction.both", Component.translatable("tetra.tool." + toolAction.name()), predicate.get("min"), predicate.get("max"))));
        } else if (predicate.has("max")) {
            tip = StringTag.valueOf(Component.Serializer.toJson(Component.translatable("tooltip.tetra.toolaction.max", Component.translatable("tetra.tool." + toolAction.name()), predicate.get("max"))));
        } else if (predicate.has("min")) {
            tip = StringTag.valueOf(Component.Serializer.toJson(Component.translatable("tooltip.tetra.toolaction.min", Component.translatable("tetra.tool." + toolAction.name()), predicate.get("min"))));
        }
        var nbt = showStack.getOrCreateTag();
        nbt.putString("tool", toolAction.name());
        var disPlay = nbt.getCompound("display");
        showStack.getTag().put("display", disPlay);

        var lore = disPlay.getList("Lore", ListTag.TAG_STRING);
        lore.add(tip);
        disPlay.put("Lore", lore);
    }
}
