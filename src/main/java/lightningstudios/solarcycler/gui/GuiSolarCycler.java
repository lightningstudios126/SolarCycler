package lightningstudios.solarcycler.gui;

import lightningstudios.solarcycler.ModRegistrar;
import lightningstudios.solarcycler.SolarCycler;
import lightningstudios.solarcycler.tile.TileEntitySolarCycler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiSolarCycler extends GuiContainer {
    
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(SolarCycler.MODID, "textures/gui/solarcycler.png");
    private final TileEntitySolarCycler tile;
    private InventoryPlayer playerInv;
    
    public GuiSolarCycler(Container container, InventoryPlayer playerInv) {
        super(container);
        this.tile = ((ContainerSolarCycler) container).getTile();
        this.playerInv = playerInv;
    }
    
    @Override
    public void initGui() {
        super.initGui();
        int[][] buttonPos = {{57, 37}, {78, 16}, {99, 37}, {78, 58}};
        for (int i = 0; i < 4; i++) {
            TextureButton button = new TextureButton(BG_TEXTURE, i, guiLeft + buttonPos[i][0], guiTop + buttonPos[i][1],
                    i * 20 + 176, 0, 20, 20,
                    I18n.format("buttons.tooltip." + i));
            addButton(button);
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(ModRegistrar.solar_cycler.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, (xSize - fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
    
        String cost = I18n.format("gui.solarcycler.cost") + ":";
        fontRenderer.drawString(cost, 14, 38, 0x404040);
        fontRenderer.drawString(tile.getCost() + "", 14, 48, 0x404040);
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        for (GuiButton button : this.buttonList)
            if (button instanceof TextureButton)
                ((TextureButton) button).drawHover(mouseX, mouseY);
    }
    
    @Override
    public void updateScreen() {
        for (GuiButton button : buttonList) {
            if (button instanceof TextureButton)
                button.enabled = button.id != tile.getSelectedButton().ordinal();
        }
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        ModRegistrar.sendButtonPacket(tile, TileEntitySolarCycler.EnumButtons.values()[button.id]);
        tile.updateButton(TileEntitySolarCycler.EnumButtons.values()[button.id]);
    }
    
}
