package com.shiver.roachdelight.common.item;

import com.shiver.roachdelight.common.entity.EntityRoachBottle;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;

public class ItemSplashRoachBottle extends Item {
    @Override
    @MethodsReturnNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW,
                SoundCategory.NEUTRAL, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            EntityRoachBottle bottle = new EntityRoachBottle(worldIn, playerIn);
            bottle.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
            worldIn.spawnEntity(bottle);
        }

        playerIn.addStat(Objects.requireNonNull(StatList.getObjectUseStats(this)));
        if (!playerIn.capabilities.isCreativeMode && !hasInfinity(stack)) {
            stack.shrink(1);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private static boolean hasInfinity(ItemStack stack) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        return enchantments.containsKey(Enchantments.INFINITY);
    }
}
