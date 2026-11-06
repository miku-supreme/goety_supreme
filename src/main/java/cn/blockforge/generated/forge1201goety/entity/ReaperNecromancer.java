package cn.blockforge.generated.goetysupreme.entity;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractReaperNecromancer;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.MusicDiscDrops;
import cn.blockforge.generated.goetysupreme.renderer.ReaperBossBarOverlay;
import cn.blockforge.generated.goetysupreme.entity.ReaperNecromancer;
import net.minecraft.server.level.ServerBossEvent;
import cn.blockforge.generated.goetysupreme.renderer.SupremeBossLoopMusic;
import cn.blockforge.generated.goetysupreme.util.SupremeBossMusic;

public class ReaperNecromancer extends AbstractReaperNecromancer implements Enemy {

    private static final EntityDataAccessor<java.util.Optional<java.util.UUID>> DATA_BOSS_BAR_ID =
            SynchedEntityData.defineId(ReaperNecromancer.class, EntityDataSerializers.OPTIONAL_UUID);

    private final ServerBossEvent bossBar =
            new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

    public ReaperNecromancer(EntityType<? extends ReaperNecromancer> entityType, Level level) {
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
        int bonus = Math.max(0, looting);
        dropGoetyItem("ectoplasm", this.random.nextInt(4 + bonus));
        dropGoetyItem("grave_dust", this.random.nextInt(4 + bonus));
        this.spawnAtLocation(new ItemStack(Items.BONE, this.random.nextInt(5 + bonus)));
        this.spawnAtLocation(new ItemStack(Items.LAPIS_LAZULI, this.random.nextInt(4 + bonus)));
        dropGoetyItem("forbidden_fragment", 1 + this.random.nextInt(2 + bonus));
        this.spawnAtLocation(new ItemStack(GeneratedMod.BROKEN_REAPER_SCYTHE.get(), 1));
        MusicDiscDrops.roll(this, GeneratedMod.MUSIC_DISC_CANT_LET_GO.get(), looting);
    }

    private void dropGoetyItem(String path, int count) {
        if (count <= 0) {
            return;
        }
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath("goety", path));
        if (item == null) {
            return;
        }
        this.spawnAtLocation(new ItemStack(item, count));
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
    }
}
