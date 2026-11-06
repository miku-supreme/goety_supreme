package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.entities.hostile.Wight;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.SEHelper;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSoulReaper;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import cn.blockforge.generated.goetysupreme.entity.SoulReaper;
import cn.blockforge.generated.goetysupreme.util.SoulReaperAmbush;
import net.minecraft.server.level.ServerBossEvent;

public class SoulReaper extends AbstractSoulReaper implements Enemy {

    private static final int DARKNESS_TICKS = 120;

    private static final double DARKNESS_RADIUS = 20.0D;

    private static final String TAG_DARKNESS_ANNOUNCED = "SoulReaperDarknessAnnounced";

    private boolean darknessAnnounced;

    private static final EntityDataAccessor<java.util.Optional<java.util.UUID>> DATA_BOSS_BAR_ID =
            SynchedEntityData.defineId(SoulReaper.class, EntityDataSerializers.OPTIONAL_UUID);

    private final ServerBossEvent bossBar =
            new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

    public SoulReaper(EntityType<? extends SoulReaper> entityType, Level level) {
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
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Warden.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Wight.class, true));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide && !this.darknessAnnounced && this.getTarget() != null) {
            this.darknessAnnounced = true;
            this.applyDarkness();
        }
        if (!this.level().isClientSide) {
            if (this.tickCount % 5 == 0) {
                this.bossBar.setProgress(this.getHealth() / this.getMaxHealth());
            }
        }
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

    private void applyDarkness() {
        if (!(this.level() instanceof ServerLevel server)) {
            return;
        }
        MobEffectUtil.addEffectToPlayersAround(server, this, this.position(), DARKNESS_RADIUS,
                new MobEffectInstance(MobEffects.DARKNESS, DARKNESS_TICKS, 0, false, false), 200);
        server.playSound(null, this.blockPosition(), ModSounds.WIGHT_TELEPORT_SCREAM.get(),
                SoundSource.HOSTILE, 2.0F, 0.7F + this.getRandom().nextFloat() * 0.2F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean(TAG_DARKNESS_ANNOUNCED, this.darknessAnnounced);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.darknessAnnounced = tag.getBoolean(TAG_DARKNESS_ANNOUNCED);
    }

    @Override
    public boolean isServantForm() {
        return false;
    }

    private int lastSoulTier = -1;

    @Override
    public int soulPowerTier() {
        return soulPowerTierFor(this.soulSourceEnergy());
    }

    @Override
    public float soulPowerDamageBonus() {
        return this.soulPowerTier() * (float) (double) SoulReaperConfig.HOSTILE_SOUL_DAMAGE_PER_1000.get();
    }

    @Override
    public void refreshGrowthAttributes() {
        int tier = this.soulPowerTier();
        if (tier == this.lastSoulTier) {
            return;
        }
        this.lastSoulTier = tier;
        AttributeInstance flying = this.getAttribute(Attributes.FLYING_SPEED);
        if (flying != null) {
            flying.setBaseValue(BASE_FLYING_SPEED
                    + tier * SoulReaperConfig.HOSTILE_SOUL_FLYING_SPEED_PER_1000.get());
        }
        AttributeInstance health = this.getAttribute(Attributes.MAX_HEALTH);
        if (health != null) {
            boolean wasFull = this.getHealth() >= health.getValue() - 0.01F;
            health.setBaseValue(SoulReaperConfig.HEALTH.get()
                    + tier * SoulReaperConfig.HOSTILE_SOUL_HEALTH_PER_1000.get());
            if (wasFull) {
                this.setHealth(this.getMaxHealth());
            }
        }
    }

    private int soulSourceEnergy() {
        if (this.getTarget() instanceof Player target) {
            return SEHelper.getSESouls(target);
        }
        Player nearest = null;
        double best = Double.MAX_VALUE;
        for (Player candidate : this.level().players()) {
            double distance = candidate.distanceToSqr(this);
            if (distance < best) {
                best = distance;
                nearest = candidate;
            }
        }
        return nearest == null ? 0 : SEHelper.getSESouls(nearest);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }
}
