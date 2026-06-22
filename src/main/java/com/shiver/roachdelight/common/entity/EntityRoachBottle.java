package com.shiver.roachdelight.common.entity;

import com.github.alexthe666.alexsmobs.entity.EntityCockroach;
import com.shiver.roachdelight.common.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

import javax.annotation.Nonnull;

public class EntityRoachBottle extends EntityThrowable implements IThrowableEntity {
    public EntityRoachBottle(World worldIn) {
        super(worldIn);
    }

    public EntityRoachBottle(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityRoachBottle(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected float getGravityVelocity() {
        return 0.05F;
    }

    @Override
    public void setThrower(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            this.thrower = (EntityLivingBase) entity;
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            int itemId = Item.getIdFromItem(ModItems.SPLASH_BOTTLED_ROACH);
            for (int i = 0; i < 12; ++i) {
                this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ,
                        (this.rand.nextDouble() - 0.5D) * 0.2D,
                        this.rand.nextDouble() * 0.2D,
                        (this.rand.nextDouble() - 0.5D) * 0.2D,
                        itemId);
            }
            return;
        }
        super.handleStatusUpdate(id);
    }

    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {
        if (!this.world.isRemote) {
            EntityCockroach roach = new EntityCockroach(this.world);
            roach.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            this.world.spawnEntity(roach);
            this.world.setEntityState(this, (byte) 3);
            this.setDead();
        }
    }
}
