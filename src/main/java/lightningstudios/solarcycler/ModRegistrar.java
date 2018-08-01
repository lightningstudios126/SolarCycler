package lightningstudios.solarcycler;

import lightningstudios.solarcycler.block.BlockBase;
import lightningstudios.solarcycler.block.BlockSolarCycler;
import lightningstudios.solarcycler.block.BlockSunstone;
import lightningstudios.solarcycler.gui.ContainerSolarCycler;
import lightningstudios.solarcycler.gui.GuiSolarCycler;
import lightningstudios.solarcycler.item.ItemBase;
import lightningstudios.solarcycler.item.ItemSunstone;
import lightningstudios.solarcycler.network.PacketButton;
import lightningstudios.solarcycler.tile.TileEntitySolarCycler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ModRegistrar implements IGuiHandler {
    
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(SolarCycler.MODID);
    
    public static final int GUI_SOLAR_CYCLER = 1;
    
    public static ArrayList<ItemBase> itemsToRegister = new ArrayList<>();
    public static ArrayList<BlockBase> blocksToRegister = new ArrayList<>();
    
    public static Item sunstone = new ItemSunstone("sunstone").setCreativeTab(CreativeTabs.MATERIALS);
    public static Block block_sunstone = new BlockSunstone(Material.IRON, "block_sunstone").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    public static Block solar_cycler = new BlockSolarCycler("solar_cycler").setCreativeTab(CreativeTabs.REDSTONE);
    
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
        GameRegistry.registerTileEntity(TileEntitySolarCycler.class, solar_cycler.getRegistryName());
    }
    
    public static void registerModels() {
        for (ItemBase item : itemsToRegister)
            item.registerItemModel();
        for (BlockBase block : blocksToRegister)
            block.registerItemModel();
    }
    
    public static void registerOreDictionary() {
        OreDictionary.registerOre("gemSunstone", sunstone);
        OreDictionary.registerOre("blockSunstone", block_sunstone);
    }
    
    @SideOnly(Side.CLIENT)
    public static void sendButtonPacket(TileEntity tile, TileEntitySolarCycler.EnumButtons buttonID) {
        INSTANCE.sendToServer(new PacketButton(tile.getPos(), tile.getWorld().provider.getDimension(), buttonID));
    }
    
    public static void registerMessages() {
        INSTANCE.registerMessage(PacketButton.Handler.class, PacketButton.class, 0, Side.SERVER);
    }
    
    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_SOLAR_CYCLER:
                return new GuiSolarCycler(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
    
    @Nullable
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_SOLAR_CYCLER:
                return new ContainerSolarCycler((TileEntitySolarCycler) world.getTileEntity(new BlockPos(x, y, z)), player.inventory);
            default:
                return null;
        }
    }
}
