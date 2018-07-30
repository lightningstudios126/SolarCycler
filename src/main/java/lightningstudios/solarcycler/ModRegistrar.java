package lightningstudios.solarcycler;

import lightningstudios.solarcycler.block.BlockBase;
import lightningstudios.solarcycler.block.BlockSolarCycler;
import lightningstudios.solarcycler.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class ModRegistrar {
    
    public static ArrayList<ItemBase> itemsToRegister = new ArrayList<>();
    public static ArrayList<BlockBase> blocksToRegister = new ArrayList<>();
    
    public static Item sunstone = new ItemBase("sunstone").setCreativeTab(CreativeTabs.MATERIALS);
    public static Block block_sunstone = new BlockBase(Material.IRON, "block_sunstone").setLightLevel(1.0F).setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static Block solar_cycler = new BlockSolarCycler("solar_cycler").setLightLevel(1.0F).setCreativeTab(CreativeTabs.REDSTONE);
    
    public static void addItemToRegister(ItemBase itemBase) {
        itemsToRegister.add(itemBase);
    }
    
    public static void addBlockToRegister(BlockBase blockBase) {
        blocksToRegister.add(blockBase);
    }
    
    public static void registerItems(IForgeRegistry<Item> registry) {
        for (ItemBase item : itemsToRegister)
            registry.register(item);
        for (BlockBase block : blocksToRegister)
            registry.register(block.createItemBlock());
    }
    
    public static void registerBlocks(IForgeRegistry<Block> registry) {
        for (BlockBase block : blocksToRegister)
            registry.register(block);
    }
    
    public static void registerModels() {
        for (ItemBase item : itemsToRegister)
            item.registerItemModel();
        for (BlockBase block : blocksToRegister)
            block.registerItemModel();
    }
}
