package cn.blockforge.generated.goetysupreme.renderer;

import com.Polarice3.Goety.client.particles.WindBlowParticleOption;
import com.Polarice3.Goety.utils.ColorUtil;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractGreenSmith;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithFx;

public final class GreenSmithFx {

    private GreenSmithFx() {
    }

    public static ParticleOptions necroFire() {
        return particle("necro_fire");
    }

    public static ParticleOptions necroEffect() {
        return particle("necro_effect");
    }

    public static ParticleOptions leech() {
        return particle("leech");
    }

    public static ParticleOptions heal() {
        return particle("heal");
    }

    public static ParticleOptions lich() {
        return particle("lich");
    }

    public static ParticleOptions particle(String path) {
        ParticleType<?> type = ForgeRegistries.PARTICLE_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", path));
        if (type instanceof SimpleParticleType simple) {
            return simple;
        }
        return ParticleTypes.SMOKE;
    }

    public static ParticleOptions windBlow(int width, float height) {
        ParticleType<?> type = ForgeRegistries.PARTICLE_TYPES.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", "wind_blow"));
        if (type != null) {
            return new WindBlowParticleOption(new ColorUtil(0xFFFFFF), width, height);
        }
        return ParticleTypes.CLOUD;
    }

    public static ParticleOptions chillHideWind(int rgb, float width, float height, int ownerId) {
        try {
            Class<?> optionClass = Class.forName("com.Polarice3.Goety.client.particles.WindParticleOption");
            Object color = goetyColor(rgb);
            return (ParticleOptions) optionClass
                    .getConstructor(color.getClass(), float.class, float.class, int.class)
                    .newInstance(color, width, height, ownerId);
        } catch (ReflectiveOperationException | LinkageError e) {
            return null;
        }
    }

    private static Object goetyColor(int rgb) throws ReflectiveOperationException {
        Class<?> colorClass = Class.forName("com.Polarice3.Goety.utils.ColorUtil");
        return colorClass.getConstructor(int.class).newInstance(rgb);
    }

    public static MobEffect ironHide() {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", "iron_hide"));
        return effect != null ? effect : MobEffects.DAMAGE_RESISTANCE;
    }

    public static MobEffect shielded() {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", "shielded"));
        return effect != null ? effect : MobEffects.DAMAGE_RESISTANCE;
    }

    public static MobEffect rallying() {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", "rallying"));
        return effect != null ? effect : MobEffects.DAMAGE_BOOST;
    }

    public static MobEffect buff() {
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(
                ResourceLocation.fromNamespaceAndPath("goety", "buff"));
        return effect != null ? effect : MobEffects.DAMAGE_BOOST;
    }

    public static SoundEvent sound(String path) {
        SoundEvent custom = customSound(path);
        if (custom != null) {
            return custom;
        }
        return ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("goety", path));
    }

    private static SoundEvent customSound(String path) {
        net.minecraftforge.registries.RegistryObject<SoundEvent> ref = switch (path) {
            case "lich_ambient" -> GeneratedMod.GREEN_SMITH_AMBIENT;
            case "lich_hurt" -> GeneratedMod.GREEN_SMITH_HURT;
            case "lich_death" -> GeneratedMod.GREEN_SMITH_DEATH;
            case "necromancer_step" -> GeneratedMod.GREEN_SMITH_STEP;
            case "lich_teleport_out" -> GeneratedMod.GREEN_SMITH_TELEPORT_OUT;
            case "lich_teleport_in" -> GeneratedMod.GREEN_SMITH_TELEPORT_IN;
            case "lich_laugh" -> GeneratedMod.GREEN_SMITH_LAUGH;
            case "soul_bolt_cast" -> GeneratedMod.GREEN_SMITH_CAST;
            case "thunderbolt" -> GeneratedMod.GREEN_SMITH_THUNDERBOLT;
            case "thunder_strike_fast" -> GeneratedMod.GREEN_SMITH_THUNDER_STRIKE;
            case "thunder_strike_epic" -> GeneratedMod.GREEN_SMITH_THUNDER_STRIKE_EPIC;
            case "hell_bolt_shoot" -> GeneratedMod.GREEN_SMITH_HELL_BOLT;
            case "corrupt_beam_start" -> GeneratedMod.GREEN_SMITH_BEAM_START;
            case "corrupt_beam_loop" -> GeneratedMod.GREEN_SMITH_BEAM_LOOP;
            case "soul_eat" -> GeneratedMod.GREEN_SMITH_SOUL_EAT;
            case "wind_blast" -> GeneratedMod.GREEN_SMITH_WIND_BLAST;
            default -> null;
        };
        return ref == null ? null : ref.get();
    }
}
