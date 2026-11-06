package cn.blockforge.generated.goetysupreme.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

public class LightningWormThunderstormEntity extends AbstractHurtingProjectile {
    private static final int MAX_LIFE = 80;
    private static final int MAX_CHAIN = 3;

    private static final double CHAIN_SEARCH = 12.0D;

    private static final double CHAIN_RANGE = 9.0D;

    private int spellLevel = 3;
    private int life;
    private LivingEntity target;

    public LightningWormThunderstormEntity(EntityType<? extends LightningWormThunderstormEntity> type, Level level) {
        super(type, level);
    }

    public LightningWormThunderstormEntity(Level level, LivingEntity owner, LivingEntity target, int spellLevel) {
        super(ModEntityTypes.LIGHTNING_WORM_THUNDERSTORM.get(), owner, target.getX(), target.getEyeY(), target.getZ(), level);
        this.spellLevel = spellLevel;
        this.target = target;
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
        this.playSound(SoundEvents.LIGHTNING_BOLT_IMPACT, 3.0F, 1.0F);
        if (this.level() instanceof ServerLevel serverLevel) {
            Vec3 center = this.position().add(0.0D, this.getBbHeight() * 0.4D, 0.0D);
            JerotesParticleFx.rayHitBurst(serverLevel, center, this.getDeltaMovement(),
                    this.getTrailParticle());
        }
        this.spawnChildren(victim);
    }

    private void spawnChildren(LivingEntity primary) {
        if (!(this.getOwner() instanceof LivingEntity owner)) {
            return;
        }
        List<LivingEntity> candidates = new ArrayList<>(this.level().getEntitiesOfClass(LivingEntity.class,
                this.getBoundingBox().inflate(CHAIN_SEARCH)));
        candidates.removeIf(e -> e == owner || e == primary
                || !owner.canAttack(e) || e.distanceTo(this) > CHAIN_RANGE);
        Collections.shuffle(candidates);
        int n = 0;
        for (LivingEntity find : candidates) {
            if (n >= MAX_CHAIN) {
                break;
            }
            LightningWormChainLightningEntity child = new LightningWormChainLightningEntity(
                    this.level(), owner, find, this.spellLevel, primary);
            Vec3 spawn = primary.position().add(0.0D, primary.getBbHeight() * 0.7D, 0.0D);
            child.setPos(spawn.x, spawn.y, spawn.z);
            this.level().addFreshEntity(child);
            ++n;
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
