package net.minecraft.titans.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.api.variants.IVariedMob;
import net.minecraft.titans.api.variants.types.EnumMobTier;
import net.minecraft.titans.api.variants.types.IMobTier;
import net.minecraft.titans.entity.model.ModelZombieVariant;
import net.minecraft.titans.entity.monster.EntityZombieVariant;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderZombieVariant extends RenderZombie {
    public static final ResourceLocation[] UNDEAD_TEXTURE_LIST = new ResourceLocation[] {
            new ResourceLocation("textures/entity/zombie/zombie.png"), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/golden_guard.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie1.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie2.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie3.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie4.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie5.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie6.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie7.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie8.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie9.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/zombie10.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/ghoul_zombie.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/wight_zombie.png")), new ResourceLocation(
            TheTitans.getTextures("textures/entities", "mobs/ender_zombie.png"))
    };
    private ModelZombieVariant eyes = new ModelZombieVariant(0.01F, true);
    private ModelZombieVariant overlay = new ModelZombieVariant(0.15F, true);
    private ModelZombieVariant overlayShoes = new ModelZombieVariant(0.15F, true);
    private ModelZombieVariant overlayHerald = new ModelZombieVariant(0.151F, true);
    public RenderZombieVariant(RenderManager p_i47204_1_)
    {
        super(p_i47204_1_);
        this.mainModel = new ModelZombieVariant();
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(EntityZombie entitylivingbaseIn, float partialTickTime)
    {
        EntityZombieVariant variedMob = (EntityZombieVariant) entitylivingbaseIn;
        float f = (variedMob.getMultiplier() >= EnumMobTier.getMultiplierByID(7) ? 1.05F : 1.0F);
        GlStateManager.scale(f, f, f);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityZombie entity)
    {
        return UNDEAD_TEXTURE_LIST[((EntityZombieVariant)entity).getVariant()];
    }

    @Override
    public void doRender(EntityZombie entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void renderModel(EntityZombie entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        overlay.swingProgress = mainModel.swingProgress;
        EntityZombieVariant variedMob = (EntityZombieVariant) entitylivingbaseIn;
        if (variedMob.getMultiplier() > EnumMobTier.getMultiplierByID(1)) {
            IMobTier.robesColoring(variedMob);
            bindTexture(new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/overlay/biped_overlay.png")));
            overlay.render(variedMob, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

            IMobTier.shoesColoring(variedMob);
            bindTexture(new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/overlay/biped_overlay_shoes.png")));
            overlayShoes.render(variedMob, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }

        if (variedMob.getMultiplier() > EnumMobTier.getMultiplierByID(4)) {
            bindTexture(new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/overlay/zombie_overlay_herald.png")));
            overlayHerald.render(variedMob, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        }

        if (variedMob.getMultiplier() > EnumMobTier.getMultiplierByID(4)) {
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            float f1 = variedMob.ticksExisted + limbSwingAmount;
            GlStateManager.color((variedMob.getMultiplier() >= EnumMobTier.getMultiplierByID(6)) ? (1.0F - MathHelper.cos(f1 * 0.05F) * 0.2F) : 0.0F, (variedMob.getMultiplier() >= EnumMobTier.getMultiplierByID(6)) ? (1.0F - MathHelper.cos(f1 * 0.05F) * 0.2F) : (0.5F + MathHelper.cos(f1 * 0.01F) * 0.2F), 1.0F - MathHelper.cos(f1 * 0.05F) * 0.2F, 1.0F);
            bindTexture(new ResourceLocation(TheTitans.getTextures("textures/entities", "minions/glow/zombie.png")));
            int c0 = 15728880;
            int j = c0 % 65536;
            int k = c0 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            eyes.render(variedMob, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            c0 = variedMob.getBrightnessForRender();
            j = c0 % 65536;
            k = c0 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
        }
    }
}