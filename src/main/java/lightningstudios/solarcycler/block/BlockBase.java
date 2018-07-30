package lightningstudios.solarcycler.block;

import lightningstudios.solarcycler.ModRegistrar;
import lightningstudios.solarcycler.SolarCycler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block {
    
    protected String name;
    
    public BlockBase(Material material, String name) {
        super(material);
        this.name = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        ModRegistrar.addBlockToRegister(this);
    }
    
    public void registerItemModel() {
        SolarCycler.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, name);
    }
    
    public Item createItemBlock() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }
    
    @Override
    public BlockBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}
