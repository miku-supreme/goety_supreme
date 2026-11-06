package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.client.particles.CircleExplodeParticleOption;
import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.Polarice3.Goety.client.particles.SphereExplodeParticleOption;
import com.Polarice3.Goety.config.SpellConfig;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.RandomUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import cn.blockforge.generated.goetysupreme.compat.GoetyCompat;
import cn.blockforge.generated.goetysupreme.client.particles.SoulVFXBridge;
import cn.blockforge.generated.goetysupreme.spell.ShockwaveFocus;

public final class ShockwaveFocus {

    private static final ColorUtil COLOR = new ColorUtil(2804175);

    private ShockwaveFocus() {
    }

    public static void cast(ServerLevel level, @Nullable LivingEntity caster, double x, double y, double z,
            int radius, int potency) {
        BlockPos center = BlockPos.containing(x, y, z);
        for (int i = -radius; i < radius; i++) {
            for (int k = -radius; k < radius; k++) {
                BlockPos pos = center.offset(i, 0, k);
                if (level.random.nextFloat() <= 0.05F) {
                    level.sendParticles(ModParticleTypes.SOUL_EXPLODE.get(),
                            pos.getX(), pos.getY(), pos.getZ(), 0, 0.0D, 0.04D, 0.0D, 0.5D);
                }
            }
        }
        level.sendParticles(new CircleExplodeParticleOption(COLOR.red, COLOR.green, COLOR.blue, radius, 1),
                x, y + 0.5D, z, 0, 0.0D, 0.0D, 0.0D, 0.0D);
        level.sendParticles(new SphereExplodeParticleOption(COLOR.red, COLOR.green, COLOR.blue, radius, 1),
                x, y + 0.5D, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
        if (GoetyCompat.isGoety60()) {
            SoulVFXBridge.cubeSpawn(level, new Vec3(x, y + 0.5D, z));
        }

        float damage = SpellConfig.ShockwaveDamage.get().floatValue() + potency;
        float maxDamage = SpellConfig.ShockwaveMaxDamage.get().floatValue() + potency;
        float trueDamage = damage >= maxDamage ? damage
                : Mth.clamp(damage + RandomUtil.nextInt(level.random, (int) (maxDamage - damage)), damage, maxDamage);

        level.playSound(null, x, y, z, ModSounds.SOUL_EXPLODE.get(), SoundSource.HOSTILE, 2.0F, 1.0F);
        MobUtil.explosionDamage(level, caster, level.damageSources().indirectMagic(caster, caster),
                center, radius, trueDamage);
    }
}
