package cn.blockforge.generated.goetysupreme.compat;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.frost.FrostNovaSpell;
import com.Polarice3.Goety.config.SpellConfig;
import com.Polarice3.Goety.utils.ModDamageSource;
import com.Polarice3.Goety.utils.WandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.util.ArrayList;
import java.util.List;
import cn.blockforge.generated.goetysupreme.compat.GoetyFrostNovaHelper;
import cn.blockforge.generated.goetysupreme.util.IframeHit;
import cn.blockforge.generated.goetysupreme.entity.MutantSnowGolemServant;

public final class GoetyFrostNovaHelper {
    private GoetyFrostNovaHelper() {
    }

    private static final double NOVA_RADIUS = 4.5D;

    private static final int NOVA_POTENCY = 1;

    private static final int NOVA_FREEZE_TICKS = 100;

    private static final double NOVA_SEARCH_PADDING = 2.0D;

    public static void castAt(ServerLevel level, MutantSnowGolemServant source, Vec3 position) {
        Vec3 original = source.position();
        float originalYaw = source.getYRot();
        float originalPitch = source.getXRot();
        source.setPos(position.x, position.y, position.z);
        try {
            BlockPos origin = source.blockPosition();
            Vec3 center = new Vec3(origin.getX(), origin.getY(), origin.getZ());
            List<LivingEntity> watched = novaCandidates(level, source, center);
            float[] healthBefore = new float[watched.size()];
            float[] absorptionBefore = new float[watched.size()];
            for (int i = 0; i < watched.size(); i++) {
                healthBefore[i] = watched.get(i).getHealth();
                absorptionBefore[i] = watched.get(i).getAbsorptionAmount();
            }
            new ExtraFrostNovaSpell().SpellResult(level, source, ItemStack.EMPTY,
                    new SpellStat(NOVA_POTENCY, 1, 16, NOVA_RADIUS, 0, 0.0F));
            settleMissedByExplosion(level, source, center, watched, healthBefore, absorptionBefore);
        } finally {
            source.setPos(original.x, original.y, original.z);
            source.setYRot(originalYaw);
            source.setXRot(originalPitch);
        }
    }

    private static List<LivingEntity> novaCandidates(ServerLevel level, MutantSnowGolemServant source, Vec3 center) {
        AABB area = new AABB(center, center).inflate(NOVA_RADIUS + NOVA_SEARCH_PADDING);
        List<LivingEntity> candidates = new ArrayList<>();
        for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entity == source || entity == source.getTrueOwner() || !entity.isAlive()
                    || entity.isSpectator() || entity.isRemoved()) {
                continue;
            }
            if (entity instanceof Player player && player.isCreative()) {
                continue;
            }
            if (source.isAlliedTo(entity)) {
                continue;
            }
            candidates.add(entity);
        }
        return candidates;
    }

    private static void settleMissedByExplosion(ServerLevel level, MutantSnowGolemServant source, Vec3 center,
            List<LivingEntity> watched, float[] healthBefore, float[] absorptionBefore) {
        if (watched.isEmpty()) {
            return;
        }
        float damage = rollNovaDamage(level);
        for (int i = 0; i < watched.size(); i++) {
            LivingEntity victim = watched.get(i);
            if (!victim.isAlive() || victim.isRemoved() || victim.level() != level) {
                continue;
            }
            if (tookDamage(victim, healthBefore[i], absorptionBefore[i])) {
                continue;
            }
            if (!withinNova(victim, center, NOVA_RADIUS)) {
                continue;
            }
            IframeHit.hurt(victim, extraDamageSource(ModDamageSource.directFreeze(source)), damage);
            if (victim.isAlive()) {
                victim.addEffect(new MobEffectInstance(GoetyEffects.FREEZING.get(), NOVA_FREEZE_TICKS, 0));
            }
        }
    }

    private static boolean tookDamage(LivingEntity victim, float healthBefore, float absorptionBefore) {
        return victim.getHealth() < healthBefore - 1.0E-4F
                || victim.getAbsorptionAmount() < absorptionBefore - 1.0E-4F;
    }

    private static boolean withinNova(LivingEntity victim, Vec3 center, double radius) {
        AABB box = victim.getBoundingBox();
        double dx = Math.max(Math.max(box.minX - center.x, 0.0D), center.x - box.maxX);
        double dy = Math.max(Math.max(box.minY - center.y, 0.0D), center.y - box.maxY);
        double dz = Math.max(Math.max(box.minZ - center.z, 0.0D), center.z - box.maxZ);
        return dx * dx + dy * dy + dz * dz <= radius * radius;
    }

    private static float rollNovaDamage(ServerLevel level) {
        float min = SpellConfig.FrostNovaDamage.get().floatValue() * WandUtil.damageMultiply() + NOVA_POTENCY;
        float max = SpellConfig.FrostNovaMaxDamage.get().floatValue() * WandUtil.damageMultiply() + NOVA_POTENCY;
        int span = (int) (max - min);
        float extra = span > 0 ? level.random.nextInt(span) : 0.0F;
        return Math.min(max, Math.max(min, min + extra));
    }

    public static final class ExtraFrostNovaSpell extends FrostNovaSpell {
    }

    public static DamageSource extraDamageSource(DamageSource original) {
        return new DamageSource(original.typeHolder(), original.getDirectEntity(), original.getEntity(),
                original.sourcePositionRaw()) {
            @Override
            public boolean is(TagKey<DamageType> tag) {
                return DamageTypeTags.BYPASSES_COOLDOWN.equals(tag) || original.is(tag);
            }
        };
    }
}
