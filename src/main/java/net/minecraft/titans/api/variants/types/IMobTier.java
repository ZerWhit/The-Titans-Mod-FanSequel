package net.minecraft.titans.api.variants.types;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.titans.api.variants.IVariedMob;
import net.minecraft.titans.api.variants.types.EnumMobTier;

public interface IMobTier
{
	static void robesColoring(IMobTier minion) {
		switch (minion.getTier()) {
			case PRIEST:
				GlStateManager.color(0.654902F, 0.34509805F, 0.7019608F, 1.0F);
				break;
			case ZEALOT:
				GlStateManager.color(0.007843138F, 0.85490197F, 0.85490197F, 1.0F);
				break;
			case BISHOP:
				GlStateManager.color(0.007843138F, 0.85882354F, 0.007843138F, 1.0F);
				break;
			case TEMPLAR:
				GlStateManager.color(1.0F, 0.007843138F, 0.007843138F, 1.0F);
				break;
			case LORD:
				GlStateManager.color(0.21568628F, 0.21568628F, 0.21568628F, 1.0F);
				break;
			case DEMITITAN:
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				break;
			default:
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	static void shoesColoring(IMobTier minion) {
		switch (minion.getTier()) {
			case TEMPLAR:
				GlStateManager.color(0.0F, 0.047058824F, 1.0F, 1.0F);
				break;
			case LORD:
			case DEMITITAN:
				GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
				break;
			default:
				GlStateManager.color(0.41960785F, 0.41960785F, 0.41960785F, 1.0F);
		}

	}
	public EnumMobTier getTier();
	
	public default float getMultiplier()
	{
		return getTier().getMultiplier();
	}

	public default void setMultiplier(float multiplier)
	{
		getTier().setMultiplier(multiplier);
	}
}
