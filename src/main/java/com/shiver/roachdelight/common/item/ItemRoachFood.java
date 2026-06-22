package com.shiver.roachdelight.common.item;

import com.shiver.roachdelight.common.RoachData;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemRoachFood extends ItemFood {
    private final FoodAction action;
    private final boolean returnsBowl;
    private final boolean fast;

    private ItemRoachFood(int amount, float saturation, boolean fast, boolean returnsBowl, FoodAction action) {
        super(amount, saturation, false);
        this.action = action;
        this.returnsBowl = returnsBowl;
        this.fast = fast;
    }

    public static ItemRoachFood simple(int amount) {
        return new ItemRoachFood(amount, 0.6F, false, false, FoodAction.NONE);
    }

    public static ItemRoachFood candy() {
        return new ItemRoachFood(2, 0.1F, true, false, FoodAction.NONE);
    }

    public static ItemRoachFood juicyRoastRoach() {
        return new ItemRoachFood(3, 0.6F, false, false, FoodAction.JUICY_ROAST);
    }

    public static ItemRoachFood gokiburiYaki() {
        return new ItemRoachFood(6, 0.6F, false, false, FoodAction.GOKIBURI_YAKI);
    }

    public static ItemRoachFood roachBall() {
        return new ItemRoachFood(4, 0.6F, false, false, FoodAction.BALL);
    }

    public static ItemRoachFood roachRoll() {
        return new ItemRoachFood(10, 0.6F, false, false, FoodAction.ROLL);
    }

    public static ItemRoachFood roachBurger() {
        return new ItemRoachFood(12, 0.6F, false, false, FoodAction.BURGER);
    }

    public static ItemRoachFood roachSaladPlatter() {
        return new ItemRoachFood(18, 0.6F, false, true, FoodAction.SALAD);
    }

    @Override
    protected void onFoodEaten(@Nonnull ItemStack stack, World worldIn, @Nonnull EntityPlayer player) {
        if (!worldIn.isRemote) {
            this.action.apply(player);
        }
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return this.fast ? 16 : super.getMaxItemUseDuration(stack);
    }

    @Override
    @MethodsReturnNonnullByDefault
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving) {
        ItemStack result = super.onItemUseFinish(stack, worldIn, entityLiving);
        if (this.returnsBowl && entityLiving instanceof EntityPlayer && !((EntityPlayer) entityLiving).capabilities.isCreativeMode) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            ItemStack bowl = new ItemStack(Items.BOWL);
            if (!player.inventory.addItemStackToInventory(bowl)) {
                player.dropItem(bowl, false);
            }
        }
        return result;
    }

    private enum FoodAction {
        NONE {
            @Override
            void apply(EntityLivingBase entity) {
            }
        },
        JUICY_ROAST {
            @Override
            void apply(EntityLivingBase entity) {
                RoachData.removeEffectIf(entity, 1, MobEffects.POISON);
            }
        },
        GOKIBURI_YAKI {
            @Override
            void apply(EntityLivingBase entity) {
                entity.addPotionEffect(new PotionEffect(RoachData.randomClusterEffect(), 20 * 30));
                RoachData.removeEffectIf(entity, 1, MobEffects.POISON);
            }
        },
        BALL {
            @Override
            void apply(EntityLivingBase entity) {
                RoachData.addBallEffects(entity);
            }
        },
        ROLL {
            @Override
            void apply(EntityLivingBase entity) {
                RoachData.addBallEffects(entity);
                RoachData.removeEffectIf(entity, 1, MobEffects.POISON);
            }
        },
        BURGER {
            @Override
            void apply(EntityLivingBase entity) {
                RoachData.addBallEffects(entity);
                RoachData.removeEffectIf(entity, 2, MobEffects.POISON);
            }
        },
        SALAD {
            @Override
            void apply(EntityLivingBase entity) {
                RoachData.addBallEffects(entity);
                RoachData.removeEffectIf(entity, 5, MobEffects.POISON);
                RoachData.removeEffectIf(entity, 5, MobEffects.WEAKNESS);
            }
        };

        abstract void apply(EntityLivingBase entity);
    }
}
