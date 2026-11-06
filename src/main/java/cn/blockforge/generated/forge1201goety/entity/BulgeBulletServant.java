package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.entity.AbstractBulgeServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.BulgeBulletServant;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;

public class BulgeBulletServant extends ThrowableItemProjectile {

    private static final int MAX_LIFE = 90;

    private static final double ACCELERATION = 0.1D;

    private int spellLevelDamage = 1;

    private float damageBonus;

    public BulgeBulletServant(EntityType<? extends BulgeBulletServant> entityType, Level level) {
        super(entityType, level);
    }

    public BulgeBulletServant(EntityType<? extends BulgeBulletServant> entityType, LivingEntity shooter,
            Level level, int spellLevelDamage) {
        super(entityType, shooter, level);
        this.spellLevelDamage = spellLevelDamage;
    }

    public int getSpellLevelDamage() {
        return spellLevelDamage;
    }

    public void setSpellLevelDamage(int spellLevelDamage) {
        this.spellLevelDamage = spellLevelDamage;
    }

    public float getDamageBonus() {
        return damageBonus;
    }

    public void setDamageBonus(float damageBonus) {
        this.damageBonus = damageBonus;
    }

    @Override
    public Item getDefaultItem() {
        ItemStack stack = AbstractJerotesServant.jerotesItem("bulge_bullet");
        return stack.isEmpty() ? Items.SNOWBALL : stack.getItem();
    }

    @Override
    public float getGravity() {
        return 0.0F;
    }

    @Override
    public boolean canHitEntity(Entity target) {
        return !(getOwner() instanceof AbstractJerotesServant servant && servant.isAlliedTo(target))
                && super.canHitEntity(target);
    }

    @Override
    public void tick() {
        super.tick();
        if (isRemoved()) {
            return;
        }
        if (tickCount > MAX_LIFE && !level().isClientSide) {
            discard();
            return;
        }
        Vec3 motion = getDeltaMovement().scale(1.0D / (isInWater() ? 0.8F : 0.99F));
        setDeltaMovement(motion.add(motion.normalize().scale(ACCELERATION)));
        if (level().isClientSide) {
            for (int i = 0; i < 2; ++i) {
                level().addParticle(ParticleTypes.BUBBLE,
                        getRandomX(0.25D), getRandomY(), getRandomZ(0.25D),
                        0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (level().isClientSide) {
            return;
        }
        Entity hit = result.getEntity();
        if (!(hit instanceof LivingEntity living)) {
            return;
        }
        if (hit instanceof AbstractBulgeServant) {
            if (!(getOwner() instanceof net.minecraft.world.entity.Mob caster) || caster.getTarget() != hit) {
                return;
            }
        }
        float damageScale = living.isSensitiveToWater() ? 3.0F : 1.0F;
        float base = rollDice1d8() + 1.0F + damageBonus;
        if (!hit.hurt(JerotesMagicDamage.drownMagic(this, getOwner()), base * damageScale)) {
            hit.hurt(damageSources().indirectMagic(this, getOwner()), base * damageScale);
        } else {
            living.clearFire();
        }
        discard();
    }

    private int rollDice1d8() {
        return 1 + random.nextInt(8);
    }

    @Override
    public void onHit(HitResult result) {
        if (result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult ehr
                && ehr.getEntity() instanceof AbstractBulgeServant) {
            if (level().isClientSide
                    || !(getOwner() instanceof net.minecraft.world.entity.Mob caster) || caster.getTarget() != ehr.getEntity()) {
                return;
            }
        }
        super.onHit(result);
        if (!level().isClientSide) {
            discard();
        }
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(getDefaultItem());
    }
}
