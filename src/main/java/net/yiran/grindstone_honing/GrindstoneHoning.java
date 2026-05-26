package net.yiran.grindstone_honing;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yiran.grindstone_honing.network.ConfigMessage;
import net.yiran.grindstone_honing.network.NetworkConfig;

@SuppressWarnings("removal")
@Mod(GrindstoneHoning.MODID)
public class GrindstoneHoning {
    public static final String MODID = "grindstone_honing";
    public DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public RegistryObject<MiniGrindstoneItem> MINI = ITEMS.register("minigrindstone", MiniGrindstoneItem::new);
    public RegistryObject<MiniNetheriteGrindstoneItem> MINI_NETHERITE = ITEMS.register("mininetheritegrindstone", MiniNetheriteGrindstoneItem::new);

    public static String version = "1.0.0";
    public static SimpleChannel NETWORK = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(GrindstoneHoning.MODID, "config"),
            () -> version,
            version::equals,
            version::equals
    );

    public GrindstoneHoning() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::onBuildCreativeModeTab);
        modEventBus.addListener(this::onCommonSetup);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.register(ClientHandler.class);
        }

        ModLoadingContext.get().registerConfig(
                ModConfig.Type.COMMON,
                Config.SPEC,
                "GrindStoneHoning-server.toml"
        );

        NETWORK.registerMessage(1, ConfigMessage.class,
                ConfigMessage::toBuffer,
                ConfigMessage::fromBuffer,
                ConfigMessage::handle
        );

        MinecraftForge.EVENT_BUS.register(NetworkConfig.class);
        MinecraftForge.EVENT_BUS.register(GrindstoneHandler.class);
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        NetworkConfig.initConfig();
    }

    public void onBuildCreativeModeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("tetra", "default"))) {
            event.accept(MINI.get().getDefaultInstance());
            event.accept(MINI_NETHERITE.get().getDefaultInstance());
        }
    }
}
