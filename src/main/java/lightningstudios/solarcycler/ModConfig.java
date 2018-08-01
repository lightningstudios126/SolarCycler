package lightningstudios.solarcycler;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = SolarCycler.MODID)
public class ModConfig {
    @Config.Comment("Fuel usage mode.")
    public static EnumFuelMode fuelMode = EnumFuelMode.PER_TIME_UNIT;
    
    @Config.Comment("The amount of fuel used per operation or per time unit defined elsewhere.")
    @Config.RangeInt(min = 0, max = 64)
    public static int usageRate = 1;
    
    @Config.Comment({"The time unit defined in ticks.", "1000t is 1 in-game hour, 24000t is 1 in-game day."})
    @Config.RangeInt(min = 1, max = 24000)
    public static int timeUnit = 2000;
    
    @Config.Comment("Strike fake lightning at the Solar Cycler on operation.")
    public static boolean doLightningEffect = true;
    
    @Config.Comment("Does the Solar Cycler need direct access to the sky?")
    public static boolean needsSky = true;
    
    public enum EnumFuelMode {
        PER_OPERATION, PER_TIME_UNIT
    }
    
    @Mod.EventBusSubscriber(modid = SolarCycler.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(SolarCycler.MODID))
                ConfigManager.sync(SolarCycler.MODID, Config.Type.INSTANCE);
        }
    }
}
