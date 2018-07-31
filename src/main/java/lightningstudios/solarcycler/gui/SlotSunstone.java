package lightningstudios.solarcycler.gui;

import lightningstudios.solarcycler.item.ItemSunstone;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotSunstone extends SlotItemHandler {
    
    public SlotSunstone(IItemHandler inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
    }
    
    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() instanceof ItemSunstone && super.isItemValid(stack);
    }
}
