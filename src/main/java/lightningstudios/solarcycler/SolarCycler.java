package lightningstudios.solarcycler;

import lightningstudios.solarcycler.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = SolarCycler.MODID, name = SolarCycler.MODNAME, version = SolarCycler.VERSION, useMetadata = true)
public class SolarCycler {
    public static final String MODID = "solarcycler";
    public static final String MODNAME = "Solar Cycler";
    public static final String VERSION = "1.0";
    
    @SidedProxy(clientSide = "lightningstudios.solarcycler.proxy.ClientProxy", serverSide = "lightningstudios.solarcycler.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @Mod.Instance
    public static SolarCycler instance;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new ModRegistrar());
        ModRegistrar.registerMessages();
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        ModRegistrar.registerOreDictionary();
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
    
    
    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModRegistrar.registerItems(event.getRegistry());
        }
    
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModRegistrar.registerBlocks(event.getRegistry());
        }
    
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModRegistrar.registerModels();
        }
    
    }
}
