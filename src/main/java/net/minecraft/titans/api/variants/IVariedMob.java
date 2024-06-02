package net.minecraft.titans.api.variants;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public interface IVariedMob
{
    void setVariant(int type);
    int getVariant();
}
