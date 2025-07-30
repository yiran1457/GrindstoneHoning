package net.yiran.grindstone_honing;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GrindstoneHoning.MODID)
public class GrindstoneHoning {
    public static final String MODID = "grindstone_honing";

    public GrindstoneHoning() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(GrindstoneHandler.class);
    }
}
