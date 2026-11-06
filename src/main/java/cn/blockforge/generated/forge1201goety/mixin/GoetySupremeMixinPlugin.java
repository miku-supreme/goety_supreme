package cn.blockforge.generated.goetysupreme.mixin;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.compat.JerotesJadeCompat;
import cn.blockforge.generated.goetysupreme.compat.SerponCombatExecuterJadeProvider;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmCindariaSpearDamageMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrNecromancerNativeCapMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrRingWeaponMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrSoulArrowDamageMixin;
import cn.blockforge.generated.goetysupreme.mixin.CataclysmProwlerMissileDamageMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSpiderFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSunkenNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSunkenNecromancerNativeCapMixin;
import cn.blockforge.generated.goetysupreme.mixin.JerotesSpearServantDamageMixin;
import cn.blockforge.generated.goetysupreme.mixin.MutantChemicalXServantMixin;
import cn.blockforge.generated.goetysupreme.mixin.MutantConversionWildMixin;
import cn.blockforge.generated.goetysupreme.mixin.MutantEndermanTeleSmashMixin;
import cn.blockforge.generated.goetysupreme.mixin.MutantEndermanVoidHealMixin;
import cn.blockforge.generated.goetysupreme.mixin.MutantThrowableBlockSoundMixin;
import cn.blockforge.generated.goetysupreme.mixin.MutantZombieMixin;
import cn.blockforge.generated.goetysupreme.mixin.SerponLifespanJadeMixin;
import cn.blockforge.generated.goetysupreme.mixin.SerponOwnerJadeMixin;
import cn.blockforge.generated.goetysupreme.mixin.SomeIllagerServantsSpiderFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.SomeIllagerServantsSunkenNecromancerFocusMixin;
import cn.blockforge.generated.goetysupreme.mixin.ZombieResurrectionMixin;

public final class GoetySupremeMixinPlugin implements IMixinConfigPlugin {

    private static final String SERPON_LIFESPAN_JADE_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.SerponLifespanJadeMixin";
    private static final String SERPON_OWNER_JADE_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.SerponOwnerJadeMixin";

    private static final String CATACLYSM_DRAUGR_FOCUS_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrNecromancerFocusMixin";

    private static final String CATACLYSM_DRAUGR_NATIVE_CAP_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrNecromancerNativeCapMixin";

    private static final String CATACLYSM_DRAUGR_RING_WEAPON_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrRingWeaponMixin";

    private static final String CATACLYSM_CINDARIA_SPEAR_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.CataclysmCindariaSpearDamageMixin";

    private static final String CATACLYSM_DRAUGR_ARROW_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.CataclysmDraugrSoulArrowDamageMixin";

    private static final String CATACLYSM_PROWLER_MISSILE_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.CataclysmProwlerMissileDamageMixin";

    private static final String CATACLYSM_CINDARIA_GOAL =
            "com.Polarice3.goety_cataclysm.common.entities.ally.acropolis.CindariaServant$MagicAttackGoal";

    private static final String CATACLYSM_PROWLER_SERVANT =
            "com.Polarice3.goety_cataclysm.common.entities.ally.factory.ProwlerServant";

    private static final String CATACLYSM_PHANTOM_ARROW =
            "com.Polarice3.goety_cataclysm.common.entities.projectiles.PhantomArrow";

    private static final String CATACLYSM_DRAUGR =
            "com.Polarice3.goety_cataclysm.common.entities.neutral.AbstractDraugrNecromancer";

    private static final String CATACLYSM_DRAUGR_SERVANT =
            "com.Polarice3.goety_cataclysm.common.entities.ally.undead.draugr.DraugrServant";

    private static final Set<String> MUTANT_MIXINS = Set.of(
            "cn.blockforge.generated.goetysupreme.mixin.MutantZombieMixin",
            "cn.blockforge.generated.goetysupreme.mixin.MutantConversionWildMixin",
            "cn.blockforge.generated.goetysupreme.mixin.MutantChemicalXServantMixin",
            "cn.blockforge.generated.goetysupreme.mixin.MutantEndermanTeleSmashMixin",
            "cn.blockforge.generated.goetysupreme.mixin.MutantEndermanVoidHealMixin",
            "cn.blockforge.generated.goetysupreme.mixin.MutantThrowableBlockSoundMixin",
            "cn.blockforge.generated.goetysupreme.mixin.ZombieResurrectionMixin");

    private static final String MUTANT_MONSTERS_PROBE =
            "fuzs.mutantmonsters.world.entity.mutant.MutantZombie";

    private static final String JEROTES_SPEAR_DAMAGE_MIXIN =
            "cn.blockforge.generated.goetysupreme.mixin.JerotesSpearServantDamageMixin";

    private static final String JEROTES_SPEAR_ITEM =
            "com.jerotes.jerotes.item.Tool.ItemToolBaseSpearBase";

    private static final Set<String> OMINOUS_MIXINS = Set.of(
            "cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSpiderFocusMixin",
            "cn.blockforge.generated.goetysupreme.mixin.SomeIllagerServantsSpiderFocusMixin",
            "cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSunkenNecromancerFocusMixin",
            "cn.blockforge.generated.goetysupreme.mixin.SomeIllagerServantsSunkenNecromancerFocusMixin",
            "cn.blockforge.generated.goetysupreme.mixin.GoetyOminousSunkenNecromancerNativeCapMixin");

    private static final String[] OMINOUS_PROBES = {
            "com.qiuyue.goetyominous.common.entities.ally.neutral.AbstractSunkenNecromancer",
            "com.qiuyue.goetyominus.common.entities.ally.neutral.AbstractSunkenNecromancer",
            "com.qiuyue.someillagerservants.common.entities.ally.neutral.AbstractSunkenNecromancer"};

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (SERPON_LIFESPAN_JADE_MIXIN.equals(mixinClassName)
                || SERPON_OWNER_JADE_MIXIN.equals(mixinClassName)) {
            return jadePresent() && serponClassLoadable();
        }
        if (CATACLYSM_DRAUGR_FOCUS_MIXIN.equals(mixinClassName)
                || CATACLYSM_DRAUGR_NATIVE_CAP_MIXIN.equals(mixinClassName)) {
            return classPresent(CATACLYSM_DRAUGR);
        }
        if (CATACLYSM_DRAUGR_RING_WEAPON_MIXIN.equals(mixinClassName)) {
            return classPresent(CATACLYSM_DRAUGR_SERVANT);
        }
        if (CATACLYSM_CINDARIA_SPEAR_MIXIN.equals(mixinClassName)) {
            return classPresent(CATACLYSM_CINDARIA_GOAL);
        }
        if (CATACLYSM_DRAUGR_ARROW_MIXIN.equals(mixinClassName)) {
            return classPresent(CATACLYSM_PHANTOM_ARROW);
        }
        if (CATACLYSM_PROWLER_MISSILE_MIXIN.equals(mixinClassName)) {
            return classPresent(CATACLYSM_PROWLER_SERVANT);
        }
        if (MUTANT_MIXINS.contains(mixinClassName)) {
            return classPresent(MUTANT_MONSTERS_PROBE);
        }
        if (JEROTES_SPEAR_DAMAGE_MIXIN.equals(mixinClassName)) {
            return classPresent(JEROTES_SPEAR_ITEM);
        }
        if (OMINOUS_MIXINS.contains(mixinClassName)) {
            for (String probe : OMINOUS_PROBES) {
                if (classPresent(probe)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    private static boolean serponClassLoadable() {
        return classPresent("com.jerotes.jerotes.entity.Interface.FactionEntity")
                && classPresent("cn.blockforge.generated.goetysupreme.AbstractSerpon");
    }

    private static boolean jadePresent() {
        return classPresent("snownee.jade.api.IEntityComponentProvider");
    }

    private static boolean classPresent(String className) {
        String resource = className.replace('.', '/') + ".class";
        try (InputStream stream = openClassResource(resource)) {
            return stream != null;
        } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            return false;
        }
    }

    private static InputStream openClassResource(String resource) {
        try {
            InputStream stream = MixinService.getService().getResourceAsStream(resource);
            if (stream != null) {
                return stream;
            }
        } catch (Throwable ignored) {
        }
        ClassLoader loader = GoetySupremeMixinPlugin.class.getClassLoader();
        return loader == null ? null : loader.getResourceAsStream(resource);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName,
            IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName,
            IMixinInfo mixinInfo) {
    }
}
