package net.minecraft.titans.items;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.titans.TheTitans;
import net.minecraft.titans.api.variants.IVariedMob;
import net.minecraft.titans.api.variants.types.EnumMobTier;
import net.minecraft.titans.api.variants.types.IMobTier;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Locale;
import java.util.Random;

public class BaseVariantEgg extends Item {
    public int primaryColor;
    public int secondaryColor;
    private String mobname;
    private String fakename;
    public int tierID;
    private int variantID;

    public BaseVariantEgg(String realname, int firstcolor, int secondcolor, String name) {
        this.setUnlocalizedName("spawn_egg_" + name);
        this.setHasSubtypes(true);
        this.setCreativeTab(TheTitans.TAB_MOBS);
        this.fakename = name;
        this.mobname = realname;
        this.primaryColor = firstcolor;
        this.secondaryColor = secondcolor;
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return "Spawn " + I18n.translateToLocal("entity." + this.fakename + ".variant.0") + " " + I18n.translateToLocal("rank." + this.getMinionType().name().toLowerCase(Locale.ROOT));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        } else {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            Block block = world.getBlockState(pos).getBlock();
            x += facing.getFrontOffsetX();
            y += facing.getFrontOffsetY();
            z += facing.getFrontOffsetZ();
            ItemStack stack = player.getHeldItem(hand);
            double addY = 0.0;
            if (facing.getIndex() == 1 && block != Blocks.AIR && block.getRenderType(block.getDefaultState()) == EnumBlockRenderType.LIQUID) {
                addY = 0.5;
            }

            for(int i = 0; i < (player.isSneaking() ? 9 : 1); ++i) {
                EntityLiving living = spawnMobCreature(new ResourceLocation("thetitans:" + (this.mobname == "WitherSkeletonMinion" ? "SkeletonMinion" : this.mobname)), world, stack.getItemDamage(), (double)x + 0.5, (double)y + addY, (double)z + 0.5);
                if (living != null) {
                    if (stack.hasDisplayName()) {
                        living.setCustomNameTag(stack.getDisplayName());
                    }

                    living.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
                    if (living instanceof IVariedMob) {
                        /*if (living instanceof EntitySkeletonMinion && this.mobname == "WitherSkeletonMinion") {
                            ((EntitySkeletonMinion)living).setSkeletonType(1);
                        }*/

                        ((IVariedMob)living).setVariant(new Random().nextInt(14));
                        /*if (TheTitans.isHeraldLevel(this.getMinionType())) {
                            TheTitans.conjureMinion(this.mobname, world, (double)x, (double)y, (double)z, EnumMinionType.GUARDIAN.getID(), 0, living.getRNG());
                            TheTitans.conjureMinion(this.mobname, world, (double)x, (double)y, (double)z, EnumMinionType.GUARDIAN.getID(), 0, living.getRNG());
                        }*/
                    }

                    living.playLivingSound();
                }
            }

            if (!player.capabilities.isCreativeMode && world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                stack.shrink(-1);
            }

            return EnumActionResult.PASS;
        }
    }

    public EntityLiving spawnMobCreature(ResourceLocation name, World world, int id, double x, double y, double z) {
        if (world.isRemote) {
            return null;
        } else {
            Entity var8 = EntityList.createEntityByIDFromName(name, world);
            if (var8 != null) {
                var8.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0F, 0.0F);
                ((IMobTier)var8).setMultiplier(EnumMobTier.getMultiplierByID(tierID));
                world.spawnEntity(var8);
                return (EntityLiving)var8;
            } else {
                return null;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return tierID > 4 || stack.isItemEnchanted();
    }

    private int getVariant() {
        return variantID;
    }

    private EnumMobTier getMinionType() {
        switch (tierID) {
            case 1:
                return EnumMobTier.LOYALIST;
            case 2:
                return EnumMobTier.PRIEST;
            case 3:
                return EnumMobTier.ZEALOT;
            case 4:
                return EnumMobTier.BISHOP;
            case 5:
                return EnumMobTier.TEMPLAR;
            case 6:
                return EnumMobTier.LORD;
            case 7:
                return EnumMobTier.DEMITITAN;
            default:
                return EnumMobTier.NORMAL;
        }
    }
}
