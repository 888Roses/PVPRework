package net.rose.pvp_rework.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.rose.pvp_rework.common.init.ModEntityTypes;

public class ChargoldScytheEntity extends PersistentProjectileEntity {
    public ChargoldScytheEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    protected ChargoldScytheEntity(World world) {
        super(ModEntityTypes.CHARGOLD_SCYTHE, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
