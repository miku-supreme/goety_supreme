package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import cn.blockforge.generated.goetysupreme.entity.AbstractJerotesServant;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurWhirlpoolEntity;
import cn.blockforge.generated.goetysupreme.compat.JerotesMagicDamage;
import cn.blockforge.generated.goetysupreme.entity.JerotesMagicProjectile;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public class GemstoneMalignasaurWhirlpoolEntity extends JerotesMagicProjectile {

    private static final int MAX_LIFE = 80;

    private int spellLevelDamage = 4;
    private int spellLevelMoveDistance = 4;

    public GemstoneMalignasaurWhirlpoolEntity(EntityType<? extends GemstoneMalignasaurWhirlpoolEntity> type, Level level) {
        super(type, level);
    }

    public GemstoneMalignasaurWhirlpoolEntity(Level level, LivingEntity owner, double x, double y, double z) {
        super(ModEntityTypes.GEMSTONE_MALIGNASAUR_WHIRLPOOL.get(), owner, level);
        this.setPos(x, y, z);
    }

    public void setSpellLevelDamage(int spellLevelDamage) {
        this.spellLevelDamage = Math.max(1, spellLevelDamage);
    }

    public void setSpellLevelMoveDistance(int spellLevelMoveDistance) {
        this.spellLevelMoveDistance = Math.max(1, spellLevelMoveDistance);
    }

    @Override
    public void tick() {
        super.tick();
        this.level().addParticle(
                AbstractJerotesServant.jerotesParticle("whirlpool", ParticleTypes.BUBBLE),
                this.getX(), (this.getY() + this.getEyeY()) / 2.0D, this.getZ(),
                0.0D, 0.0D, 0.0D);
    }

    @Override
    public int getMaxLife() {
        return MAX_LIFE;
    }

    @Override
    public ParticleOptions getTrailParticle() {
        return ParticleTypes.BUBBLE_COLUMN_UP;
    }

    @Override
    public float getInertia() {
        return 0.85F;
    }

    @Override
    public float getLiquidInertia() {
        return 1.0F;
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide()) {
            return;
        }
        Entity hit = result.getEntity();
        if (hit instanceof LivingEntity victim && this.getOwner() instanceof LivingEntity owner) {
            if (victim == owner || victim.isAlliedTo(owner)) {
                return;
            }
            float damageScale = victim.isSensitiveToWater() ? 3.0F : 1.0F;
            float damage = rollDice();
            boolean waterBreathing = victim.hasEffect(MobEffects.WATER_BREATHING)
                    || victim.hasEffect(MobEffects.CONDUIT_POWER);
            if (waterBreathing) {
                damage /= 3.0F;
            }
            damage *= damageScale;
            if (victim.isInWater()) {
                victim.hurt(JerotesMagicDamage.drownMagic(this, owner), damage);
            } else {
                victim.hurt(this.damageSources().indirectMagic(this, owner), damage);
            }
            if (mobSizeSmall(owner) || mobSizeMedium(owner) || mobSizeLarge(owner)) {
                if (!isNoSpecialKnockback(owner.getType())) {
                    double resist = victim.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)
                            + this.spellLevelMoveDistance * 0.1D;
                    double factor = Math.max(this.spellLevelMoveDistance * 0.1D,
                            1.0D + this.spellLevelMoveDistance * 0.1D - resist);
                    victim.setDeltaMovement(victim.getDeltaMovement().add(
                            (owner.getX() - victim.getX()) * 0.35D * factor, 0.0D, 0.0D));
                    victim.setDeltaMovement(victim.getDeltaMovement().add(
                            0.0D, (owner.getY() - victim.getY()) * 0.25D * factor, 0.0D));
                    victim.setDeltaMovement(victim.getDeltaMovement().add(
                            0.0D, 0.0D, (owner.getZ() - victim.getZ()) * 0.35D * factor));
                }
            }
        }
        this.discard();
    }

    private static float mobWidth(Entity entity) {
        return (float) ((entity.getBoundingBox().getXsize() + entity.getBoundingBox().getZsize()) / 2.0D);
    }

    private static float mobHeight(Entity entity) {
        return (float) entity.getBoundingBox().getYsize();
    }

    private static boolean mobSizeFind(Entity entity, float average, float max) {
        return !(mobWidth(entity) >= max || mobHeight(entity) >= max
                || mobWidth(entity) >= average && mobHeight(entity) >= average);
    }

    private static boolean mobSizeSmall(Entity entity) {
        return mobSizeFind(entity, 0.75F, 1.75F);
    }

    private static boolean mobSizeMedium(Entity entity) {
        return mobSizeFind(entity, 1.75F, 3.5F) && !mobSizeSmall(entity);
    }

    private static boolean mobSizeLarge(Entity entity) {
        return mobSizeFind(entity, 3.5F, 6.0F) && !mobSizeSmall(entity) && !mobSizeMedium(entity);
    }

    private static boolean isNoSpecialKnockback(EntityType<?> type) {
        return type.is(TagKey.create(Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath("jerotes", "no_special_knockback")));
    }

    @Override
    public void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    private float rollDice() {
        int count = Math.max(1, this.spellLevelDamage * 3);
        int total = 0;
        for (int i = 0; i < count; ++i) {
            total += this.random.nextInt(4) + 1;
        }
        return total;
    }
}
