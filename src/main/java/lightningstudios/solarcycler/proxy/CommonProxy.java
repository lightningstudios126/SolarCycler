package lightningstudios.solarcycler.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class CommonProxy {
    
    public void preInit(FMLPreInitializationEvent event) {
    }
    
    public void init(FMLInitializationEvent event) {
    }
    
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    public void registerItemRenderer(Item item, int meta, String id) {
    }
}
