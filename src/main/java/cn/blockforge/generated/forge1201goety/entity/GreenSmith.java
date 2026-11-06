package cn.blockforge.generated.goetysupreme.entity;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.GreenSmith;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithBossBarOverlay;
import cn.blockforge.generated.goetysupreme.util.MusicDiscDrops;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import net.minecraft.server.level.ServerBossEvent;

public class GreenSmith extends AbstractGreenSmith implements Enemy {

    private static final EntityDataAccessor<java.util.Optional<java.util.UUID>> DATA_BOSS_BAR_ID =
            SynchedEntityData.defineId(GreenSmith.class, EntityDataSerializers.OPTIONAL_UUID);

    private final ServerBossEvent bossBar =
            new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS);

    private static final Component NAME_PHASE1 =
            Component.translatable("boss.goety_supreme.green_smith.phase1");
    private static final Component NAME_PHASE2 =
            Component.translatable("boss.goety_supreme.green_smith.phase2");

    private boolean lastSecondPhase;

    public GreenSmith(EntityType<? extends GreenSmith> entityType, Level level) {
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
        this.goalSelector.addGoal(0, new FleeWarlordGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private static final class FleeWarlordGoal extends AvoidEntityGoal<AbstractWitherWarlord> {

        private final GreenSmith smith;

        FleeWarlordGoal(GreenSmith smith) {
            super(smith, AbstractWitherWarlord.class, 24.0F, 1.15D, 1.4D,
                    entity -> !smith.isWarlordHateReached());
            this.smith = smith;
        }

        @Override
        public boolean canUse() {
            return !this.smith.isClone() && !this.smith.isWarlordHateReached() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.smith.isWarlordHateReached() && super.canContinueToUse();
        }

        @Override
        public void start() {
            super.start();
            this.smith.setFleeingWarlord(true);
        }

        @Override
        public void stop() {
            super.stop();
            this.smith.setFleeingWarlord(false);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
            MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        this.setPersistenceRequired();
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
    }

    @Override
    public void dropGreenSmithLoot(int looting) {
        int bonus = Math.max(0, looting);
        dropGoetyItem("ectoplasm", this.random.nextInt(5 + bonus));
        dropGoetyItem("grave_dust", this.random.nextInt(5 + bonus));
        this.spawnAtLocation(new ItemStack(net.minecraft.world.item.Items.BONE,
                this.random.nextInt(6 + bonus)));
        dropGoetyItem("heart_of_the_night", 1);
        int fragmentExtra = bonus / 3;
        dropGoetyItem("forbidden_fragment", 1 + this.random.nextInt(3) + fragmentExtra);
        this.spawnAtLocation(new ItemStack(GeneratedMod.GREEN_APPLE.get(), 1));
        MusicDiscDrops.roll(this, GeneratedMod.MUSIC_DISC_GREEN_SMITH_THEME.get(), looting);
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
    public Component getDisplayName() {
        if (this.hasCustomName()) {
            return super.getDisplayName();
        }
        return this.isSecondPhase() ? NAME_PHASE2 : NAME_PHASE1;
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
        boolean secondPhase = this.isSecondPhase();
        if (secondPhase != this.lastSecondPhase) {
            this.lastSecondPhase = secondPhase;
            this.bossBar.setName(this.getDisplayName());
        }
    }
}
