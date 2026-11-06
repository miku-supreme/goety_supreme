package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.ModDamageSource;
import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.event.ServantHandler;
import cn.blockforge.generated.goetysupreme.util.ServantRobeFireDamage;

public final class ServantRobeFireDamage {

    private ServantRobeFireDamage() {
    }

    private static final TagKey<DamageType> FIRE_ATTACKS =
            TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("goety", "fire_attacks"));

    public static boolean isPlainFire(@Nullable DamageSource source) {
        if (source == null) {
            return false;
        }
        if (!source.is(DamageTypeTags.IS_FIRE) && !source.is(FIRE_ATTACKS)) {
            return false;
        }
        return !ModDamageSource.isMagicFire(source) && !ModDamageSource.hellfireAttacks(source);
    }

    public static boolean isFireBreath(@Nullable DamageSource source) {
        return source != null && source.is(ModDamageSource.FIRE_BREATH);
    }

    public static int fireBreathIgniteSeconds() {
        return Math.max(0, 5 * GolemServantConfig.FURNACE.FIRE_BREATH_BURNING.get());
    }

    @Nullable
    public static LivingEntity masterOf(@Nullable LivingEntity servant) {
        if (servant instanceof IOwned owned) {
            return owned.getMasterOwner();
        }
        return null;
    }

    public static boolean masterWearsFireRobe(@Nullable LivingEntity servant) {
        LivingEntity master = masterOf(servant);
        return master != null && (CuriosFinder.hasNetherRobe(master) || CuriosFinder.hasUnholyRobe(master));
    }

    public static DamageSource upgrade(@Nullable LivingEntity servant, DamageSource source) {
        if (servant == null || !isPlainFire(source)) {
            return source;
        }
        LivingEntity master = masterOf(servant);
        if (master == null) {
            return source;
        }
        if (CuriosFinder.hasNetherRobe(master)) {
            return ModDamageSource.magicFireBreath(servant, servant);
        }
        if (CuriosFinder.hasUnholyRobe(master)) {
            return ModDamageSource.hellfire(servant, servant);
        }
        return source;
    }
}
