package cn.blockforge.generated.goetysupreme.block;

import com.Polarice3.Goety.utils.ModDamageSource;
import fuzs.mutantmonsters.world.entity.mutant.MutantSnowGolem;
import fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import cn.blockforge.generated.goetysupreme.config.MutantSnowGolemServantConfig;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;
import cn.blockforge.generated.goetysupreme.block.MutantSnowGolemServantIceBlock;

public final class MutantSnowGolemServantIceBlock extends ThrowableBlock {

    private static final double SPLASH_DISTANCE_SQR = 6.25D;

    private final MutantSnowGolemServant servant;
    private final LivingEntity effectiveOwner;

    public MutantSnowGolemServantIceBlock(MutantSnowGolem standIn, MutantSnowGolemServant servant) {
        super(standIn);
        this.servant = servant;
        this.effectiveOwner = servant.getTrueOwner() != null ? servant.getTrueOwner() : servant;
        setOwner(servant);
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (target == this || target == servant || (target instanceof LivingEntity && servant.isAlliedTo(target))) {
            return false;
        }
        return super.canHitEntity(target);
    }

    @Override
    public void onHit(HitResult result) {
        Level level = level();
        if (level.isClientSide()) {
            discard();
            return;
        }
        Entity directHit = result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHit
                ? entityHit.getEntity() : null;
        AABB box = getBoundingBox().inflate(2.5D, 2.5D, 2.5D);
        for (Mob mob : level.getEntitiesOfClass(Mob.class, box, this::canDamage)) {
            if (mob == servant || mob == directHit) {
                continue;
            }
            if (inSplash(mob, 2.5D, 2.5D)) {
                double damage = MutantSnowGolemServantConfig.randomBetween(
                        MutantSnowGolemServantConfig.ICE_DAMAGE_AOE_MIN,
                        MutantSnowGolemServantConfig.ICE_DAMAGE_AOE_MAX, random);
                mob.hurt(ModDamageSource.summonAttack(servant, effectiveOwner),
                        (float) damage + servant.iceSplashDamageBonus());
                servant.applyIceBlockEffect(mob);
            }
        }
        if (directHit != null && canDamage(directHit) && directHit instanceof LivingEntity living) {
            double directDamage = MutantSnowGolemServantConfig.randomBetween(
                    MutantSnowGolemServantConfig.ICE_DAMAGE_DIRECT_MIN,
                    MutantSnowGolemServantConfig.ICE_DAMAGE_DIRECT_MAX, random);
            living.hurt(ModDamageSource.summonAttack(servant, effectiveOwner),
                    (float) directDamage + servant.iceDirectDamageBonus());
            servant.applyIceBlockEffect(living);
        }
        level.broadcastEntityEvent(this, (byte) 3);
        if (servant.hasFrostNovaUpgrade()) {
            servant.triggerFrostNovaAt(position());
        }
        playBreakSound();
        discard();
    }

    private boolean inSplash(Entity mob, double radius, double height) {
        double dx = mob.getX() - getX();
        double dz = mob.getZ() - getZ();
        double dy = mob.getBoundingBox().getCenter().y - getY();
        return dx * dx + dz * dz <= radius * radius && Math.abs(dy) <= height;
    }

    private boolean canDamage(Entity candidate) {
        return candidate != null && candidate != this && candidate.isAlive()
                && !(candidate instanceof LivingEntity && servant.isAlliedTo(candidate))
                && (candidate instanceof Mob || candidate instanceof LivingEntity);
    }

    private void playBreakSound() {
        BlockState ice = net.minecraft.world.level.block.Blocks.ICE.defaultBlockState();
        playSound(ice.getSoundType().getBreakSound(), 0.8F,
                0.8F + (random.nextFloat() - random.nextFloat()) * 0.2F);
    }
}
