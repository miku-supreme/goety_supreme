package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.CorrosiveSnowCloudServantEntity;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class CorrosiveSnowCloudServantEntity extends Entity {

    private int life;
    private int duration = 20 * 12;
    private int spellLevel = 5;
    private LivingEntity owner;

    public CorrosiveSnowCloudServantEntity(EntityType<? extends CorrosiveSnowCloudServantEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    public CorrosiveSnowCloudServantEntity(Level level, double x, double y, double z) {
        this(ModEntityTypes.CORROSIVE_SNOW_CLOUD_SERVANT.get(), level);
        this.setPos(x, y, z);
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    public void setDuration(int ticks) {
        this.duration = ticks;
    }

    public void setSpellLevel(int level) {
        this.spellLevel = level;
    }

    @Override
    public void defineSynchedData() {
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        ++this.life;
        if (this.level().isClientSide) {
            for (int i = 0; i < 4; ++i) {
                this.level().addParticle(fogParticle(),
                        this.getX() + (this.random.nextDouble() - 0.5D) * 4.0D,
                        this.getY() + this.random.nextDouble() * 0.4D - 0.2D,
                        this.getZ() + (this.random.nextDouble() - 0.5D) * 4.0D,
                        0.0D, -0.05D, 0.0D);
            }
            return;
        }
        if (this.life > 20 && this.owner != null && this.level() instanceof ServerLevel server) {
            AABB area = this.getBoundingBox().inflate(1.0D, 8.0D, 1.0D).move(0.0D, -8.0D, 0.0D);
            int amplitude = Math.max(0, (int) Math.ceil(this.spellLevel / 2.0D) - 1);
            MobEffect corrosive = jerotesCorrosive();
            for (LivingEntity victim : server.getEntitiesOfClass(LivingEntity.class, area)) {
                if (victim == this.owner || this.owner.isAlliedTo(victim)) {
                    continue;
                }
                if (victim.distanceToSqr(this) > 128.0D * 128.0D) {
                    continue;
                }
                victim.addEffect(new MobEffectInstance(
                        corrosive == null ? MobEffects.POISON : corrosive,
                        20 * Math.max(1, this.spellLevel), amplitude, false, corrosive != null));
            }
        }
        if (this.life > this.duration) {
            this.discard();
        }
    }

    private static MobEffect jerotesCorrosive() {
        if (!corrosiveResolved) {
            corrosiveResolved = true;
            corrosiveCache = ForgeRegistries.MOB_EFFECTS.getValue(
                    ResourceLocation.fromNamespaceAndPath("jerotes", "corrosive"));
        }
        return corrosiveCache;
    }

    private static boolean corrosiveResolved;
    private static MobEffect corrosiveCache;

    private static ParticleOptions fogParticle() {
        if (!fogResolved) {
            fogResolved = true;
            Object type = ForgeRegistries.PARTICLE_TYPES.getValue(
                    ResourceLocation.fromNamespaceAndPath("jerotesvillage", "corrosiver_breath_fog"));
            fogCache = type instanceof net.minecraft.core.particles.SimpleParticleType simple
                    ? simple : ParticleTypes.SNOWFLAKE;
        }
        return fogCache;
    }

    private static boolean fogResolved;
    private static ParticleOptions fogCache;
}
