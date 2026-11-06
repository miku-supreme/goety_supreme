package cn.blockforge.generated.goetysupreme.entity;

import com.Polarice3.Goety.client.particles.CircleExplodeParticleOption;
import com.Polarice3.Goety.client.particles.LichShockwaveParticleOption;
import com.Polarice3.Goety.client.particles.SphereExplodeParticleOption;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ModDamageSource;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.client.particles.SoulVFXBridge;

public final class NamelessShockwave {

    // 墓灵怪 grave cube 生成特效的绿色（GraveCube.SUMMON_COLOR = NecroVFXPalette.LIME_HEX，上游 GraveCube.java:55）
    private static final ColorUtil COLOR = new ColorUtil(0x9df431);

    public static final int RADIUS = 7;

    public static final float ATTACK_DAMAGE_MULTIPLIER = 3.0F;

    private NamelessShockwave() {
    }

    public static void cast(ServerLevel level, @Nullable LivingEntity caster, double x, double y, double z,
            int radius, float damage) {
        BlockPos center = BlockPos.containing(x, y, z);
        DustParticleOptions greenDust = new DustParticleOptions(
                new Vector3f(157.0F / 255.0F, 244.0F / 255.0F, 49.0F / 255.0F), 1.0F);
        for (int i = -radius; i < radius; i++) {
            for (int k = -radius; k < radius; k++) {
                BlockPos pos = center.offset(i, 0, k);
                if (level.random.nextFloat() <= 0.05F) {
                    level.sendParticles(greenDust,
                            pos.getX(), pos.getY(), pos.getZ(), 0, 0.0D, 0.04D, 0.0D, 0.5D);
                }
            }
        }
        level.sendParticles(new CircleExplodeParticleOption(COLOR.red, COLOR.green, COLOR.blue, radius, 1),
                x, y + 0.5D, z, 0, 0.0D, 0.0D, 0.0D, 0.0D);
        level.sendParticles(new SphereExplodeParticleOption(COLOR.red, COLOR.green, COLOR.blue, radius, 1),
                x, y + 0.5D, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        if (GoetyCompat.isGoety60()) {
            SoulVFXBridge.cubeSpawn(level, new Vec3(x, y + 0.5D, z), true);
            level.sendParticles(new LichShockwaveParticleOption(COLOR, 8, 6, 2, 40),
                    x, y + 0.5D, z, 0, 0.0D, 0.0D, 0.0D, 0.5D);
        }
        level.playSound(null, x, y, z, ModSounds.SOUL_EXPLODE.get(), SoundSource.HOSTILE,
                2.0F, 1.0F);
        // 伤害来源归属用主人链顶端（玩家），避免 goety areAllies 只认一层主人链导致友伤
        LivingEntity direct = caster instanceof com.Polarice3.Goety.common.entities.ally.Summoned summoned
                ? summoned.getTrueOwner() : null;
        if (direct == null) {
            direct = caster;
        }
        MobUtil.explosionDamage(level, caster, ModDamageSource.indirectSoul(direct, direct),
                center, radius, damage);
    }

    public static void castAtAttack(ServerLevel level, @Nullable LivingEntity caster, double x, double y, double z) {
        cast(level, caster, x, y, z, RADIUS, attackDamage(caster) * ATTACK_DAMAGE_MULTIPLIER);
    }

    public static void castAtAttack(ServerLevel level, @Nullable LivingEntity caster, double x, double y, double z,
            int radius, float damage) {
        cast(level, caster, x, y, z, radius, damage);
    }

    public static float attackDamage(@Nullable LivingEntity caster) {
        if (caster == null || caster.getAttribute(Attributes.ATTACK_DAMAGE) == null) {
            return 0.0F;
        }
        return (float) caster.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }
}
