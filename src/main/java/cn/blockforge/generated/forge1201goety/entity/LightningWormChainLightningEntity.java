package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesCoreBridge;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.renderer.JerotesParticleFx;
import cn.blockforge.generated.goetysupreme.entity.LightningWormChainLightningEntity;
import cn.blockforge.generated.goetysupreme.entity.LightningWormThunderstormEntity;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class LightningWormChainLightningEntity extends AbstractHurtingProjectile {
    private static final int MAX_LIFE = 40;

    private static final double MAX_DRIFT = 9.0D;

    private int spellLevel = 3;
    private int life;
    private LivingEntity target;

    private Vec3 lastHurtPos;

    public LightningWormChainLightningEntity(EntityType<? extends LightningWormChainLightningEntity> type, Level level) {
        super(type, level);
    }

    public LightningWormChainLightningEntity(Level level, LivingEntity owner, LivingEntity target, int spellLevel) {
        this(level, owner, target, spellLevel, target);
    }

    public LightningWormChainLightningEntity(Level level, LivingEntity owner, LivingEntity target, int spellLevel,
            LivingEntity lastHurt) {
        super(ModEntityTypes.LIGHTNING_WORM_CHAIN_LIGHTNING.get(), owner, target.getX(), target.getEyeY(), target.getZ(), level);
        this.spellLevel = spellLevel;
        this.target = target;
        this.lastHurtPos = lastHurt.position().add(0.0D, lastHurt.getBbHeight() * 0.7D, 0.0D);
    }

    @Override
    public boolean shouldBurn() {
        return false;
    }

    @Override
    public ParticleOptions getTrailParticle() {
        return AbstractJerotesServant.jerotesParticle("worm_thunderstorm", ParticleTypes.ELECTRIC_SPARK);
    }

    @Override
    public void tick() {
        super.tick();
        if (++this.life >= MAX_LIFE) {
            this.discard();
            return;
        }
        if (this.level() instanceof ServerLevel serverLevel) {
            JerotesParticleFx.rayTrail(serverLevel, this, this.getTrailParticle());
        }
        if (this.level().isClientSide()) {
            return;
        }
        if (this.lastHurtPos != null && this.position().distanceTo(this.lastHurtPos) > MAX_DRIFT) {
            this.discard();
            return;
        }
        if (this.target == null || !this.target.isAlive()) {
            this.discard();
            return;
        }
        Vec3 to = this.target.position().add(0.0D, this.target.getBbHeight() * 0.7D, 0.0D)
                .subtract(this.position()).normalize().scale(0.6D);
        this.setDeltaMovement(this.getDeltaMovement().add(to).scale(0.6D));
        if (this.distanceToSqr(this.target) < 1.5D * 1.5D) {
            this.strike(this.target);
            this.discard();
        }
    }

    private void strike(LivingEntity victim) {
        if (this.getOwner() instanceof LivingEntity owner && !victim.isAlliedTo(owner)) {
            float damage = rollDice();
            if (JerotesCoreBridge.savingSpellThrow(victim)) {
                damage /= 2.0F;
            }
            victim.hurt(JerotesMagicDamage.lightningMagic(this, owner), damage);
        }
        this.playSound(SoundEvents.LIGHTNING_BOLT_IMPACT, 10.0F, 1.0F);
        if (this.level() instanceof ServerLevel serverLevel) {
            Vec3 center = this.position().add(0.0D, this.getBbHeight() * 0.4D, 0.0D);
            JerotesParticleFx.rayHitBurst(serverLevel, center, this.getDeltaMovement(),
                    this.getTrailParticle());
        }
    }

    private float rollDice() {
        int count = 8 + (this.spellLevel - 3);
        int total = 0;
        for (int i = 0; i < Math.max(1, count); ++i) {
            total += this.random.nextInt(6) + 1;
        }
        return total;
    }
}
