package lightningstudios.solarcycler.block;

import lightningstudios.solarcycler.ModRegistrar;
import lightningstudios.solarcycler.SolarCycler;
import lightningstudios.solarcycler.tile.TileEntitySolarCycler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BlockSolarCycler extends BlockBase {
    
    public static long ticksPerDay = 24000L;
    public static int timeToSet = 6000;
    
    public BlockSolarCycler(String name) {
        super(Material.IRON, name);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileEntitySolarCycler))
            return false;
        if (!playerIn.isSneaking()) {
            playerIn.openGui(SolarCycler.instance, ModRegistrar.GUI_SOLAR_CYCLER, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
    
    void setTime(World worldIn, int time) {
        // skips to the next day at the specified time
        worldIn.setWorldTime((worldIn.getWorldTime() / ticksPerDay + 1) * ticksPerDay + time);
    }
    
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        if (side == null) return false;
        switch (side) {
            case DOWN:
            case UP:
                return false;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                return true;
        }
        return false;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
    
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntitySolarCycler();
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntitySolarCycler tile = ((TileEntitySolarCycler) worldIn.getTileEntity(pos));
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
            worldIn.spawnEntity(item);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
