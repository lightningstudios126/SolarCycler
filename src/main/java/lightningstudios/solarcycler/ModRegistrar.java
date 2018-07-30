package lightningstudios.solarcycler;

import lightningstudios.solarcycler.block.BlockBase;
import lightningstudios.solarcycler.item.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class ModRegistrar {
    
    public static ArrayList<ItemBase> itemsToRegister = new ArrayList<>();
    public static ArrayList<BlockBase> blocksToRegister = new ArrayList<>();
    
    public static Item sunstone = new ItemBase("sunstone");
    public static Block block_sunstone = new BlockBase(Material.IRON, "block_sunstone");
    
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
