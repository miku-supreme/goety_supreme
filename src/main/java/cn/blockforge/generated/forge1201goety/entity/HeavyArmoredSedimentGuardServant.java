package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import com.Polarice3.Goety.utils.ModDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.EliteServant;
import cn.blockforge.generated.goetysupreme.entity.HeavyArmoredSedimentGuardServant;
import cn.blockforge.generated.goetysupreme.util.RingSetBonuses;

public class HeavyArmoredSedimentGuardServant extends AbstractSedimentServant implements EliteServant, RangedAttackMob {

    private static final double SWEEP_INFLATE_XZ = 1.0D;
    private static final double SWEEP_INFLATE_Y = 0.25D;

    private static final float SWEEP_DAMAGE_FACTOR = 0.5F;

    public HeavyArmoredSedimentGuardServant(
            EntityType<? extends HeavyArmoredSedimentGuardServant> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 20;
        setShieldLevel(2);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 35.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    public JerotesVillageServantConfig.Section configSection() {
        return JerotesVillageServantConfig.HEAVY_ARMORED_SEDIMENT_GUARD;
    }

    @Override
    public String servantTexturePath() {
        return "textures/entity/heavy_armored_sediment_guard.png";
    }

    @Override
    public EquipmentSlot replacementSlotFor(net.minecraft.world.item.ItemStack held) {
        if (held.isEmpty()) {
            return null;
        }
        if (held.is(GUARD_HEAVY_ARMOR)) {
            return armorSlotFor(held);
        }
        if (held.is(GUARD_HEAVY_WEAPON)) {
            return EquipmentSlot.MAINHAND;
        }
        return null;
    }

    @Override
    public void registerCombatGoals() {
        registerSedimentRangedGoals(this);
        this.goalSelector.addGoal(2, new SedimentMeleeGoal(this, 1.2D, true));
    }

    @Override
    public boolean canRaiseShield() {
        return !holdsBow() && !holdsOtherRangedWeapon();
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        shootAncientArrow(target);
    }

    @Override
    public void equipDefaultGear(RandomSource random) {
        equipJerotes(EquipmentSlot.HEAD, "ancient_second_rounder_heavy_helmet");
        equipJerotes(EquipmentSlot.CHEST, "ancient_second_rounder_heavy_chestplate");
        equipJerotes(EquipmentSlot.LEGS, "ancient_second_rounder_heavy_leggings");
        equipJerotes(EquipmentSlot.FEET, "ancient_second_rounder_heavy_boots");
        float roll = random.nextFloat();
        if (RingSetBonuses.ancientSwordExcluded(this)) {
            while (roll > 0.65F) {
                roll = random.nextFloat();
            }
        }
        if (roll > 0.65F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_greatsword");
        } else if (roll > 0.35F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_battleaxe");
        } else if (roll > 0.25F) {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_pike");
        } else {
            equipJerotes(EquipmentSlot.MAINHAND, "ancient_second_rounder_warhammer");
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && !level().isClientSide && target != null && !getMainHandItem().isEmpty()) {
            float sweepDamage = (float) getAttributeValue(Attributes.ATTACK_DAMAGE) * SWEEP_DAMAGE_FACTOR;
            for (LivingEntity nearby : level().getEntitiesOfClass(LivingEntity.class,
                    target.getBoundingBox().inflate(SWEEP_INFLATE_XZ, SWEEP_INFLATE_Y, SWEEP_INFLATE_XZ))) {
                if (nearby == target || nearby == this || isAlliedTo(nearby) || !canAttack(nearby)) {
                    continue;
                }
                nearby.hurt(ModDamageSource.summonAttack(this, getTrueOwner()), sweepDamage);
            }
            playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.0F);
            if (level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                        target.getX(), target.getY(0.5D), target.getZ(),
                        1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        return hit;
    }
}
