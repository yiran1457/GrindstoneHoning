package net.yiran.grindstone_honing;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<Boolean> IgnoreUnbreakingEnchantment;
    public static final ForgeConfigSpec.ConfigValue<Boolean> UseDurability;

    static {
        IgnoreUnbreakingEnchantment = BUILDER
                .comment("设置为true来取消耐久附魔对耐久消耗的减免","Set to true to cancel the durability consumption reduction of durability enchantment","default: false")
                .define("ignoreUnbreakingEnchantment", false);
        UseDurability = BUILDER
                .comment("设置为false，在使用砂轮和迷你砂轮的时候打磨物品不消耗耐久","Set to false, grinding items with the grindstone and miniGrindstone will not consume durability","default: true")
                .define("useDurability", true);
        SPEC = BUILDER.build();
    }
}
