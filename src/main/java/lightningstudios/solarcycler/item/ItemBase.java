package lightningstudios.solarcycler.item;

import lightningstudios.solarcycler.ModRegistrar;
import lightningstudios.solarcycler.SolarCycler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    
    protected String name;
    
    public ItemBase(String name) {
        this.name = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        ModRegistrar.addItemToRegister(this);
    }
    
    public void registerItemModel() {
        SolarCycler.proxy.registerItemRenderer(this, 0, name);
    }
    
    @Override
    public ItemBase setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }
}


