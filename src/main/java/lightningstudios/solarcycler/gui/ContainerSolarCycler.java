package lightningstudios.solarcycler.gui;

import lightningstudios.solarcycler.tile.TileEntitySolarCycler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class ContainerSolarCycler extends Container {
    private static int SLOT_SPACING = 18;
    
    private TileEntitySolarCycler tile;
    
    public ContainerSolarCycler(final TileEntitySolarCycler tile, IInventory playerInventory) {
        this.tile = tile;
        
        IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        addSlotToContainer(new SlotSunstone(inventory, 0, 80, 39) {
            @Override
            public void onSlotChanged() {
                tile.markDirty();
            }
        });
        addPlayerSlots(playerInventory);
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    
    private void addPlayerSlots(IInventory playerInventory) {
        final int xoff = 8;
        final int yoff = 84;
    
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, xoff + col * SLOT_SPACING, yoff + row * SLOT_SPACING));
    
        for (int i = 0; i < 9; i++)
            this.addSlotToContainer(new Slot(playerInventory, i, xoff + i * SLOT_SPACING, 3 * SLOT_SPACING + yoff + 4));
    }
    
    // taken from shadowfacts's 1.12 modding tutorial for TileEntity w/Inventory+GUI
    // https://shadowfacts.net/tutorials/forge-modding-112/tile-entities-inventory-gui/
    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        
        if (slot != null && slot.getHasStack()) {
            ItemStack orig = slot.getStack();
            copy = orig.copy();
            
            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();
    
            if (index < containerSlots)
                if (!this.mergeItemStack(orig, containerSlots, inventorySlots.size(), true))
                    return ItemStack.EMPTY;
                else if (!this.mergeItemStack(orig, 0, containerSlots, false))
                return ItemStack.EMPTY;
    
            if (orig.getCount() == 0)
                slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
    
            if (orig.getCount() == copy.getCount())
                return ItemStack.EMPTY;
            slot.onTake(player, orig);
        }
        return copy;
    }
    
    public TileEntitySolarCycler getTile() {
        return tile;
    }
}
