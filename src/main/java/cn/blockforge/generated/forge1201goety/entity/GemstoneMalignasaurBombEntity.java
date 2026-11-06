package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBombEntity;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.entity.JerotesMagicProjectile;
import cn.blockforge.generated.goetysupreme.renderer.MalignasaurBombRenderer;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class GemstoneMalignasaurBombEntity extends JerotesMagicProjectile {

    private static final int MAX_LIFE = 240;

    private int spellLevelDamage = 4;
    private float spellLevelExplode = 1.0F;

    public GemstoneMalignasaurBombEntity(EntityType<? extends GemstoneMalignasaurBombEntity> type, Level level) {
        super(type, level);
    }

    public GemstoneMalignasaurBombEntity(Level level, LivingEntity owner, double x, double y, double z) {
        super(ModEntityTypes.GEMSTONE_MALIGNASAUR_BOMB.get(), owner, level);
        this.setPos(x, y, z);
    }

    public GemstoneMalignasaurBombEntity(int spellLevelDamage, float spellLevelExplode,
            Level level, LivingEntity owner, double x, double y, double z) {
        this(level, owner, x, y, z);
        this.spellLevelDamage = spellLevelDamage;
        this.spellLevelExplode = spellLevelExplode;
    }

    @Override
    public int getMaxLife() {
        return MAX_LIFE;
    }

    @Override
    public ParticleOptions getTrailParticle() {
        return AbstractJerotesServant.jerotesParticle("gemstone_bubble", ParticleTypes.BUBBLE);
    }

    @Override
    public float getInertia() {
        return 1.0F;
    }

    @Override
    public float getLiquidInertia() {
        return 1.0F;
    }

    @Override
    public void lastBreak() {
        this.explode();
    }

    @Override
    public void tick() {
        if (this.getLife() >= this.getMaxLife()) {
            this.playSound(SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, 3.0F, 1.0F);
        }
        super.tick();
        if (!this.level().isClientSide() && this.getOwner() instanceof LivingEntity owner) {
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,
                    this.getBoundingBox().inflate(2.0D, 2.0D, 2.0D));
            for (LivingEntity attack : list) {
                if (attack == null) {
                    continue;
                }
                if (!this.hasLineOfSightEntity(attack) || !owner.canAttack(attack)) {
                    continue;
                }
                attack.hurt(JerotesMagicDamage.bypassesCooldownDrown(this, owner), rollDice());
                this.explode();
                this.playSound(AbstractJerotesServant.jerotesCoreSound("spell", SoundEvents.WITCH_THROW),
                        1.0F, 1.0F);
                this.discard();
            }
        }
        if (this.tickCount % 20 == 0 && !this.onGround()) {
            Vec3 movement = this.getDeltaMovement();
            this.setDeltaMovement(movement.x, -0.15D, movement.z);
        } else if (this.tickCount % 20 == 10 && !this.onGround()) {
            Vec3 movement = this.getDeltaMovement();
            this.setDeltaMovement(movement.x, 0.15D, movement.z);
        }
    }

    private void explode() {
        if (this.level().isClientSide()) {
            return;
        }
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.GENERIC_EXPLODE, this.getSoundSource(), 3.0F, 0.5F);
        if (!(this.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        ParticleOptions particle = AbstractJerotesServant.jerotesParticle("gemstone_bubble_land",
                ParticleTypes.BUBBLE_POP);
        for (int i = 0; i < 2; ++i) {
            RandomSource random = RandomSource.create();
            double dx = random.nextGaussian() * 0.02D;
            double dy = random.nextGaussian() * 0.02D;
            double dz = random.nextGaussian() * 0.02D;
            double x = this.getX((2.0D * random.nextDouble() - 1.0D) * (3.0D + random.nextDouble() * 2.0D));
            double y = this.getY((2.0D * random.nextDouble() - 1.0D) * (3.0D + random.nextDouble() * 2.0D));
            double z = this.getZ((2.0D * random.nextDouble() - 1.0D) * (3.0D + random.nextDouble() * 2.0D));
            serverLevel.sendParticles(particle, x, y, z, 0, dx, dy, dz, 0.0D);
            double centerY = this.getY(0.5D);
            for (int j = 0; j < 40; ++j) {
                RandomSource random2 = RandomSource.create();
                double angle = random2.nextDouble() * Math.PI * 2.0D;
                double inclination = random2.nextDouble() * Math.PI;
                double ox = Math.sin(inclination) * Math.cos(angle);
                double oy = Math.cos(inclination);
                double oz = Math.sin(inclination) * Math.sin(angle);
                serverLevel.sendParticles(particle, this.getX(), centerY, this.getZ(),
                        0, ox * 0.5D, oy * 0.5D, oz * 0.5D, 1.0D);
            }
        }
    }

    private boolean hasLineOfSightEntity(Entity target) {
        if (target.level() != this.level()) {
            return false;
        }
        Vec3 from = new Vec3(this.getX(), this.getEyeY(), this.getZ());
        Vec3 to = new Vec3(target.getX(), target.getEyeY(), target.getZ());
        return this.level().clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this))
                .getType() == HitResult.Type.MISS;
    }

    private float rollDice() {
        int total = 0;
        for (int i = 0; i < Math.max(1, this.spellLevelDamage); ++i) {
            total += this.random.nextInt(4) + 1;
        }
        return total;
    }
}
