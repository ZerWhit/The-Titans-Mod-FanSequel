package net.minecraft.titans.registries;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.potion.PotionUtils;
import net.minecraft.titans.items.BaseVariantEgg;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;

public class TItemColors extends ItemColors {
    private final java.util.Map<net.minecraftforge.registries.IRegistryDelegate<Item>, IItemColor> itemColorMap = com.google.common.collect.Maps.newHashMap();

    public static ItemColors init(final BlockColors colors)
    {
        ItemColors itemcolors = new ItemColors();
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                return tintIndex > 0 ? -1 : ((ItemArmor)stack.getItem()).getColor(stack);
            }
        }, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = BlockDoublePlant.EnumPlantType.byMetadata(stack.getMetadata());
                return blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.GRASS && blockdoubleplant$enumplanttype != BlockDoublePlant.EnumPlantType.FERN ? -1 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
            }
        }, Blocks.DOUBLE_PLANT);
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                if (tintIndex != 1)
                {
                    return -1;
                }
                else
                {
                    NBTBase nbtbase = ItemFireworkCharge.getExplosionTag(stack, "Colors");

                    if (!(nbtbase instanceof NBTTagIntArray))
                    {
                        return 9079434;
                    }
                    else
                    {
                        int[] aint = ((NBTTagIntArray)nbtbase).getIntArray();

                        if (aint.length == 1)
                        {
                            return aint[0];
                        }
                        else
                        {
                            int i = 0;
                            int j = 0;
                            int k = 0;

                            for (int l : aint)
                            {
                                i += (l & 16711680) >> 16;
                                j += (l & 65280) >> 8;
                                k += (l & 255) >> 0;
                            }

                            i = i / aint.length;
                            j = j / aint.length;
                            k = k / aint.length;
                            return i << 16 | j << 8 | k;
                        }
                    }
                }
            }
        }, Items.FIREWORK_CHARGE);
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                return tintIndex > 0 ? -1 : PotionUtils.getColor(stack);
            }
        }, Items.POTIONITEM, Items.SPLASH_POTION, Items.LINGERING_POTION);
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.ENTITY_EGGS.get(ItemMonsterPlacer.getNamedIdFrom(stack));

                if (entitylist$entityegginfo == null)
                {
                    return -1;
                }
                else
                {
                    return tintIndex == 0 ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor;
                }
            }
        }, Items.SPAWN_EGG);
        if (TItems.registry != null) {
            for (Item i0 :
                    TItems.registry.getRegistry()) {
                if (i0 instanceof BaseVariantEgg) {
                    BaseVariantEgg egg0 = (BaseVariantEgg) i0;
                    itemcolors.registerItemColorHandler(new IItemColor() {
                        public int colorMultiplier(ItemStack stack, int tintIndex) {
                            return tintIndex == 0 ? egg0.primaryColor : egg0.secondaryColor;
                        }
                    }, i0);
                }
            }
        }
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                IBlockState iblockstate = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
                return colors.colorMultiplier(iblockstate, (IBlockAccess)null, (BlockPos)null, tintIndex);
            }
        }, Blocks.GRASS, Blocks.TALLGRASS, Blocks.VINE, Blocks.LEAVES, Blocks.LEAVES2, Blocks.WATERLILY);
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                return tintIndex == 0 ? PotionUtils.getColor(stack) : -1;
            }
        }, Items.TIPPED_ARROW);
        itemcolors.registerItemColorHandler(new IItemColor()
        {
            public int colorMultiplier(ItemStack stack, int tintIndex)
            {
                return tintIndex == 0 ? -1 : ItemMap.getColor(stack);
            }
        }, Items.FILLED_MAP);
        net.minecraftforge.client.ForgeHooksClient.onItemColorsInit(itemcolors, colors);
        return itemcolors;
    }

    public int colorMultiplier(ItemStack stack, int tintIndex)
    {
        IItemColor iitemcolor = this.itemColorMap.get(stack.getItem().delegate);
        return iitemcolor == null ? -1 : iitemcolor.colorMultiplier(stack, tintIndex);
    }

    public void registerItemColorHandler(IItemColor itemColor, Block... blocksIn)
    {
        for (Block block : blocksIn)
        {
            if (block == null) throw new IllegalArgumentException("Block registered to item color handler cannot be null!");
            if (block.getRegistryName() == null) throw new IllegalArgumentException("Block must be registered before assigning color handler.");
            this.itemColorMap.put(Item.getItemFromBlock(block).delegate, itemColor);
        }
    }

    public void registerItemColorHandler(IItemColor itemColor, Item... itemsIn)
    {
        for (Item item : itemsIn)
        {
            if (item == null) throw new IllegalArgumentException("Item registered to item color handler cannot be null!");
            if (item.getRegistryName() == null) throw new IllegalArgumentException("Item must be registered before assigning color handler.");
            this.itemColorMap.put(item.delegate, itemColor);
        }
    }
}
