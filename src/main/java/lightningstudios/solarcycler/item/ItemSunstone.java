package lightningstudios.solarcycler.item;

import net.minecraftforge.oredict.OreDictionary;

public class ItemSunstone extends ItemBase {
    // literally does nothing so I can use instanceof keyword when checking in the restricted slot
    public ItemSunstone(String name) {
        super(name);
        OreDictionary.registerOre("gemSunstone", this);
    }
}
