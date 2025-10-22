package net.yiran.grindstone_honing;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.ConfigValue<Boolean> IgnoreUnbreakingEnchantment;

    static {
        IgnoreUnbreakingEnchantment = BUILDER
                .comment("设置为true来取消耐久附魔对耐久消耗的减免","Set to true to cancel the durability consumption reduction of durability enchantment","default: false")
                .define("ignoreUnbreakingEnchantment", false);
        SPEC = BUILDER.build();
    }
}
