package cn.blockforge.generated.goetysupreme.ai;

import cn.blockforge.generated.goetysupreme.util.MutantInternals;
import fuzs.mutantmonsters.animation.AnimatedEntity;
import fuzs.mutantmonsters.animation.Animation;
import fuzs.mutantmonsters.core.ZombieResurrection;
import fuzs.mutantmonsters.world.entity.mutant.MutantZombie;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.MutantZombieServantConfig;
import cn.blockforge.generated.goetysupreme.event.MutantMonstersEvents;
import cn.blockforge.generated.goetysupreme.ai.WildMutantRoarGuarantee;
import cn.blockforge.generated.goetysupreme.mixin.ZombieResurrectionMixin;

public final class WildMutantRoarGuarantee extends Goal {

    private static final int ERUPTION_MIN_TICKS = 40;
    private static final int ERUPTION_EXTRA_TICKS = 20;

    private final MutantZombie zombie;
    private LivingEntity trackedTarget;

    private long guaranteeTime = -1;
    private boolean spent;
    private long readyTime;

    private boolean ourRoar;

    public WildMutantRoarGuarantee(MutantZombie zombie) {
        this.zombie = zombie;
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        if (!MutantZombieServantConfig.FIRST_ROAR_GUARANTEE.get()) {
            return false;
        }
        List<Object> pending = MutantInternals.resurrectionListOf(zombie);
        if (pending == null) {
            return false;
        }
        Level level = zombie.level();
        long now = level.getGameTime();
        if (zombie.getAnimation() == MutantZombie.ROAR_ANIMATION && !ourRoar) {
            spent = true;
            guaranteeTime = -1;
            return false;
        }
        LivingEntity target = zombie.getTarget();
        if (target != trackedTarget) {
            trackedTarget = target;
            spent = false;
            guaranteeTime = -1;
        }
        if (target == null || spent) {
            return false;
        }
        if (guaranteeTime < 0) {
            if (now < readyTime) {
                return false;
            }
            guaranteeTime = now + MutantZombieServantConfig.firstRoarDelayTicks(level.random);
        }
        if (now < guaranteeTime) {
            return false;
        }
        return zombie.isAlive() && target.isAlive()
                && !zombie.isAnimationPlaying()
                && zombie.onGround()
                && pending.isEmpty();
    }

    @Override
    public void start() {
        spent = true;
        guaranteeTime = -1;
        readyTime = zombie.level().getGameTime()
                + MutantZombieServantConfig.FIRST_ROAR_MAX_SECONDS.get() * 40L;
        ourRoar = true;
        zombie.invulnerableTime = 20;
        AnimatedEntity.sendAnimationPacket(zombie, MutantZombie.ROAR_ANIMATION);
    }

    @Override
    public boolean canContinueToUse() {
        return zombie.getAnimation() == MutantZombie.ROAR_ANIMATION
                && zombie.getAnimationTick() < MutantZombie.ROAR_ANIMATION.duration();
    }

    @Override
    public void tick() {
        ourRoar = true;
        zombie.getNavigation().stop();
        int tick = zombie.getAnimationTick();
        LivingEntity target = zombie.getTarget();
        if (tick < 75 && target != null) {
            zombie.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
        if (tick == 10) {
            performRoarShockwave();
        }
        if (tick >= 20 && tick < 80 && tick % 10 == 0) {
            scheduleEruption();
        }
    }

    @Override
    public void stop() {
        ourRoar = false;
        if (zombie.getAnimation() == MutantZombie.ROAR_ANIMATION) {
            AnimatedEntity.sendAnimationPacket(zombie, Animation.NONE);
        }
    }

    private void scheduleEruption() {
        Level level = zombie.level();
        int x = Mth.floor(zombie.getX());
        int z = Mth.floor(zombie.getZ());
        x += (1 + level.random.nextInt(8)) * (level.random.nextBoolean() ? 1 : -1);
        z += (1 + level.random.nextInt(8)) * (level.random.nextBoolean() ? 1 : -1);
        int y = ZombieResurrection.getSuitableGround(level, x,
                Mth.floor(zombie.getBoundingBox().minY) - 1, z);
        if (y == -1) {
            return;
        }
        List<Object> pending = MutantInternals.resurrectionListOf(zombie);
        if (pending != null) {
            pending.add(new ZombieResurrection(level, new BlockPos(x, y, z),
                    ERUPTION_MIN_TICKS + level.random.nextInt(ERUPTION_EXTRA_TICKS)));
        }
    }

    private void performRoarShockwave() {
        Level level = zombie.level();
        SoundEvent roar = BuiltInRegistries.SOUND_EVENT.get(
                ResourceLocation.fromNamespaceAndPath("mutantmonsters", "entity.mutant_zombie.roar"));
        level.playSound(null, zombie.getX(), zombie.getY(), zombie.getZ(),
                roar == null ? SoundEvents.ZOMBIE_AMBIENT : roar,
                SoundSource.HOSTILE, 3.0F, 0.7F + level.random.nextFloat() * 0.2F);
        for (Entity entity : level.getEntities(zombie,
                zombie.getBoundingBox().inflate(12.0D, 8.0D, 12.0D), EntitySelector.NO_SPECTATORS)) {
            if (!canHarm(entity) || zombie.distanceToSqr(entity) > 196.0D) {
                continue;
            }
            double x = entity.getX() - zombie.getX();
            double z = entity.getZ() - zombie.getZ();
            double length = Math.sqrt(x * x + z * z);
            if (length < 1.0E-4D) {
                length = 1.0D;
            }
            entity.setDeltaMovement(x / length * 0.7D, 0.3D, z / length * 0.7D);
            entity.hurtMarked = true;
            if (entity instanceof LivingEntity living) {
                living.hurt(level.damageSources().mobAttack(zombie), 2.0F + level.random.nextInt(2));
            }
        }
    }

    private static boolean canHarm(Entity entity) {
        return entity.getType() != EntityType.ZOMBIE
                && entity.getType() != EntityType.ZOMBIE_VILLAGER
                && entity.getType() != EntityType.HUSK
                && entity.getType() != EntityType.DROWNED
                && !(entity instanceof MutantZombie);
    }
}
