package net.minecraft.titans.entity.model;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.math.MathHelper;

public class ModelZombieVariant extends ModelZombie {
    public ModelZombieVariant() {
        super(0.0F, false);
    }
    public ModelZombieVariant(float modelSize, boolean texMirror) {
        super(modelSize, texMirror);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        this.bipedHead.rotateAngleZ = netHeadYaw / 114.59155F;
        this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
        float f6 = MathHelper.sin(this.swingProgress * 3.1415927F);
        float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F);
        this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F;
        this.bipedBody.rotateAngleY = 0.0F;
        if (entityIn instanceof EntityCreature) {
            EntityCreature zombie = (EntityCreature)entityIn;
            this.isChild = zombie.isChild();
            float f1 = zombie.limbSwingAmount * 4.0F + f6;
            this.bipedRightArm.rotateAngleX = -((f1 >= 1.6F) ? 1.6F : f1) - f6 * 1.2F - f7 * 0.4F + MathHelper.cos(limbSwing * 0.3331F + 3.1415927F) * 0.5F * limbSwingAmount * 0.5F;
            this.bipedLeftArm.rotateAngleX = -((f1 >= 1.6F) ? 1.6F : f1) - f6 * 1.2F - f7 * 0.4F + MathHelper.cos(limbSwing * 0.3331F) * 0.5F * limbSwingAmount * 0.5F;
        }
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
    }
}
