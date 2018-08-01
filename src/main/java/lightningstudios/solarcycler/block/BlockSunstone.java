package lightningstudios.solarcycler.block;

import net.minecraft.block.material.Material;

public class BlockSunstone extends BlockBase {
    
    public BlockSunstone(Material material, String name) {
        super(material, name);
        this.setLightLevel(1.0F);
        this.setHardness(3);
        this.setResistance(5);
        this.setHarvestLevel("pickaxe", 1);
    }
}
