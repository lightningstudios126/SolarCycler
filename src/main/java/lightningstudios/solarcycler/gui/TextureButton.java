package lightningstudios.solarcycler.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.ArrayList;

public class TextureButton extends GuiButton {
    private final ResourceLocation resource;
    public int textureX, textureY;
    public String hoverText;
    
    public TextureButton(ResourceLocation resource, int ID, int x, int y, int textureX, int textureY, int width, int height, String hoverText) {
        super(ID, x, y, width, height, "");
        this.resource = resource;
        this.textureX = textureX;
        this.textureY = textureY;
        this.hoverText = hoverText;
    }
    
    public TextureButton(ResourceLocation resource, int ID, int x, int y, int textureX, int textureY, int width, int height) {
        this(resource, ID, x, y, textureX, textureY, width, height, "");
    }
    
    @Override
    public void drawButton(Minecraft minecraft, int mx, int my, float partialTicks) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(this.resource);
            
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            
            this.hovered = mx >= this.x && my >= this.y && mx < this.x + this.width && my < this.y + this.height;
            int hoverState = this.getHoverState(this.hovered) + 1;
            
            this.drawTexturedModalRect(this.x, this.y, this.textureX, this.textureY - this.height + this.height * hoverState, this.width, this.height);
            this.mouseDragged(minecraft, mx, my);
        }
    }
    
    public void drawHover(int x, int y) {
        if (this.isMouseOver()) {
            Minecraft minecraft = Minecraft.getMinecraft();
            ArrayList<String> strings = new ArrayList<>(1);
            strings.add(hoverText);
            GuiUtils.drawHoveringText(strings, x, y, minecraft.displayWidth, minecraft.displayHeight, -1, minecraft.fontRenderer);
        }
    }
}
