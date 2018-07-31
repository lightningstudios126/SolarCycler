package lightningstudios.solarcycler.tile;

import lightningstudios.solarcycler.item.ItemSunstone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySolarCycler extends TileEntity {
    public String NBT_TIME = "TargetTime";
    public String NBT_INVENTORY = "Inventory";
    
    private int targetTime = 5678;
    private ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        
        @Override
        protected void onContentsChanged(int slot) {
            TileEntitySolarCycler.this.markDirty();
        }
        
        // make slot even more restrictive by copying overriding the insert function
        // this will prevent automation systems from inserting non-sunstone items in here
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (stack.isEmpty()) return ItemStack.EMPTY;
            validateSlotIndex(slot);
            if (stack.isEmpty())
                return ItemStack.EMPTY;
            
            validateSlotIndex(slot);
            
            if (!(stack.getItem() instanceof ItemSunstone))
                return stack;
            
            return super.insertItem(slot, stack, simulate);
        }
    };
    
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(NBT_TIME))
            targetTime = compound.getInteger(NBT_TIME);
        if (compound.hasKey(NBT_INVENTORY))
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag(NBT_INVENTORY));
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(NBT_TIME, targetTime);
        compound.setTag(NBT_INVENTORY, itemStackHandler.serializeNBT());
        return compound;
    }
    
    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return super.hasCapability(capability, facing);
    }
    
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        return super.getCapability(capability, facing);
    }
    
    public int getTargetTime() {
        return targetTime;
    }
}
