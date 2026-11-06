package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.api.entities.ally.IServant;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import java.util.EnumSet;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.ai.CommandPositionGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractMutantServant;

public abstract class AbstractMutantServant extends Summoned implements IServant, net.minecraft.world.entity.OwnableEntity {

    public static final EntityDataAccessor<Byte> SERVANT_FLAGS =
            SynchedEntityData.defineId(AbstractMutantServant.class, EntityDataSerializers.BYTE);
    public static final int WANDERING_FLAG = 1;
    public static final int STAYING_FLAG = 2;
    public static final int NATURAL_FLAG = 4;

    public boolean hostile;

    public AbstractMutantServant(EntityType<? extends AbstractMutantServant> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public BlockPos getBoundPos() {
        return boundPos;
    }

    @Override
    public void setBoundPos(BlockPos pos) {
        boundPos = pos == null ? null : pos.immutable();
        if (boundPos != null) {
            boundDim = level().dimension().location().toString();
        }
    }

    @Override
    public String getBoundDim() {
        return boundDim;
    }

    @Override
    public void setBoundDim(String dimension) {
        boundDim = dimension == null ? level().dimension().location().toString() : dimension;
    }

    @Override
    public BlockPos getCommandPos() {
        return commandPos;
    }

    @Override
    public void setCommandPos(BlockPos pos) {
        setCommandPos(pos, true);
    }

    @Override
    public void setCommandPos(BlockPos pos, boolean removeEntity) {
        if (removeEntity) {
            commandPosEntity = null;
        }
        commandPos = pos == null ? null : pos.immutable();
        commandTick = pos == null ? 0 : (removeEntity ? 200 : Integer.MAX_VALUE);
        setWandering(false);
        setStaying(false);
    }

    @Override
    public void setCommandPosEntity(LivingEntity entity) {
        commandPosEntity = entity;
        commandPos = entity == null ? null : entity.blockPosition();
        commandTick = entity == null ? 0 : Integer.MAX_VALUE;
        setStaying(false);
    }

    @Override
    public LivingEntity getCommandPosEntity() {
        return commandPosEntity;
    }

    @Override
    public int getCommandTick() {
        return commandTick;
    }

    @Override
    public void setCommandTick(int ticks) {
        commandTick = ticks;
    }

    @Override
    public boolean isCommanded() {
        return commandPos != null || commandPosEntity != null;
    }

    @Override
    public boolean isFollowing() {
        return !isCommanded() && !isWandering() && !isStaying()
                && !isGuardingArea() && canFollow();
    }

    @Override
    public UUID getOwnerUUID() {
        return getOwnerId();
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target != null && target != this && !isAlliedTo(target) && super.canAttack(target);
    }

    @Override
    public void setTarget(LivingEntity target) {
        super.setTarget(target != null && canAttack(target) ? target : null);
    }

    @Override
    public void checkHostility() {
        hostile = false;
    }

    @Override
    public void setHostile(boolean hostile) {
        this.hostile = false;
    }

    @Override
    public boolean isHostile() {
        return hostile;
    }

    private boolean getFlag(int flag) {
        return entityData.hasItem(SERVANT_FLAGS) && (entityData.get(SERVANT_FLAGS) & flag) != 0;
    }

    private void setFlag(int flag, boolean value) {
        if (!entityData.hasItem(SERVANT_FLAGS)) {
            return;
        }
        byte flags = entityData.get(SERVANT_FLAGS);
        entityData.set(SERVANT_FLAGS, value ? (byte) (flags | flag) : (byte) (flags & ~flag));
    }

    @Override
    public boolean isNatural() {
        return getFlag(NATURAL_FLAG);
    }

    @Override
    public void setNatural(boolean natural) {
        setFlag(NATURAL_FLAG, natural);
    }

    @Override
    public boolean isWandering() {
        return getFlag(WANDERING_FLAG);
    }

    @Override
    public void setWandering(boolean wandering) {
        setFlag(WANDERING_FLAG, wandering);
    }

    @Override
    public boolean isStaying() {
        return getFlag(STAYING_FLAG) && !isCommanded();
    }

    @Override
    public void setStaying(boolean staying) {
        setFlag(STAYING_FLAG, staying);
        if (staying) {
            getNavigation().stop();
        }
    }

    @Override
    public boolean canUpdateMove() {
        return true;
    }

    public void updateCommand() {
        if (!isCommanded() || getTarget() != null) {
            return;
        }
        if (commandTick != Integer.MAX_VALUE && --commandTick <= 0) {
            commandPos = null;
            commandPosEntity = null;
            return;
        }
        if (commandPosEntity != null) {
            commandPos = commandPosEntity.blockPosition();
            if (distanceToSqr(commandPosEntity) <= 4.0D) {
                commandPos = null;
                commandPosEntity = null;
            }
        }
    }

    public static void setBaseAttribute(AttributeInstance attribute, double value) {
        if (attribute != null) {
            attribute.setBaseValue(value);
        }
    }

    public static SoundEvent mutantSound(String id, SoundEvent fallback) {
        SoundEvent event = BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.fromNamespaceAndPath("mutantmonsters", id));
        return event == null ? fallback : event;
    }

    public abstract static class OwnerTargetGoal extends TargetGoal {
        public final AbstractMutantServant servant;
        LivingEntity owner;
        LivingEntity target;
        int timestamp;

        public OwnerTargetGoal(AbstractMutantServant servant) {
            super(servant, false);
            this.servant = servant;
            setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean validTarget(LivingEntity candidate) {
            return owner != null && candidate != null && candidate.isAlive() && candidate != owner
                    && !servant.isAlliedTo(candidate)
                    && canAttack(candidate, TargetingConditions.DEFAULT);
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity current = mob.getTarget();
            if (current == null) {
                current = this.targetMob;
            }
            if (current == null) {
                return false;
            }
            if (!mob.canAttack(current) || sameTeam(mob, current)) {
                return false;
            }
            double follow = getFollowDistance();
            if (mob.distanceToSqr(current) > follow * follow) {
                return false;
            }
            if (this.mustSee && !mob.getSensing().hasLineOfSight(current)) {
                return false;
            }
            mob.setTarget(current);
            return true;
        }

        private static boolean sameTeam(LivingEntity mob, LivingEntity other) {
            var mine = mob.getTeam();
            return mine != null && other.getTeam() == mine;
        }

        @Override
        public void stop() {
            target = null;
            super.stop();
        }
    }

    public static final class OwnerHurtByTargetGoal extends OwnerTargetGoal {
        public OwnerHurtByTargetGoal(AbstractMutantServant servant) {
            super(servant);
        }

        @Override
        public boolean canUse() {
            owner = servant.getTrueOwner();
            target = owner == null ? null : owner.getLastHurtByMob();
            if (owner == null || owner.getLastHurtByMobTimestamp() == timestamp) {
                return false;
            }
            return validTarget(target);
        }

        @Override
        public void start() {
            if (owner != null && target != null) {
                servant.setTarget(target);
                timestamp = owner.getLastHurtByMobTimestamp();
            }
            super.start();
        }
    }

    public static final class OwnerHurtTargetGoal extends OwnerTargetGoal {
        public OwnerHurtTargetGoal(AbstractMutantServant servant) {
            super(servant);
        }

        @Override
        public boolean canUse() {
            owner = servant.getTrueOwner();
            target = owner == null ? null : owner.getLastHurtMob();
            if (owner == null || owner.getLastHurtMobTimestamp() == timestamp) {
                return false;
            }
            return validTarget(target);
        }

        @Override
        public void start() {
            if (owner != null && target != null) {
                servant.setTarget(target);
                timestamp = owner.getLastHurtMobTimestamp();
            }
            super.start();
        }
    }
}
