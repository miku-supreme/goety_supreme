package cn.blockforge.generated.goetysupreme.entity;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithServant;
import cn.blockforge.generated.goetysupreme.util.MusicDiscDrops;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import net.minecraft.server.level.ServerBossEvent;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;
import cn.blockforge.generated.goetysupreme.util.WarlordNecromancerHate;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlord;
import cn.blockforge.generated.goetysupreme.renderer.WitherWarlordBossBarOverlay;
import cn.blockforge.generated.goetysupreme.entity.WitherWarlordServant;

public class WitherWarlord extends AbstractWitherWarlord implements Enemy {

    private static final EntityDataAccessor<java.util.Optional<java.util.UUID>> DATA_BOSS_BAR_ID =
            SynchedEntityData.defineId(WitherWarlord.class,
                    EntityDataSerializers.OPTIONAL_UUID);

    private final ServerBossEvent bossBar =
            new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);

    private static final float PLAYER_HATE_DAMAGE_THRESHOLD = 20.0F;

    private static final int GREEN_SMITH_SCAN_INTERVAL = 10;

    private int greenSmithScanCooldown;

    private boolean greenSmithEngaged;

    public WitherWarlord(EntityType<? extends WitherWarlord> entityType, Level level) {
        super(entityType, level);
        this.bossBar.setVisible(true);
        this.entityData.set(DATA_BOSS_BAR_ID, java.util.Optional.of(this.bossBar.getId()));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BOSS_BAR_ID, java.util.Optional.empty());
    }

    public java.util.Optional<java.util.UUID> getBossBarId() {
        return this.entityData.get(DATA_BOSS_BAR_ID);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new HuntGreenSmithGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractPiglin.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, LivingEntity.class,
                10, true, false, WarlordNecromancerHate::isNecromancerKind));
    }

    @Override
    public net.minecraft.sounds.SoundSource getSoundSource() {
        return net.minecraft.sounds.SoundSource.HOSTILE;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData,
            @Nullable CompoundTag tag) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        MusicDiscDrops.roll(this, GeneratedMod.MUSIC_DISC_WARLORD_THEME.get(), looting);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossBar.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossBar.removePlayer(player);
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.level().isClientSide) {
            return;
        }
        if (this.tickCount % 5 == 0) {
            this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        }
        if (--this.greenSmithScanCooldown <= 0) {
            this.greenSmithScanCooldown = GREEN_SMITH_SCAN_INTERVAL;
            boolean nearby = this.hasNearbyGreenSmith();
            if (nearby != this.greenSmithEngaged) {
                this.greenSmithEngaged = nearby;
                this.resetPlayerHateDamage();
            }
        }
    }

    private void beginGreenSmithEncounter() {
        this.greenSmithEngaged = true;
        this.resetPlayerHateDamage();
    }

    private boolean playerHateReached() {
        return this.getPlayerHateDamage() > PLAYER_HATE_DAMAGE_THRESHOLD;
    }

    private boolean hasNearbyGreenSmith() {
        double range = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        AABB box = this.getBoundingBox().inflate(range);
        for (AbstractGreenSmith smith : this.level().getEntitiesOfClass(AbstractGreenSmith.class, box)) {
            if (smith.isAlive() && this.canAttack(smith)) {
                return true;
            }
        }
        return false;
    }

    private static final class HuntGreenSmithGoal extends NearestAttackableTargetGoal<AbstractGreenSmith> {

        private final WitherWarlord warlord;

        HuntGreenSmithGoal(WitherWarlord warlord) {
            super(warlord, AbstractGreenSmith.class, true, false);
            this.warlord = warlord;
        }

        @Override
        public boolean canUse() {
            if (this.warlord.greenSmithEngaged && this.warlord.playerHateReached()) {
                return false;
            }
            if (!super.canUse()) {
                return false;
            }
            if (!this.warlord.greenSmithEngaged) {
                this.warlord.beginGreenSmithEncounter();
            }
            return !this.warlord.playerHateReached();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.warlord.playerHateReached() && super.canContinueToUse();
        }
    }
}
