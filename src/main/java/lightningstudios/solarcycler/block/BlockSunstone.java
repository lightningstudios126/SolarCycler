package lightningstudios.solarcycler.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;

public class BlockSunstone extends BlockBase {
    public BlockSunstone(Material material, String name) {
        super(material, name);
        OreDictionary.registerOre("blockSunstone", this);
    }
}
