package me.njbizzle.firstmod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.function.Supplier;

import static me.njbizzle.firstmod.FirstMod.ModBlocks.registerBlock;

// The value here should match an entry in the META-INF/mods.toml file

@Mod(FirstMod.MODID)
public class FirstMod
{
// Define mod id in a common place for everything to reference
public static final String MODID = "firstmod";
// Directly reference a slf4j logger
private static final Logger LOGGER = LogUtils.getLogger();
// Create a Deferred Register to hold Blocks which will all be registered under the "firstmod" namespace
public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
// Create a Deferred Register to hold Items which will all be registered under the "firstmod" namespace
public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
// Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "firstmod" namespace
public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


public static final RegistryObject<Item> ENDERITE_BAR = ITEMS.register("enderite_bar",
        () -> new Item(new Item.Properties().stacksTo(64)));
public static final RegistryObject<Item> RAW_ENDERITE = ITEMS.register("raw_enderite",
        () -> new Item(new Item.Properties().stacksTo(64)));
public static final RegistryObject<Item> ENDERITE_PLATING = ITEMS.register("enderite_plating",
        () -> new Item(new Item.Properties().stacksTo(64)));






public static final RegistryObject<Block> ENDERITE_ORE = registerBlock("enderite_ore");

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FirstMod.MODID);

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, Supplier<T> block) {
        return FirstMod.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties()));
    }
    public static <T extends Block> RegistryObject<T> registerBlock(String name) {
        RegistryObject<T> toReturn = BLOCKS.register(name, );
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}




    // Creates a creative tab with the id "firstmod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ENDERITE_BAR.get().getDefaultInstance())
            .displayItems(
                    (parameters, output) -> {
//                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
//                output.accept(EXAMPLE_BLOCK_ITEM.get());
                        output.accept(ENDERITE_BAR.get());
                        output.accept(RAW_ENDERITE.get());
                        output.accept(ENDERITE_PLATING.get());
                    }
            )
            .build());

    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);


        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

//        if (Config.logDirtBlock)
//            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
//
//        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
            event.accept(RAW_ENDERITE);
            event.accept(ENDERITE_PLATING);
            event.accept(ENDERITE_BAR);
            event.accept(ENDERITE_ORE);
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}

