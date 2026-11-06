package cn.blockforge.generated.goetysupreme.compat;

import java.io.InputStream;
import net.minecraftforge.fml.ModList;
import cn.blockforge.generated.goetysupreme.ai.JerotesAllyHelpGoal;
import cn.blockforge.generated.goetysupreme.ai.SerponFlyingStrollGoal;
import cn.blockforge.generated.goetysupreme.entity.AbstractJvillageInventoryServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSedimentServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompatManager;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;
import cn.blockforge.generated.goetysupreme.compat.JvillageServantShieldBreak;
import cn.blockforge.generated.goetysupreme.compat.JvillageSpearGoals;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrownJavelin;
import cn.blockforge.generated.goetysupreme.compat.JvillageThrowShoot;
import cn.blockforge.generated.goetysupreme.entity.SerponCombatExecuter;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.mixin.GoetySupremeMixinPlugin;

public final class JerotesVillageCompat {

    public static final String JEROTES_VILLAGE_MOD_ID = "jerotesvillage";

    private static final String JVILLAGE_SERVANT_PROBE =
            "cn.blockforge.generated.goetysupreme.AbstractSedimentServant";

    private static final String JEROTES_INVENTORY_INTERFACE =
            "com.jerotes.jerotes.entity.Interface.InventoryEntity";
    private static final String JEROTES_MAIN_CLASS = "com.jerotes.jerotes.util.Main";

    private static final String JEROTES_SPEAR_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesSpearUseGoal";
    private static final String JEROTES_PIKE_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesPikeUseGoal";
    private static final String JEROTES_SPEAR_ITEM_CLASS =
            "com.jerotes.jerotes.item.Tool.ItemToolBaseSpearBase";

    private static final String JEROTES_RANGED_BOW_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesRangedBowAttackGoal";
    private static final String JEROTES_RANGED_JAVELIN_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesRangedJavelinAttackGoal";
    private static final String JEROTES_RANGED_THROW_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesRangedThrowAttackGoal";
    private static final String JEROTES_HELP_SAME_FACTION_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesHelpSameFactionGoal";
    private static final String JEROTES_HELP_ALLIES_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesHelpAlliesGoal";

    private static final String JEROTES_MAIN_SPELL_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesMainSpellAttackGoal";
    private static final String JEROTES_ADD_SPELL_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesAddSpellAttackGoal";
    private static final String JEROTES_WIZARD_INTERFACE =
            "com.jerotes.jerotes.entity.Interface.WizardEntity";

    private static final String JEROTES_FLYING_STROLL_GOAL_CLASS =
            "com.jerotes.jerotes.goal.JerotesFlyingRandomStrollGoal";

    private static final String JEROTES_ITEM_THROW_USE_CLASS =
            "com.jerotes.jerotes.item.Interface.JerotesItemThrowUse";
    private static final String JEROTES_ITEM_THROWN_JAVELIN_USE_CLASS =
            "com.jerotes.jerotes.item.Interface.JerotesItemThrownJavelinUse";

    private static Boolean jerotesVillageLoaded = null;

    private static Boolean jvillageServantLoadable = null;

    private static Boolean spearGoalLoadable = null;

    private static Boolean rangedGoalLoadable = null;

    private static Boolean helpGoalLoadable = null;

    private static Boolean flyingStrollLoadable = null;

    private static Boolean spellGoalLoadable = null;

    private static Boolean throwUseLoadable = null;

    private static Boolean thrownJavelinLoadable = null;

    private JerotesVillageCompat() {
    }

    public static boolean isJerotesVillageLoaded() {
        if (jerotesVillageLoaded == null) {
            jerotesVillageLoaded = ModList.get().isLoaded(JEROTES_VILLAGE_MOD_ID);
        }
        return jerotesVillageLoaded;
    }

    public static boolean isJvillageServantLoadable() {
        if (jvillageServantLoadable == null) {
            jvillageServantLoadable = classPresent(JEROTES_INVENTORY_INTERFACE)
                    && classPresent(JEROTES_MAIN_CLASS)
                    && probeClass(JVILLAGE_SERVANT_PROBE);
        }
        return jvillageServantLoadable;
    }

    public static boolean isJerotesSpearGoalLoadable() {
        if (spearGoalLoadable == null) {
            spearGoalLoadable = classPresent(JEROTES_SPEAR_GOAL_CLASS)
                    && classPresent(JEROTES_PIKE_GOAL_CLASS)
                    && classPresent(JEROTES_SPEAR_ITEM_CLASS)
                    && classPresent(JEROTES_INVENTORY_INTERFACE)
                    && probeClass(JEROTES_SPEAR_GOAL_CLASS)
                    && probeClass(JEROTES_PIKE_GOAL_CLASS);
        }
        return spearGoalLoadable;
    }

    public static boolean isJerotesRangedGoalLoadable() {
        if (rangedGoalLoadable == null) {
            rangedGoalLoadable = classPresent(JEROTES_RANGED_BOW_GOAL_CLASS)
                    && classPresent(JEROTES_RANGED_JAVELIN_GOAL_CLASS)
                    && classPresent(JEROTES_RANGED_THROW_GOAL_CLASS)
                    && classPresent(JEROTES_INVENTORY_INTERFACE)
                    && probeClass(JEROTES_RANGED_BOW_GOAL_CLASS)
                    && probeClass(JEROTES_RANGED_JAVELIN_GOAL_CLASS)
                    && probeClass(JEROTES_RANGED_THROW_GOAL_CLASS);
        }
        return rangedGoalLoadable;
    }

    public static boolean isJerotesHelpGoalLoadable() {
        if (helpGoalLoadable == null) {
            helpGoalLoadable = classPresent(JEROTES_HELP_SAME_FACTION_GOAL_CLASS)
                    && classPresent(JEROTES_HELP_ALLIES_GOAL_CLASS)
                    && classPresent(JEROTES_INVENTORY_INTERFACE)
                    && probeClass(JEROTES_HELP_SAME_FACTION_GOAL_CLASS)
                    && probeClass(JEROTES_HELP_ALLIES_GOAL_CLASS);
        }
        return helpGoalLoadable;
    }

    public static boolean isJerotesSpellGoalLoadable() {
        if (spellGoalLoadable == null) {
            spellGoalLoadable = classPresent(JEROTES_MAIN_SPELL_GOAL_CLASS)
                    && classPresent(JEROTES_ADD_SPELL_GOAL_CLASS)
                    && classPresent(JEROTES_WIZARD_INTERFACE)
                    && classPresent(JEROTES_INVENTORY_INTERFACE)
                    && probeClass(JEROTES_MAIN_SPELL_GOAL_CLASS)
                    && probeClass(JEROTES_ADD_SPELL_GOAL_CLASS)
                    && probeClass(JEROTES_WIZARD_INTERFACE);
        }
        return spellGoalLoadable;
    }

    public static boolean isJerotesFlyingStrollLoadable() {
        if (flyingStrollLoadable == null) {
            flyingStrollLoadable = classPresent(JEROTES_FLYING_STROLL_GOAL_CLASS)
                    && probeClass(JEROTES_FLYING_STROLL_GOAL_CLASS);
        }
        return flyingStrollLoadable;
    }

    public static boolean isJerotesThrowUseLoadable() {
        if (throwUseLoadable == null) {
            throwUseLoadable = classPresent(JEROTES_ITEM_THROW_USE_CLASS)
                    && probeClass(JEROTES_ITEM_THROW_USE_CLASS);
        }
        return throwUseLoadable;
    }

    public static boolean isJerotesThrownJavelinLoadable() {
        if (thrownJavelinLoadable == null) {
            thrownJavelinLoadable = classPresent(JEROTES_ITEM_THROWN_JAVELIN_USE_CLASS)
                    && probeClass(JEROTES_ITEM_THROWN_JAVELIN_USE_CLASS);
        }
        return thrownJavelinLoadable;
    }

    private static boolean probeClass(String className) {
        try {
            Class.forName(className, false, JerotesVillageCompat.class.getClassLoader());
            return true;
        } catch (Throwable absent) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", absent.toString());
            return false;
        }
    }

    private static boolean classPresent(String className) {
        String resource = className.replace('.', '/') + ".class";
        if (resourcePresent(Thread.currentThread().getContextClassLoader(), resource)) {
            return true;
        }
        return resourcePresent(JerotesVillageCompat.class.getClassLoader(), resource);
    }

    private static boolean resourcePresent(ClassLoader loader, String resource) {
        if (loader == null) {
            return false;
        }
        try (InputStream stream = loader.getResourceAsStream(resource)) {
            return stream != null;
        } catch (Throwable absent) {
            return false;
        }
    }
}
