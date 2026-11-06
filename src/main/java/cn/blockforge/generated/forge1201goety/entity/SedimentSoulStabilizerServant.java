package cn.blockforge.generated.goetysupreme.entity;

import java.util.EnumSet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.entity.SedimentMissileEntity;
import cn.blockforge.generated.goetysupreme.entity.SedimentSoulStabilizerServant;

public class SedimentSoulStabilizerServant extends AbstractSedimentServant implements RangedAttackMob {

    private static final int CAST_ANIM_TICKS = 24;

    private static final int VOLLEY_SHOTS = 4;

    private static final int VOLLEY_INTERVAL_TICKS = 5;

    private static final int CAST_INTERVAL_TICKS = 60;

    private static final double MIN_CAST_DISTANCE_SQR = 16.0D;

    private static final double MAX_CAST_DISTANCE_SQR = 225.0D;

    private static final double MELEE_GRIP_DISTANCE_SQR = 25.0D;

    public SedimentSoulStabilizerServant(EntityType<? extends SedimentSoulStabilizerServant> entityType,
            Level level) {
        super(entityType, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.ARMOR, 0.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.SEDIMENT_SOUL_STABILIZER;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/sediment_soul_stabilizer.png";
    }

    @Override
    public EquipmentSlot replacementSlotFor(net.minecraft.world.item.ItemStack held) {
        if (!held.isEmpty() && held.is(STABILIZER_DAGGER)) {
            return EquipmentSlot.MAINHAND;
        }
        return null;
    }

    @Override
    public boolean acceptsRangedWeaponGiving() {
        return false;
    }

    @Override
    public void registerCombatGoals() {
        this.goalSelector.addGoal(1, new MissileCastGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1D, true));
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_dagger");
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            LivingEntity target = getTarget();
            boolean dagger = isHoldingDagger();
            setShiftKeyDown(dagger && target != null && target.isAlive() && onGround()
                    && distanceToSqr(target) <= MELEE_GRIP_DISTANCE_SQR);
        }
    }

    public void spawnCastDisplay() {
        if (level() instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    jerotesParticle("sediment_missile_display",
                            net.minecraft.core.particles.ParticleTypes.SOUL),
                    getX(), getBoundingBox().maxY + 0.5D, getZ(), 0, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    private boolean isHoldingDagger() {
        return JerotesItems.isDagger(getMainHandItem());
    }

    @Override
    public SoundEvent getAmbientSound() {
        return jerotesSound("resurrecter_ambient", SoundEvents.PILLAGER_AMBIENT);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        if (level().isClientSide || target == null || !target.isAlive() || !canAttack(target)) {
            return;
        }
        SedimentMissileEntity missile = new SedimentMissileEntity(level(), this);
        missile.setDamageBonus(getSummonPotency());
        double dx = target.getX() - getX();
        double dy = target.getY(0.5D) - missile.getY();
        double dz = target.getZ() - getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        missile.shoot(dx, dy + horizontal * 0.08D, dz, 1.4F, 4.0F);
        missile.setLockedTarget(target);
        level().addFreshEntity(missile);
        swing(InteractionHand.MAIN_HAND, true);
        playSound(jerotesSound("resurrecter_spell", SoundEvents.EVOKER_CAST_SPELL), 1.0F, 1.0F);
    }

    private static final class MissileCastGoal extends Goal {
        private final SedimentSoulStabilizerServant servant;
        private LivingEntity target;
        private int cooldown;
        private int castTime = -1;

        private int pathCooldown;

        MissileCastGoal(SedimentSoulStabilizerServant servant) {
            this.servant = servant;
            setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        private boolean validTarget() {
            LivingEntity candidate = servant.getTarget();
            return candidate != null && candidate.isAlive() && servant.canAttack(candidate)
                    && servant.distanceToSqr(candidate) > MIN_CAST_DISTANCE_SQR;
        }

        @Override
        public boolean canUse() {
            return !servant.isCasting() && validTarget();
        }

        @Override
        public boolean canContinueToUse() {
            if (servant.isCasting() && target != null && target.isAlive() && servant.canAttack(target)) {
                return true;
            }
            return validTarget();
        }

        @Override
        public void start() {
            target = servant.getTarget();
            cooldown = 0;
            castTime = -1;
        }

        @Override
        public void stop() {
            if (servant.isCasting()) {
                servant.setCastTicks(0);
            }
            target = null;
            castTime = -1;
        }

        private void approach(LivingEntity target, double distance, boolean visible) {
            if (pathCooldown > 0) {
                pathCooldown--;
                return;
            }
            if (distance <= MIN_CAST_DISTANCE_SQR) {
                servant.getNavigation().stop();
                return;
            }
            if (!visible) {
                pathCooldown = 10;
                servant.getNavigation().moveTo(target, 1.0D);
                return;
            }
            pathCooldown = 4 + servant.getRandom().nextInt(7);
            if (!servant.getNavigation().moveTo(target, 1.0D)) {
                pathCooldown += 15;
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity current = servant.getTarget();
            if (current != null && current.isAlive() && servant.canAttack(current)) {
                target = current;
            }
            if (target == null || !target.isAlive() || !servant.canAttack(target)) {
                return;
            }
            double distance = servant.distanceToSqr(target);
            boolean visible = servant.hasLineOfSight(target);

            if (castTime >= 0) {
                servant.getLookControl().setLookAt(target, 10.0F, servant.getMaxHeadXRot());
                approach(target, distance, visible);
                castTime++;
                if (castTime % VOLLEY_INTERVAL_TICKS == 0
                        && castTime <= VOLLEY_INTERVAL_TICKS * VOLLEY_SHOTS
                        && visible && distance <= MAX_CAST_DISTANCE_SQR) {
                    servant.performRangedAttack(target, 1.0F);
                }
                if (castTime >= CAST_ANIM_TICKS) {
                    castTime = -1;
                    cooldown = CAST_INTERVAL_TICKS;
                }
                return;
            }

            servant.getLookControl().setLookAt(target, 30.0F, servant.getMaxHeadXRot());
            approach(target, distance, visible);
            if (cooldown > 0) {
                cooldown--;
            }
            if (cooldown <= 0 && visible && distance > MIN_CAST_DISTANCE_SQR
                    && distance <= MAX_CAST_DISTANCE_SQR) {
                castTime = 0;
                servant.spawnCastDisplay();
                servant.setCastTicks(CAST_ANIM_TICKS);
            }
        }
    }
}
