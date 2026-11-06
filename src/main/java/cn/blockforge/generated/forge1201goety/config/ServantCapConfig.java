package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.config.SupremeServantConfigs;

public final class ServantCapConfig {

    public static ForgeConfigSpec.IntValue FURNACE_GOLEM;
    public static ForgeConfigSpec.IntValue MAGMA_GOLEM;
    public static ForgeConfigSpec.IntValue PRISON_GOLEM;
    public static ForgeConfigSpec.IntValue GIANT_SNOW_GOLEM;
    public static ForgeConfigSpec.IntValue WITHER_SERVANT;
    public static ForgeConfigSpec.IntValue MUTANT_ZOMBIE_SERVANT;
    public static ForgeConfigSpec.IntValue MUTANT_SKELETON_SERVANT;
    public static ForgeConfigSpec.IntValue MUTANT_SNOW_GOLEM_SERVANT;
    public static ForgeConfigSpec.IntValue MUTANT_ENDERMAN_SERVANT;

    public static ForgeConfigSpec.IntValue WITHER_WARLORD_SERVANT;

    public static ForgeConfigSpec.IntValue JEROTES_SEDIMENT_LORD;

    public static ForgeConfigSpec.IntValue JEROTES_CORROSIVER;

    public static ForgeConfigSpec.IntValue JEROTES_LIGHTNING_WORM;

    public static ForgeConfigSpec.IntValue JEROTES_FLAMOAR;

    public static ForgeConfigSpec.IntValue JEROTES_SEA_WAVE;

    public static ForgeConfigSpec.IntValue JEROTES_DROUGHT_WILDFIRE;

    public static ForgeConfigSpec.IntValue JEROTES_SECOND_ROUNDER_ZOMBIE;

    public static ForgeConfigSpec.IntValue JEROTES_SECOND_ROUNDER_SKELETON;

    public static ForgeConfigSpec.IntValue JEROTES_SECOND_ROUNDER_SPIDER;

    public static ForgeConfigSpec.IntValue JEROTES_ENDER_STOWAWAY;

    public static ForgeConfigSpec.IntValue JEROTES_SERPON_COLONIAL_SOLDIER;

    public static ForgeConfigSpec.IntValue JEROTES_SERPON_RIOTER;

    public static ForgeConfigSpec.IntValue JEROTES_SERPON_PIONEER;

    public static ForgeConfigSpec.IntValue JEROTES_SERPON_FIRE_APOSTLE;

    public static ForgeConfigSpec.IntValue JEROTES_SERPON_INTRUDER;

    public static ForgeConfigSpec.IntValue JEROTES_SERPON_FLAME_WIZARD;

    public static ForgeConfigSpec.IntValue JEROTES_WAR_PYTHON;

    public static ForgeConfigSpec.IntValue JEROTES_ROYAL_WAR_PYTHON;

    public static ForgeConfigSpec.IntValue JEROTES_SERPON_COMBAT_EXECUTER;

    public static ForgeConfigSpec.IntValue JEROTES_SECOND_ROUNDER_ANIMATED_ARMOR;

    public static ForgeConfigSpec.IntValue JEROTES_FLYING_FIREBALL;

    public static ForgeConfigSpec.IntValue JEROTES_IRON_TEETH;

    public static ForgeConfigSpec.IntValue JEROTES_PRIMORDIER;

    public static ForgeConfigSpec.IntValue JEROTES_FROST_YETI;

    public static ForgeConfigSpec.IntValue SCORCHBACK;

    public static ForgeConfigSpec.IntValue WILD_MAGE_SERVANT;

    public static ForgeConfigSpec.IntValue REAPER_NECROMANCER_SERVANT;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("各类仆从同一主人可同时存在的数量上限（含模具搭建、祭坛仪式与聚晶召唤等全部来源；"
                        + "有聚晶的仆从以上限以 focus.toml 里聚晶的 summon_limit 为准）。修改后重启游戏生效。")
                .push("servant_caps");
        FURNACE_GOLEM = builder.comment("熔炉傀儡仆从上限，默认 4。")
                .defineInRange("furnace_golem", 4, 1, Integer.MAX_VALUE);
        MAGMA_GOLEM = builder.comment("岩浆傀儡仆从上限，默认 4。")
                .defineInRange("magma_golem", 4, 1, Integer.MAX_VALUE);
        PRISON_GOLEM = builder.comment("监狱傀儡仆从上限，默认 2。")
                .defineInRange("prison_golem", 2, 1, Integer.MAX_VALUE);
        GIANT_SNOW_GOLEM = builder.comment("巨型雪傀儡仆从上限，默认 2。")
                .defineInRange("giant_snow_golem", 2, 1, Integer.MAX_VALUE);
        WITHER_SERVANT = builder.comment("凋灵仆从上限，默认 1。")
                .defineInRange("wither_servant", 1, 1, Integer.MAX_VALUE);
        MUTANT_ZOMBIE_SERVANT = builder.comment("突变僵尸仆从上限，默认 2。")
                .defineInRange("mutant_zombie_servant", 2, 1, Integer.MAX_VALUE);
        MUTANT_SKELETON_SERVANT = builder.comment("突变骷髅仆从上限，默认 2。")
                .defineInRange("mutant_skeleton_servant", 2, 1, Integer.MAX_VALUE);
        MUTANT_SNOW_GOLEM_SERVANT = builder.comment("突变雪傀儡仆从上限，默认 2。")
                .defineInRange("mutant_snow_golem_servant", 2, 1, Integer.MAX_VALUE);
        MUTANT_ENDERMAN_SERVANT = builder.comment("突变末影人仆从上限，默认 2。")
                .defineInRange("mutant_enderman_servant", 2, 1, Integer.MAX_VALUE);
        WILD_MAGE_SERVANT = builder.comment("荒野法师仆从上限，默认 2（至多两个）。")
                .defineInRange("wild_mage_servant", 2, 1, Integer.MAX_VALUE);
        SCORCHBACK = builder.comment("炽背兽仆从上限，默认 2（玩家方生物，走驯化仪式转化嗅探兽或孵蛋）。")
                .defineInRange("scorchback", 2, 1, Integer.MAX_VALUE);
        REAPER_NECROMANCER_SERVANT = builder.comment("收割者死灵法师仆从上限，默认 2（至多两个；寿命 2 分钟）。")
                .defineInRange("reaper_necromancer_servant", 2, 1, Integer.MAX_VALUE);
        WITHER_WARLORD_SERVANT = builder.comment("凋灵督军·黑大帅仆从上限，默认 1（首领级仆从，走炼狱凭证的专家下界召唤仪式）。")
                .defineInRange("wither_warlord_servant", 1, 1, Integer.MAX_VALUE);
        builder.pop();
    }

    public static void linkJerotesCaps() {
        JEROTES_SEDIMENT_LORD = JerotesVillageServantConfig.CAP_SEDIMENT_LORD;
        JEROTES_CORROSIVER = JerotesVillageServantConfig.CAP_CORROSIVER;
        JEROTES_LIGHTNING_WORM = JerotesVillageServantConfig.CAP_LIGHTNING_WORM;
        JEROTES_FLAMOAR = JerotesVillageServantConfig.CAP_FLAMOAR;
        JEROTES_SEA_WAVE = JerotesVillageServantConfig.CAP_SEA_WAVE;
        JEROTES_DROUGHT_WILDFIRE = JerotesVillageServantConfig.CAP_DROUGHT_WILDFIRE;
        JEROTES_SECOND_ROUNDER_ZOMBIE = JerotesVillageServantConfig.CAP_SECOND_ROUNDER_ZOMBIE;
        JEROTES_SECOND_ROUNDER_SKELETON = JerotesVillageServantConfig.CAP_SECOND_ROUNDER_SKELETON;
        JEROTES_SECOND_ROUNDER_SPIDER = JerotesVillageServantConfig.CAP_SECOND_ROUNDER_SPIDER;
        JEROTES_ENDER_STOWAWAY = JerotesVillageServantConfig.CAP_ENDER_STOWAWAY;
        JEROTES_SERPON_COLONIAL_SOLDIER = JerotesVillageServantConfig.CAP_SERPON_COLONIAL_SOLDIER;
        JEROTES_SERPON_RIOTER = JerotesVillageServantConfig.CAP_SERPON_RIOTER;
        JEROTES_SERPON_PIONEER = JerotesVillageServantConfig.CAP_SERPON_PIONEER;
        JEROTES_SERPON_FIRE_APOSTLE = JerotesVillageServantConfig.CAP_SERPON_FIRE_APOSTLE;
        JEROTES_SERPON_INTRUDER = JerotesVillageServantConfig.CAP_SERPON_INTRUDER;
        JEROTES_SERPON_FLAME_WIZARD = JerotesVillageServantConfig.CAP_SERPON_FLAME_WIZARD;
        JEROTES_WAR_PYTHON = JerotesVillageServantConfig.CAP_WAR_PYTHON;
        JEROTES_ROYAL_WAR_PYTHON = JerotesVillageServantConfig.CAP_ROYAL_WAR_PYTHON;
        JEROTES_SERPON_COMBAT_EXECUTER = JerotesVillageServantConfig.CAP_SERPON_COMBAT_EXECUTER;
        JEROTES_SECOND_ROUNDER_ANIMATED_ARMOR = JerotesVillageServantConfig.CAP_SECOND_ROUNDER_ANIMATED_ARMOR;
        JEROTES_FLYING_FIREBALL = JerotesVillageServantConfig.CAP_FLYING_FIREBALL;
        JEROTES_IRON_TEETH = JerotesVillageServantConfig.CAP_IRON_TEETH;
        JEROTES_PRIMORDIER = JerotesVillageServantConfig.CAP_PRIMORDIER;
        JEROTES_FROST_YETI = JerotesVillageServantConfig.CAP_FROST_YETI;
    }

    private ServantCapConfig() {
    }
}
