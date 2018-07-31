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
    public String NBT_BUTTON = "SelectedButton";
    public String NBT_TIME = "TargetTime";
    public String NBT_INVENTORY = "Inventory";
    public String NBT_POWER = "Powered";
    
    private int targetTime = 6000;
    private boolean redstonePower = false;
    private EnumButtons selectedButton = EnumButtons.NOON;
    
    private ItemStackHandler itemStackHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntitySolarCycler.this.markDirty();
        }
    
        // make slot even more restrictive by overriding the insert function
        // this will prevent automation systems from inserting non-sunstone items in here
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
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
        if (compound.hasKey(NBT_POWER))
            redstonePower = compound.getBoolean(NBT_POWER);
        if (compound.hasKey(NBT_BUTTON))
            selectedButton = EnumButtons.values()[compound.getInteger(NBT_BUTTON)];
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger(NBT_TIME, targetTime);
        compound.setTag(NBT_INVENTORY, itemStackHandler.serializeNBT());
        compound.setBoolean(NBT_POWER, redstonePower);
        compound.setInteger(NBT_BUTTON, selectedButton.ordinal());
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
    
    public boolean getRedstone() {
        return redstonePower;
    }
    
    public void setRedstonePower(boolean redstonePower) {
        this.redstonePower = redstonePower;
        this.markDirty();
    }
    
    public void setTargetTime(int time) {
        this.targetTime = time;
        this.markDirty();
    }
    
    public EnumButtons getSelectedButton() {
        return selectedButton;
    }
    
    public void setSelectedButton(EnumButtons button) {
        this.selectedButton = button;
        this.markDirty();
    }
    
    public void updateButton(EnumButtons buttonPressed) {
        setTargetTime(buttonPressed.time);
        setSelectedButton(buttonPressed);
    }
    
    public enum EnumButtons {
        SUNRISE(0), NOON(6000), SUNSET(12000), MIDNIGHT(18000);
        public int time;
        
        EnumButtons(int time) {
            this.time = time;
        }
    }
}
