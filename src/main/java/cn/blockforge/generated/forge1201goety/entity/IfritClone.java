package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import cn.blockforge.generated.goetysupreme.util.Ifrit;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;

public class IfritClone extends Ifrit implements com.Polarice3.Goety.api.entities.ally.IServant {

    public static final int LIFETIME_TICKS = 2400;

    @Nullable
    private UUID ownerUuid;
    private int lifeTicks = LIFETIME_TICKS;
    private boolean wandering;
    private boolean staying;
    private int killChance;

    public IfritClone(EntityType<? extends IfritClone> type, Level level) {
        super(type, level);
        this.setClone(true);
        this.setPersistenceRequired();
    }

    @Override
    public LivingEntity getTrueOwner() {
        return this.getMaskOwner();
    }

    @Override
    public UUID getOwnerId() {
        return this.ownerUuid;
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerUuid = ownerId;
    }

    @Override
    public void setTrueOwner(LivingEntity owner) {
        this.setMaskOwner(owner);
    }

    @Override
    public void setHostile(boolean hostile) {
    }

    @Override
    public boolean isHostile() {
        return false;
    }

    @Override
    public boolean isWandering() {
        return this.wandering;
    }

    @Override
    public void setWandering(boolean wandering) {
        this.wandering = wandering;
    }

    @Override
    public boolean isStaying() {
        return this.staying;
    }

    @Override
    public void setStaying(boolean staying) {
        this.staying = staying;
    }

    @Override
    public boolean canUpdateMove() {
        return false;
    }

    @Override
    public void updateMoveMode(Player player) {
    }

    @Override
    public boolean isCommanded() {
        return false;
    }

    @Override
    public void setCommandPos(BlockPos pos) {
    }

    @Override
    public BlockPos getCommandPos() {
        return null;
    }

    @Override
    public void setCommandPos(BlockPos pos, boolean removeEntity) {
    }

    @Override
    public void setCommandPosEntity(LivingEntity entity) {
    }

    @Override
    public LivingEntity getCommandPosEntity() {
        return null;
    }

    @Override
    public int getCommandTick() {
        return 0;
    }

    @Override
    public void setCommandTick(int ticks) {
    }

    @Override
    public int getKillChance() {
        return this.killChance;
    }

    @Override
    public void setKillChance(int ticks) {
        this.killChance = ticks;
    }

    @Override
    public void servantTick() {
        if (this.killChance > 0) {
            --this.killChance;
        }
    }

    public void warnKill(Player player) {
        this.killChance = 60;
        player.displayClientMessage(Component.translatable("info.goety.servant.tryKill", this.getDisplayName()), true);
    }

    @Override
    public void tryKill(Player player) {
        if (this.level().isClientSide()) {
            return;
        }
        if (this.getKillChance() <= 0) {
            this.warnKill(player);
            return;
        }
        this.hurt(com.Polarice3.Goety.utils.ModDamageSource.getDamageSource(
                this.level(), com.Polarice3.Goety.utils.ModDamageSource.DISMISSED), Float.MAX_VALUE);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.targetSelector.removeAllGoals(goal -> true);
        this.goalSelector.addGoal(5, new CloneFollowGoal(this, 1.2D, 12.0F, 4.0F));
        this.targetSelector.addGoal(1, new CloneOwnerHurtByGoal(this));
        this.targetSelector.addGoal(2, new CloneOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Monster.class, true,
                entity -> entity != this.getMaskOwner() && !this.isAlliedTo(entity)));
    }

    @Override
    public boolean shouldDespawnInPeaceful() { return false; }

    public void setMaskOwner(LivingEntity owner) {
        this.ownerUuid = owner.getUUID();
    }

    @Nullable
    public LivingEntity getMaskOwner() {
        if (this.ownerUuid == null || !(this.level() instanceof ServerLevel server)) {
            return null;
        }
        Entity entity = server.getEntity(this.ownerUuid);
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (target == this.getMaskOwner() || this.isAlliedTo(target)) {
            return false;
        }
        if (!(target instanceof Enemy) && !(target instanceof Monster)) {
            return false;
        }
        return super.canAttack(target);
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        LivingEntity owner = this.getMaskOwner();
        if (owner != null) {
            if (other == owner || other.isAlliedTo(owner) || owner.isAlliedTo(other)) {
                return true;
            }
            if (other instanceof com.Polarice3.Goety.common.entities.ally.Summoned summoned
                    && summoned.getTrueOwner() == owner) {
                return true;
            }
        }
        return super.isAlliedTo(other);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Player) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.servantTick();
            LivingEntity owner = this.getMaskOwner();
            if (owner != null && this.getTarget() == owner) {
                this.setTarget(null);
            }
            if (--this.lifeTicks <= 0) {
                this.discard();
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.ownerUuid != null) {
            tag.putUUID("MaskOwner", this.ownerUuid);
        }
        tag.putInt("MaskLife", this.lifeTicks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("MaskOwner")) {
            this.ownerUuid = tag.getUUID("MaskOwner");
        }
        this.lifeTicks = tag.getInt("MaskLife");
    }

    public static void discardFor(Player player) {
        if (!(player.level() instanceof ServerLevel server)) {
            return;
        }
        List<IfritClone> clones = server.getEntitiesOfClass(IfritClone.class,
                player.getBoundingBox().inflate(128.0D),
                clone -> player.getUUID().equals(clone.ownerUuid));
        for (IfritClone clone : clones) {
            clone.discard();
        }
    }

    private static final class CloneFollowGoal extends Goal {
        private final IfritClone clone;
        private final double speed;
        private final float start;
        private final float stop;

        private CloneFollowGoal(IfritClone clone, double speed, float start, float stop) {
            this.clone = clone;
            this.speed = speed;
            this.start = start;
            this.stop = stop;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity owner = this.clone.getMaskOwner();
            return owner != null && this.clone.getTarget() == null
                    && this.clone.distanceToSqr(owner) > this.start * this.start;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity owner = this.clone.getMaskOwner();
            return owner != null && this.clone.getTarget() == null
                    && this.clone.distanceToSqr(owner) > this.stop * this.stop;
        }

        @Override
        public void tick() {
            LivingEntity owner = this.clone.getMaskOwner();
            if (owner != null) {
                this.clone.getLookControl().setLookAt(owner, 30.0F, 30.0F);
                this.clone.getNavigation().moveTo(owner, this.speed);
            }
        }

        @Override
        public void stop() {
            this.clone.getNavigation().stop();
        }
    }

    private static final class CloneOwnerHurtByGoal extends TargetGoal {
        private LivingEntity attacker;

        private CloneOwnerHurtByGoal(IfritClone clone) {
            super(clone, false);
        }

        @Override
        public boolean canUse() {
            IfritClone clone = (IfritClone) this.mob;
            LivingEntity owner = clone.getMaskOwner();
            if (owner == null) {
                return false;
            }
            this.attacker = owner.getLastHurtByMob();
            return this.attacker != null && this.attacker.isAlive()
                    && this.attacker != clone && !clone.isAlliedTo(this.attacker);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.attacker);
            super.start();
        }
    }

    private static final class CloneOwnerHurtTargetGoal extends TargetGoal {
        private LivingEntity victim;

        private CloneOwnerHurtTargetGoal(IfritClone clone) {
            super(clone, false);
        }

        @Override
        public boolean canUse() {
            IfritClone clone = (IfritClone) this.mob;
            LivingEntity owner = clone.getMaskOwner();
            if (owner == null) {
                return false;
            }
            this.victim = owner.getLastHurtMob();
            return this.victim != null && this.victim.isAlive()
                    && this.victim != clone && !clone.isAlliedTo(this.victim);
        }

        @Override
        public void start() {
            this.mob.setTarget(this.victim);
            super.start();
        }
    }
}
