package lightningstudios.solarcycler.block;

import lightningstudios.solarcycler.ModRegistrar;
import lightningstudios.solarcycler.SolarCycler;
import lightningstudios.solarcycler.tile.TileEntitySolarCycler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
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
import java.util.Random;

public class BlockSolarCycler extends BlockBase {
    
    public static long ticksPerDay = 24000L;
    
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
        // drop inventory on breakage
        TileEntitySolarCycler tile = ((TileEntitySolarCycler) worldIn.getTileEntity(pos));
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty())
            worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack));
        super.breakBlock(worldIn, pos, state);
    }
    
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        updateRedstone(worldIn, pos);
    }
    
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        updateRedstone(worldIn, pos);
    }
    
    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        if (world instanceof World)
            updateRedstone(((World) world), pos);
    }
    
    private void updateRedstone(World worldIn, BlockPos pos) {
        if (!worldIn.isRemote) {
            TileEntity temp = worldIn.getTileEntity(pos);
            if (temp instanceof TileEntitySolarCycler) {
                TileEntitySolarCycler tile = ((TileEntitySolarCycler) temp);
                boolean powered = worldIn.isBlockIndirectlyGettingPowered(pos) > 0;
                boolean wasPowered = tile.getRedstone();
                if (powered && !wasPowered) {
                    worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
                    tile.setRedstonePower(true);
                } else if (!powered && wasPowered)
                    tile.setRedstonePower(false);
            }
        }
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            TileEntity temp = worldIn.getTileEntity(pos);
            if (temp instanceof TileEntitySolarCycler)
                changeTime(worldIn, pos);
        }
    }
    
    void setWorldTime(World worldIn, int time) {
        boolean skipNextDay = worldIn.getWorldTime() % ticksPerDay > time;
        worldIn.setWorldTime((worldIn.getWorldTime() / ticksPerDay + (skipNextDay ? 1 : 0)) * ticksPerDay + time);
    }
    
    private void changeTime(World worldIn, BlockPos pos) {
        setWorldTime(worldIn, ((TileEntitySolarCycler) worldIn.getTileEntity(pos)).getTargetTime());
        EntityLightningBolt bolt = new EntityLightningBolt(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), true);
        worldIn.addWeatherEffect(bolt);
        worldIn.spawnEntity(bolt);
    }
}
