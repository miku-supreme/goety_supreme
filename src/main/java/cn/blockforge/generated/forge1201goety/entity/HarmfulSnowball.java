package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractGolemServant;
import cn.blockforge.generated.goetysupreme.entity.HarmfulSnowball;
import cn.blockforge.generated.goetysupreme.util.IframeHit;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.util.SnowballVolley;

public class HarmfulSnowball extends Snowball {

    private static final int CHILL_TICKS = 100;

    private float damage = 4.0F;

    private boolean ignoresInvulnerability;

    public HarmfulSnowball(EntityType<? extends Snowball> type, Level level) {
        super(type, level);
    }

    public HarmfulSnowball(Level level, LivingEntity thrower, float damage) {
        this(ModEntityTypes.HARMFUL_SNOWBALL.get(), level);
        setOwner(thrower);
        setPos(thrower.getX(), thrower.getEyeY() - 0.1D, thrower.getZ());
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public boolean ignoresInvulnerability() {
        return ignoresInvulnerability;
    }

    public void setIgnoresInvulnerability(boolean ignoresInvulnerability) {
        this.ignoresInvulnerability = ignoresInvulnerability;
    }

    @Override
    public boolean canHitEntity(Entity target) {
        if (!super.canHitEntity(target)) {
            return false;
        }
        LivingEntity owner = getOwner() instanceof LivingEntity living ? living : null;
        return owner == null || !SnowballVolley.isAlliedTo(owner, target);
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        if (level().isClientSide) {
            return;
        }
        Entity target = result.getEntity();
        Entity owner = getOwner();
        if (target instanceof LivingEntity living
                && (!(owner instanceof LivingEntity livingOwner)
                        || SnowballVolley.isAlliedTo(livingOwner, living))) {
            return;
        }
        boolean hurt = hurtIgnoringInvulnerability(target, owner);
        if (hurt && owner instanceof LivingEntity livingOwner) {
            doEnchantDamageEffects(livingOwner, target);
        }
        applyChill(target);
    }

    private boolean hurtIgnoringInvulnerability(Entity target, Entity owner) {
        DamageSource source = damageSources().thrown(this, owner);
        if (!ignoresInvulnerability || !(target instanceof LivingEntity living)) {
            return target.hurt(source, damage);
        }
        return IframeHit.hurt(living, source, damage);
    }

    private void applyChill(Entity target) {
        if (!(target instanceof LivingEntity living) || !living.isAlive()) {
            return;
        }
        boolean frost = getOwner() instanceof AbstractFrostGolemServant golem
                && golem.ownerWearsFrostRobes();
        MobEffect effect = frost ? GoetyEffects.FREEZING.get() : MobEffects.MOVEMENT_SLOWDOWN;
        living.addEffect(new MobEffectInstance(effect, CHILL_TICKS, 0), getOwner());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", damage);
        tag.putBoolean("IgnoresInvulnerability", ignoresInvulnerability);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Damage")) {
            damage = tag.getFloat("Damage");
        }
        ignoresInvulnerability = tag.getBoolean("IgnoresInvulnerability");
    }
}
