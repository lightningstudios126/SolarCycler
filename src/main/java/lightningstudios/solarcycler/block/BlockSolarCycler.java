package lightningstudios.solarcycler.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockSolarCycler extends BlockBase{
    
    public BlockSolarCycler(String name) {
        super(Material.IRON, name);
    }
  
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        worldIn.setWorldTime(24000L);
        return true;
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
}
