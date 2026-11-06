package cn.blockforge.generated.goetysupreme.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBombEntity;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBreathAreaServantEntity;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurBreathEntity;
import cn.blockforge.generated.goetysupreme.entity.GemstoneMalignasaurWhirlpoolEntity;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesFocusBurst;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;
import cn.blockforge.generated.goetysupreme.entity.SerpenperthMeteorEntity;

public final class MalignasaurSpells {

    private MalignasaurSpells() {
    }

    public static void oceanCurrentBreath(ServerLevel level, LivingEntity caster, LivingEntity target, int spellLevel) {
        oceanCurrentBreath(level, caster, target, spellLevel, 0);
    }

    public static void oceanCurrentBreath(ServerLevel level, LivingEntity caster, LivingEntity target, int spellLevel,
            int subcategory) {
        castSound(caster, "magic_ocean_current_breath", SoundEvents.GUARDIAN_ATTACK);
        if (subcategory == 0) {
            Vec3 areaLook = caster.getLookAngle();
            GemstoneMalignasaurBreathAreaServantEntity area =
                    new GemstoneMalignasaurBreathAreaServantEntity(level, caster);
            area.setSpellLevel(spellLevel);
            area.setPos(caster.getX() + areaLook.x * 2.25D,
                    caster.getY() + caster.getBbHeight() * 0.25D + 2.0D / 16.0D,
                    caster.getZ() + areaLook.z * 2.25D);
            area.setYRot(caster.getYRot());
            area.setXRot(caster.getXRot());
            level.addFreshEntity(area);
            return;
        }
        int count = Math.max(1, spellLevel);
        for (int i = 0; i < count; ++i) {
            GemstoneMalignasaurBreathEntity breath = new GemstoneMalignasaurBreathEntity(level, caster,
                    caster.getX(), caster.getY(0.7D), caster.getZ());
            breath.setSpellLevel(spellLevel);
            breath.setPower(caster.getLookAngle());
            breath.shootFromRotation(caster, caster.getXRot(),
                    caster.getYRot() - ((count - 1) * 5.0F) / 2.0F + i * 5.0F, 0.0F, 1.0F, 0.0F);
            level.addFreshEntity(breath);
        }
    }

    public static void whirlpool(ServerLevel level, LivingEntity caster, LivingEntity target, int spellLevel) {
        castSound(caster, "magic_whirlpool", SoundEvents.GUARDIAN_ATTACK);
        int count = Math.max(1, spellLevel);
        for (int i = 0; i < count; ++i) {
            GemstoneMalignasaurWhirlpoolEntity whirlpool = new GemstoneMalignasaurWhirlpoolEntity(level, caster,
                    caster.getX(), caster.getY(0.7D), caster.getZ());
            whirlpool.setSpellLevelDamage(spellLevel);
            whirlpool.setSpellLevelMoveDistance(spellLevel);
            whirlpool.setPower(caster.getLookAngle());
            whirlpool.shootFromRotation(caster, caster.getXRot(),
                    caster.getYRot() - ((count - 1) * 10.0F) / 2.0F + i * 10.0F, 0.0F, 1.0F, 0.0F);
            level.addFreshEntity(whirlpool);
        }
    }

    public static void asphyxiaControl(ServerLevel level, LivingEntity caster, LivingEntity target, int spellLevel) {
        castSound(caster, "magic_asphyxia_control", SoundEvents.GUARDIAN_ATTACK);
        double range = spellLevel * 8.0D;
        int duration = (6 + (spellLevel - 1) * 3) * 20;
        int amplifier = Math.max(0, (int) Math.ceil((double) spellLevel / 2.0D) - 1);
        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class,
                caster.getBoundingBox().inflate(range))) {
            if (victim == caster || caster.isAlliedTo(victim) || victim.getMobType() == net.minecraft.world.entity.MobType.WATER
                    || victim.hasEffect(net.minecraft.world.effect.MobEffects.WATER_BREATHING)
                    || victim.hasEffect(net.minecraft.world.effect.MobEffects.CONDUIT_POWER)) {
                continue;
            }
            if (caster.distanceTo(victim) > range * 2.0D) {
                continue;
            }
            victim.addEffect(new MobEffectInstance(GeneratedMod.MALIGNASAUR_ASPHYXIA.get(),
                    duration, amplifier, false, true));
            double resistance = victim.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE);
            double factor = Math.max(0.0D, 1.0D - resistance);
            if (caster.isInWater()) {
                for (int i = 0; i < 5; ++i) {
                    level.sendParticles(jerotesParticle("gemstone_bubble", net.minecraft.core.particles.ParticleTypes.BUBBLE),
                            victim.getRandomX(0.8D), victim.getRandomY(), victim.getRandomZ(0.8D),
                            0, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
            victim.setDeltaMovement(victim.getDeltaMovement().add(
                    -(caster.getX() - victim.getX()) * 0.1D * factor,
                    -(caster.getY() - victim.getY()) * 0.02D * factor,
                    -(caster.getZ() - victim.getZ()) * 0.1D * factor));
            victim.knockback(0.5D,
                    net.minecraft.util.Mth.sin(caster.getYRot() * 0.04F),
                    -net.minecraft.util.Mth.cos(caster.getYRot() * 0.04F));
        }
    }

    public static void bomb(ServerLevel level, LivingEntity caster, LivingEntity target, int spellLevel) {
        castSound(caster, "magic_malignasaur_bomb", SoundEvents.FIREWORK_ROCKET_LAUNCH);
        int count = 48 + caster.getRandom().nextInt(64 - 48 + 1);
        ParticleOptions bubble = jerotesParticle("gemstone_bubble", ParticleTypes.BUBBLE);
        for (int i = 0; i < count; ++i) {
            BlockPos summonPos = SerpenperthMeteorEntity.findSpawnPositionNear(level, caster.blockPosition(), 12);
            GemstoneMalignasaurBombEntity bomb = new GemstoneMalignasaurBombEntity(
                    spellLevel, 2.0F + spellLevel * 0.25F, level, caster, 0.0D, 0.0D, 0.0D);
            bomb.setPos(summonPos.getCenter().subtract(0.0D, 4.0D, 0.0D));
            level.addFreshEntity(bomb);
            level.sendParticles(bubble, bomb.getRandomX(0.5D), bomb.getRandomY(), bomb.getRandomZ(0.5D),
                    20, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void deterrence(ServerLevel level, LivingEntity caster, LivingEntity target, int spellLevel) {
        castFeedback(level, caster, "magic_ocean_dragon_deterrence", SoundEvents.GUARDIAN_AMBIENT,
                "ocean_dragon_deterrence_display", ParticleTypes.BUBBLE);
        level.sendParticles(jerotesParticle("ocean_dragon_deterrence", ParticleTypes.BUBBLE),
                caster.getRandomX(0.2D), caster.getEyeY() + 3.0D, caster.getRandomZ(0.2D),
                4, 0.0D, 0.0D, 0.0D, 0.0D);
        int range = spellLevel * 8;
        int mainTime = spellLevel * 2 * 20;
        int mainLevel = spellLevel / 2;
        for (LivingEntity victim : level.getEntitiesOfClass(LivingEntity.class,
                caster.getBoundingBox().inflate(range))) {
            if (victim == caster || caster.isAlliedTo(victim) || caster.distanceTo(victim) > range * 2.0D
                    || victim.hasEffect(MobEffects.WATER_BREATHING) || victim.hasEffect(MobEffects.CONDUIT_POWER)) {
                continue;
            }
            MobEffect deter = jerotesVillageEffect("ocean_dragon_deterrence");
            victim.addEffect(new MobEffectInstance(deter == null ? MobEffects.DARKNESS : deter,
                    mainTime, Math.max(0, mainLevel - 1), false, true));
            victim.addEffect(new MobEffectInstance(MobEffects.DARKNESS, mainTime, Math.max(0, mainLevel - 1),
                    false, true));
        }
    }

    public static int effectiveSpellLevel(ServerLevel level, int baseSpellLevel) {
        int spellLevel = baseSpellLevel - 1;
        if (level.getDifficulty() == Difficulty.NORMAL) {
            spellLevel = baseSpellLevel;
        } else if (level.getDifficulty() == Difficulty.HARD) {
            spellLevel = baseSpellLevel + 1;
        }
        return spellLevel;
    }

    private static void castSound(LivingEntity caster, String soundPath, SoundEvent fallbackSound) {
        if (caster.isSilent()) {
            return;
        }
        SoundEvent sound = JerotesFocusBurst.jerotesSound(soundPath, fallbackSound);
        if (caster.level() instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                    sound, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    private static void castFeedback(ServerLevel level, LivingEntity caster, String soundPath,
            SoundEvent fallbackSound, String displayParticlePath, ParticleOptions fallbackParticle) {
        castSound(caster, soundPath, fallbackSound);
        level.sendParticles(jerotesParticle(displayParticlePath, fallbackParticle),
                caster.getX(), caster.getBoundingBox().maxY + 0.5D, caster.getZ(),
                0, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    private static Vec3 rotateY(Vec3 vec, double degrees) {
        double rad = Math.toRadians(degrees);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new Vec3(vec.x * cos - vec.z * sin, vec.y, vec.x * sin + vec.z * cos);
    }

    private static ParticleOptions jerotesParticle(String path, ParticleOptions fallback) {
        Object type = ForgeRegistries.PARTICLE_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
        return type instanceof SimpleParticleType simple ? simple : fallback;
    }

    private static MobEffect jerotesVillageEffect(String path) {
        return ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("jerotesvillage", path));
    }
}
