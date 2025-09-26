package net.yiran.grindstone_honing;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(GrindstoneHoning.MODID)
public class GrindstoneHoning {
    public static final String MODID = "grindstone_honing";
    public DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public RegistryObject<MiniGrindstoneItem> MINI = ITEMS.register("minigrindstone",MiniGrindstoneItem::new);
    public RegistryObject<MiniNetheriteGrindstoneItem> MINI_NETHERITE = ITEMS.register("mininetheritegrindstone",MiniNetheriteGrindstoneItem::new);

    public GrindstoneHoning() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(GrindstoneHandler.class);
    }
}
