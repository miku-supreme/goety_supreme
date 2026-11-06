package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBreathEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesCoreBridge;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.entity.JerotesMagicProjectile;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class GemstoneMalignasaurBreathEntity extends JerotesMagicProjectile {

    private static final int MAX_LIFE = 30;

    private static final float MAX_REACH = 27.0F;

    private int spellLevel = 4;

    private final Set<UUID> piercedTargets = new HashSet<>();

    public GemstoneMalignasaurBreathEntity(EntityType<? extends GemstoneMalignasaurBreathEntity> type, Level level) {
        super(type, level);
    }

    public GemstoneMalignasaurBreathEntity(Level level, LivingEntity owner, double x, double y, double z) {
        super(ModEntityTypes.GEMSTONE_MALIGNASAUR_BREATH.get(), owner, level);
        this.setPos(x, y, z);
    }

    public void setSpellLevel(int spellLevel) {
        this.spellLevel = Math.max(1, spellLevel);
    }

    @Override
    public int getMaxLife() {
        return MAX_LIFE;
    }

    public float getMaxReach() {
        return MAX_REACH;
    }

    public boolean hasCloud() {
        return true;
    }

    public float getCloudRadius() {
        return 1.0F;
    }

    public float getCloudEffectTimeMultiple() {
        return 1.0F;
    }

    public ParticleOptions getCloudParticle() {
        return AbstractJerotesServant.jerotesParticle("ocean_current_breath_fog", ParticleTypes.BUBBLE);
    }

    @Override
    public ParticleOptions getTrailParticle() {
        return AbstractJerotesServant.jerotesParticle("ocean_current_breath", ParticleTypes.BUBBLE);
    }

    @Override
    public float getInertia() {
        return 0.95F;
    }

    @Override
    public float getLiquidInertia() {
        return 1.1F;
    }

    @Override
    public void tick() {
        if (this.getLife() >= this.getMaxLife() && this.hasCloud()) {
            this.spawnCloud(this.getX(), this.getY(), this.getZ());
            this.discard();
        }
        super.tick();
        ParticleOptions trailParticle = this.getTrailParticle();
        if (trailParticle != null) {
            double spread = (double) this.getBbWidth() * ((double) this.getLife() * 0.1D);
            for (int i = 0; i < 5 + this.getLife(); ++i) {
                this.level().addParticle(trailParticle,
                        this.getX() + (this.random.nextDouble() - 0.5D) * spread,
                        this.getY() + (this.random.nextDouble() - 0.5D) * spread,
                        this.getZ() + (this.random.nextDouble() - 0.5D) * spread,
                        0.0D, 0.0D, 0.0D);
            }
        }
        Entity owner = this.getOwner();
        if (owner != null && this.position().distanceToSqr(owner.position()) > (double) this.getMaxReach()
                && !this.level().isClientSide()) {
            this.lastBreak();
            this.spawnCloud(this.getX(), this.getY(), this.getZ());
            this.discard();
        }
        if (!this.level().isClientSide()) {
            List<Boat> boats = this.level().getEntitiesOfClass(Boat.class, this.getBoundingBox().inflate(2.0D, 2.0D, 2.0D));
            for (Boat boat : boats) {
                if (this.isAlliedTo(boat) || this.distanceTo(boat) > 16.0D) {
                    continue;
                }
                this.spawnAtLocation(boat.getDropItem());
                boat.discard();
            }
        }
    }

    private boolean spawnCloud(double x, double y, double z) {
        if (!(this.level() instanceof ServerLevel server)) {
            return false;
        }
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        List<LivingEntity> nearby = this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D));
        nearby.removeIf(living -> living == owner);
        AreaEffectCloud cloud = new AreaEffectCloud(server, x, y, z);
        if (owner != null) {
            cloud.setOwner(owner);
        }
        cloud.setRadius(this.getCloudRadius());
        cloud.setDuration((int) ((float) (20 * this.spellLevel * 3) * this.getCloudEffectTimeMultiple()));
        cloud.setParticle(this.getCloudParticle());
        cloud.setRadiusPerTick((2.0F - cloud.getRadius()) / (float) cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(GeneratedMod.MALIGNASAUR_ASPHYXIA.get(),
                20 * this.spellLevel * 3, Math.max(0, this.spellLevel - 1)));
        for (LivingEntity living : nearby) {
            if (this.distanceToSqr(living) >= 256.0D) {
                continue;
            }
            cloud.setPos(living.getX(), living.getY(), living.getZ());
            break;
        }
        if (!this.isSilent()) {
            this.level().playSound(null, x, y, z,
                    AbstractJerotesServant.jerotesCoreSound("breath", SoundEvents.RAVAGER_ROAR),
                    SoundSource.NEUTRAL, 1.0F, 1.0F);
        }
        server.addFreshEntity(cloud);
        return true;
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide()) {
            return;
        }
        Entity hit = result.getEntity();
        if (hit instanceof LivingEntity attack && this.getOwner() instanceof LivingEntity owner
                && attack != owner && !attack.isAlliedTo(owner)) {
            if (!piercedTargets.add(attack.getUUID())) {
                return;
            }
            float damageScale = attack.isSensitiveToWater() ? 3.0F : 1.0F;
            if (attack.hasEffect(MobEffects.WATER_BREATHING) || attack.hasEffect(MobEffects.CONDUIT_POWER)) {
                damageScale /= 3.0F;
            }
            float damage = this.rollDice(this.spellLevel * 2, 6) * damageScale;
            if (JerotesCoreBridge.savingSpellThrow(attack)) {
                damage /= 2.0F;
            }
            boolean damaged = attack.hurt(JerotesMagicDamage.drownMagic(this, owner), damage);
            if (damaged && !this.isSilent()) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        AbstractJerotesServant.jerotesCoreSound("spell", SoundEvents.WITCH_THROW),
                        SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

    private float rollDice(int count, int sides) {
        int total = 0;
        for (int i = 0; i < Math.max(1, count); ++i) {
            total += this.random.nextInt(sides) + 1;
        }
        return total;
    }

    @Override
    public void onHit(HitResult result) {
        if (result instanceof BlockHitResult blockHit) {
            if (!this.level().isClientSide()
                    && this.level().getBlockState(blockHit.getBlockPos()).is(Blocks.WATER)) {
                return;
            }
            if (!this.level().isClientSide()) {
                this.spawnCloud(blockHit.getLocation().x, blockHit.getLocation().y, blockHit.getLocation().z);
            }
        }
        super.onHit(result);
        if (result instanceof BlockHitResult) {
            this.discard();
        }
    }
}
