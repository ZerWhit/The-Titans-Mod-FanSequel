package net.minecraft.titans.entity.monster;

import net.endermanofdoom.mac.util.TranslateUtil;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.titans.api.variants.types.EnumMobTier;
import net.minecraft.titans.api.variants.types.IMobTier;
import net.minecraft.titans.api.variants.IVariedMob;
import net.minecraft.world.World;

public class EntityZombieVariant extends EntityZombie implements IVariedMob, IMobTier
{
	private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityZombieVariant.class, DataSerializers.VARINT);
	private static final DataParameter<Float> MULTIPLIER = EntityDataManager.<Float>createKey(EntityZombieVariant.class, DataSerializers.FLOAT);

    public EntityZombieVariant(World worldIn)
	{
		super(worldIn);
	}
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(VARIANT, Integer.valueOf(0));
		this.dataManager.register(MULTIPLIER, Float.valueOf(0));
    }

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Variant", getVariant());
		compound.setFloat("Multiplier", getMultiplier());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setVariant(compound.getInteger("Variant"));
		setMultiplier(compound.getFloat("Multiplier"));
	}

	public int getVariant()
    {
        return ((Integer)this.dataManager.get(VARIANT)).intValue();
    }

    public void setVariant(int time)
    {
        this.dataManager.set(VARIANT, Integer.valueOf(time));
    }

	public float getMultiplier()
	{
		return ((Float)this.dataManager.get(MULTIPLIER)).floatValue();
	}

	public void setMultiplier(float multiplier)
	{
		this.dataManager.set(MULTIPLIER, Float.valueOf(multiplier));
	}

    @Override
    public String getName()
    {
    	if (hasCustomName())
            return getCustomNameTag();
        else
        	return TranslateUtil.translate("entity.zombie.variant." + getVariant());
    }

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}

	public double getVariantHealth()
	{
		switch (this.getVariant())
		{
			case 1:
				return 80;
			case 2:
				return 25;
			case 3:
				return 35;
			case 4:
				return 14;
			case 5:
				return 22;
			case 6:
				return 25;
			case 7:
				return 30;
			case 8:
				return 10;
			case 9:
				return 16;
			case 10:
				return 16;
			case 11:
				return 60;
			case 12:
				return 400;
			case 13:
				return 3000;
			case 14:
				return 40;
			default:
				return 20;
		}
	}

	public double getVariantAttack()
	{
		switch (this.getVariant())
		{
			case 1:
				return 6;
			case 2:
				return 4;
			case 3:
				return 5;
			case 4:
				return 2;
			case 5:
				return 3;
			case 6:
				return 3;
			case 7:
				return 4;
			case 8:
				return 1;
			case 9:
				return 2;
			case 10:
				return 2;
			case 11:
				return 8;
			case 12:
				return 30;
			case 13:
				return 200;
			case 14:
				return 6;
			default:
				return 3;
		}
	}

	public double getVariantSpeed()
	{
		switch (this.getVariant())
		{
			case 1:
				return 0.25D;
			case 4:
				return 0.3D;
			case 11:
				return 0.235D;
			case 12:
				return 0.3D;
			case 13:
				return 0.25D;
			default:
				return 0.23D;
		}
	}

	@Override
	public EnumMobTier getTier()
	{
		return EnumMobTier.getByMultiplier(getMultiplier());
	}
}
