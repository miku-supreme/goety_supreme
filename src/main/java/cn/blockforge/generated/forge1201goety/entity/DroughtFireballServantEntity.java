package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.DroughtFireballServantEntity;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class DroughtFireballServantEntity extends SmallFireball {

    private static final int MAX_LIFE_TICKS = 90;

    private int burnSeconds = 6;

    private float damageBonus;

    public DroughtFireballServantEntity(EntityType<? extends DroughtFireballServantEntity> entityType,
            Level level) {
        super(entityType, level);
    }

    public DroughtFireballServantEntity(Level level, LivingEntity shooter, Vec3 direction, int burnSeconds) {
        super(ModEntityTypes.DROUGHT_FIREBALL_SERVANT.get(), level);
        setOwner(shooter);
        setPos(shooter.getX(), shooter.getY(1.0D), shooter.getZ());
        setDeltaMovement(direction);
        this.burnSeconds = burnSeconds;
        setItem(droughtFireballItem());
    }

    public void setJerotesAcceleration() {
        Vec3 power = getDeltaMovement().normalize().scale(0.1D);
        this.xPower = power.x;
        this.yPower = power.y;
        this.zPower = power.z;
    }

    public float getDamageBonus() {
        return damageBonus;
    }

    public void setDamageBonus(float damageBonus) {
        this.damageBonus = damageBonus;
    }

    private static ItemStack droughtFireballItem() {
        ItemStack stack = AbstractJerotesServant.jerotesItem("drought_fireball");
        return stack.isEmpty() ? new ItemStack(Items.BLAZE_POWDER) : stack;
    }

    @Override
    public float getInertia() {
        return 1.0F;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && tickCount > MAX_LIFE_TICKS) {
            discard();
            return;
        }
        if (level().isClientSide) {
            level().addParticle(ParticleTypes.FLAME,
                    getX() + (random.nextDouble() - 0.5D) * 0.2D,
                    getY() + (random.nextDouble() - 0.5D) * 0.2D,
                    getZ() + (random.nextDouble() - 0.5D) * 0.2D, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (!super.canHitEntity(target)) {
            return false;
        }
        return !(getOwner() instanceof AbstractJerotesServant shooter && shooter.isAlliedTo(target));
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        Entity hit = result.getEntity();
        if (level().isClientSide || !(hit instanceof LivingEntity target)) {
            return;
        }
        Entity owner = getOwner();
        if (owner == target || (owner != null && owner.isAlliedTo(target))) {
            return;
        }
        float damage = 2.0F + random.nextInt(8) + damageBonus;
        if (target.hurt(JerotesMagicDamage.fireMagic(this, owner), damage)) {
            target.setSecondsOnFire(burnSeconds);
        } else {
            target.hurt(damageSources().indirectMagic(this, owner), damage);
        }
        discard();
    }

    @Override
    public void onHitBlock(BlockHitResult result) {
        if (level() instanceof ServerLevel serverLevel) {
            BlockPos hitPos = result.getBlockPos();
            BlockPos adjacent = hitPos.relative(result.getDirection());
            igniteBlock(serverLevel, hitPos, getOwner());
            igniteBlock(serverLevel, adjacent, getOwner());
            placeFire(serverLevel, hitPos, getOwner());
            placeFire(serverLevel, adjacent, getOwner());
            serverLevel.sendParticles(ParticleTypes.FLAME, result.getLocation().x,
                    result.getLocation().y, result.getLocation().z, 8,
                    0.2D, 0.2D, 0.2D, 0.01D);
        }
        discard();
    }

    private static void igniteBlock(ServerLevel level, BlockPos pos, Entity owner) {
        if (owner instanceof net.minecraft.world.entity.Mob
                && !level.getGameRules().getBoolean(net.minecraft.world.level.GameRules.RULE_DOFIRETICK)) {
            return;
        }
        net.minecraft.world.level.block.state.BlockState state = level.getBlockState(pos);
        if (state.hasProperty(net.minecraft.world.level.block.CampfireBlock.LIT)
                && !state.getValue(net.minecraft.world.level.block.CampfireBlock.LIT)) {
            level.setBlock(pos, state.setValue(net.minecraft.world.level.block.CampfireBlock.LIT, true), 3);
            return;
        }
        if (state.hasProperty(net.minecraft.world.level.block.AbstractCandleBlock.LIT)
                && !state.getValue(net.minecraft.world.level.block.AbstractCandleBlock.LIT)) {
            level.setBlock(pos, state.setValue(net.minecraft.world.level.block.AbstractCandleBlock.LIT, true), 3);
        }
    }

    private static void placeFire(ServerLevel level, BlockPos pos, Entity owner) {
        if (owner instanceof net.minecraft.world.entity.Mob
                && !level.getGameRules().getBoolean(net.minecraft.world.level.GameRules.RULE_DOFIRETICK)) {
            return;
        }
        if (level.isEmptyBlock(pos)
                && net.minecraft.world.level.block.BaseFireBlock.canBePlacedAt(
                        level, pos, net.minecraft.core.Direction.UP)) {
            level.setBlockAndUpdate(pos,
                    net.minecraft.world.level.block.BaseFireBlock.getState(level, pos));
        }
    }
}
