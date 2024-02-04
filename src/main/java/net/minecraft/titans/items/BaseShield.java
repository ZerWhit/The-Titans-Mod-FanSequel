package net.minecraft.titans.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.titans.TheTitans;

public class BaseShield extends ItemShield 
{
	public BaseShield (int durability) 
	{
		super();
		this.setMaxDamage(durability);
		this.setCreativeTab(TheTitans.TAB_COMBAT);
	}
	
	public BaseShield () 
	{
		super();
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
    {
		return I18n.format(this.getUnlocalizedName() + ".name");
    }
}