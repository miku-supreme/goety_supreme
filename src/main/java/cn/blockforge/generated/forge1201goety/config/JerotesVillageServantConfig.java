package cn.blockforge.generated.goetysupreme.config;

import java.util.function.Consumer;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeConfigSpec;

public final class JerotesVillageServantConfig {

    public static Section SEDIMENT_GUARD;

    public static Section SEDIMENT_SOUL_STABILIZER;

    public static Section HEAVY_ARMORED_SEDIMENT_GUARD;

    public static Section SEDIMENT_LORD;

    public static Section CORROSIVER;
    public static Section LIGHTNING_WORM;

    public static Section FLAMOAR;

    public static Section PRIMORDIER;

    public static Section FROST_YETI;

    public static Section BULGE;

    public static Section SEA_WAVE;

    public static DroughtWildfireSection DROUGHT_WILDFIRE;

    public static Section DROUGHT_BLAZE;

    public static Section ENDER_STOWAWAY;

    public static Section SERPON_COLONIAL_SOLDIER;

    public static Section SERPON_RIOTER;

    public static Section SERPON_PIONEER;

    public static Section SERPON_FIRE_APOSTLE;

    public static Section SERPON_INTRUDER;

    public static Section SERPON_FLAME_WIZARD;

    public static Section WAR_PYTHON;

    public static Section ROYAL_WAR_PYTHON;

    public static Section SERPON_COMBAT_EXECUTER;

    public static ForgeConfigSpec.BooleanValue SERPON_WEAKNESS_IN_NON_HOT_BIOMES;

    public static ForgeConfigSpec.DoubleValue CORROSIVER_SPECIAL_KNOCKBACK_CAP;

    public static ForgeConfigSpec.IntValue CORROSIVER_TOUGH_INVULN_TICKS;

    public static ForgeConfigSpec.BooleanValue CORROSIVER_CAIRN_JAR_ENABLED;

    public static ForgeConfigSpec.DoubleValue CORROSIVER_CAIRN_JAR_HEALTH;

    public static ForgeConfigSpec.DoubleValue CORROSIVER_CAIRN_JAR_COOLDOWN_SCALE;

    public static ForgeConfigSpec.BooleanValue CORROSIVER_FROSTBORN_ENABLED;

    public static ForgeConfigSpec.DoubleValue CORROSIVER_FROSTBORN_HEALTH;

    public static ForgeConfigSpec.DoubleValue CORROSIVER_FROSTBORN_GOLEM_CHANCE;

    public static ForgeConfigSpec.IntValue CORROSIVER_FROSTBORN_GOLEM_CAP;

    public static ForgeConfigSpec.DoubleValue CORROSIVER_FROSTBORN_DAMAGE_SCALE;

    public static ForgeConfigSpec.BooleanValue CORROSIVER_RIDING_ENABLED;

    public static ForgeConfigSpec.DoubleValue CORROSIVER_RIDING_SPEED;

    public static GiantSection SECOND_ROUNDER_ZOMBIE;

    public static GiantSection SECOND_ROUNDER_SKELETON;

    public static Section SECOND_ROUNDER_SPIDER;

    public static Section SECOND_ROUNDER_ANIMATED_ARMOR;

    public static Section FLYING_FIREBALL;

    public static Section IRON_TEETH;

    public static Section BITTER_COLD_WIND;

    public static ForgeConfigSpec.IntValue CAP_SEDIMENT_LORD;    public static ForgeConfigSpec.IntValue CAP_SEA_WAVE;
    public static ForgeConfigSpec.IntValue CAP_CORROSIVER;
    public static ForgeConfigSpec.IntValue CAP_LIGHTNING_WORM;
    public static ForgeConfigSpec.IntValue CAP_FLAMOAR;
    public static ForgeConfigSpec.IntValue CAP_PRIMORDIER;
    public static ForgeConfigSpec.IntValue CAP_FROST_YETI;
    public static ForgeConfigSpec.IntValue CAP_DROUGHT_WILDFIRE;
    public static ForgeConfigSpec.IntValue CAP_SECOND_ROUNDER_ZOMBIE;
    public static ForgeConfigSpec.IntValue CAP_SECOND_ROUNDER_SKELETON;
    public static ForgeConfigSpec.IntValue CAP_SECOND_ROUNDER_SPIDER;
    public static ForgeConfigSpec.IntValue CAP_ENDER_STOWAWAY;
    public static ForgeConfigSpec.IntValue CAP_SERPON_COLONIAL_SOLDIER;
    public static ForgeConfigSpec.IntValue CAP_SERPON_RIOTER;
    public static ForgeConfigSpec.IntValue CAP_SERPON_PIONEER;
    public static ForgeConfigSpec.IntValue CAP_SERPON_FIRE_APOSTLE;
    public static ForgeConfigSpec.IntValue CAP_SERPON_INTRUDER;
    public static ForgeConfigSpec.IntValue CAP_SERPON_FLAME_WIZARD;
    public static ForgeConfigSpec.IntValue CAP_WAR_PYTHON;
    public static ForgeConfigSpec.IntValue CAP_ROYAL_WAR_PYTHON;
    public static ForgeConfigSpec.IntValue CAP_SERPON_COMBAT_EXECUTER;
    public static ForgeConfigSpec.IntValue CAP_SECOND_ROUNDER_ANIMATED_ARMOR;
    public static ForgeConfigSpec.IntValue CAP_FLYING_FIREBALL;
    public static ForgeConfigSpec.IntValue CAP_IRON_TEETH;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("J 村（jerotesvillage）联动仆从的统一大节：下面每个仆从各占一个独立小节，"
                        + "没有聚晶来源的几个另在 servant_caps 里占一项；修改后重启游戏生效。")
                .push("jerotes_village");

        SEDIMENT_GUARD = Section.build(builder, "sediment_guard_servant",
                "沉降戍卫（J 村沉降守卫）仆从的属性；默认值照抄 J 村本体。",
                0.27D, 30.0D, 0.0D, 5.0D, 0.0D, 0.0D);
        SEDIMENT_SOUL_STABILIZER = Section.build(builder, "sediment_soul_stabilizer_servant",
                "沉降魂守（J 村沉降定灵人）仆从的属性；默认值照抄 J 村本体。",
                0.27D, 35.0D, 0.0D, 5.0D, 0.0D, 0.0D);
        HEAVY_ARMORED_SEDIMENT_GUARD = Section.build(builder, "heavy_armored_sediment_guard_servant",
                "沉降重卫（J 村重装沉降守卫）仆从的属性；默认值照抄 J 村本体。",
                0.24D, 40.0D, 5.0D, 5.0D, 0.5D, 0.0D);
        SEDIMENT_LORD = Section.build(builder, "sediment_lord_servant",
                "沉降领主（J 村沉降领主）仆从的属性；默认值照抄 J 村本体。"
                        + "它的沉降号令召来的守卫 / 重装数量上限另有口径，不在这里。",
                0.2D, 170.0D, 3.0D, 6.0D, 0.5D, 0.0D);
        CORROSIVER = Section.build(builder, "corrosiver_servant",
                "蚀龙兽（J 村群系 Boss）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.32 / 生命 225 / 护甲 5 / 攻击 12 / 击退抗性 1.0，攻击击退 0.6 不在本表）。",
                0.32D, 225.0D, 5.0D, 12.0D, 1.0D, 0.0D);
        LIGHTNING_WORM = Section.build(builder, "lightning_worm_servant",
                "闪电蠕虫（J 村飞行精英怪）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 135 / 护甲 11 / 攻击 16 / 击退抗性 0.3，飞行速度 1.0 / 攻击击退 0.4 不在本表）。",
                0.35D, 135.0D, 11.0D, 16.0D, 0.3D, 0.0D);
        FLAMOAR = Section.build(builder, "flamoar_servant",
                "火灵（J 村精英飞行生物）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.33 / 生命 80 / 护甲 18 / 攻击 5 / 护甲韧性 4 / 击退抗性 1.0，"
                        + "飞行速度 0.35、攻击击退 0.4 不在本表）。",
                0.33D, 80.0D, 18.0D, 5.0D, 1.0D, 4.0D);

        PRIMORDIER = Section.build(builder, "primordier_servant",
                "元凶雪人（J 村 Boss 级精英）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 320 / 护甲 8 / 攻击 25 / 击退抗性 1.0；"
                        + "J 村本体没有护甲韧性，攻击击退 2.0 不在本表）。",
                0.35D, 320.0D, 8.0D, 25.0D, 1.0D, 0.0D);
        FROST_YETI = Section.build(builder, "frost_yeti_servant",
                "寒霜雪怪（J 村精英雪怪）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 128 / 护甲 12 / 攻击 14 / 击退抗性 0.8；"
                        + "J 村本体没有护甲韧性，攻击击退 1.2 不在本表）。",
                0.35D, 128.0D, 12.0D, 14.0D, 0.8D, 0.0D);

        BULGE = Section.build(builder, "bulge_servant",
                "苍澜（J 村波涛人）仆从的属性；默认值照抄 J 村本体。",
                0.3D, 20.0D, 0.0D, 2.0D, 0.0D, 0.0D);
        SEA_WAVE = Section.build(builder, "sea_wave_servant",
                "骇浪（J 村海波）仆从的属性；默认值照抄 J 村本体。",
                0.3D, 80.0D, 8.0D, 8.0D, 0.6D, 0.0D);

        DROUGHT_WILDFIRE = DroughtWildfireSection.build(builder);
        DROUGHT_BLAZE = Section.build(builder, "drought_blaze_servant",
                "焦炎（J 村旱火烈焰人）仆从的属性；默认值照抄 J 村本体。",
                0.25D, 25.0D, 0.0D, 6.0D, 0.0D, 0.0D);

        ENDER_STOWAWAY = Section.build(builder, "ender_stowaway_servant",
                "末影渡客（J 村末影渡客）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.32 / 生命 45 / 攻击 7，跟随范围不开放）。",
                0.32D, 45.0D, 0.0D, 7.0D, 0.0D, 0.0D);
        SERPON_COLONIAL_SOLDIER = Section.build(builder, "serpon_colonial_soldier",
                "蛇龙殖民兵（J 村蛇龙殖民兵）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 40 / 护甲 0 / 攻击 5，攻击击退 0.1 不在本表）。",
                0.35D, 40.0D, 0.0D, 5.0D, 0.0D, 0.0D);
        SERPON_RIOTER = Section.build(builder, "serpon_rioter",
                "蛇龙暴动者（J 村蛇龙暴动者）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 55 / 护甲 15 / 攻击 8，攻击击退 0.1 不在本表）。",
                0.35D, 55.0D, 15.0D, 8.0D, 0.0D, 0.0D);
        SERPON_PIONEER = Section.build(builder, "serpon_pioneer",
                "蛇龙先锋（J 村蛇龙先锋）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.37 / 生命 40 / 护甲 0 / 攻击 5，攻击击退 0.1 不在本表）。",
                0.37D, 40.0D, 0.0D, 5.0D, 0.0D, 0.0D);
        SERPON_FIRE_APOSTLE = Section.build(builder, "serpon_fire_apostle",
                "蛇龙火焰使徒（J 村蛇龙火焰使徒）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 70 / 护甲 5 / 攻击 8 / 击退抗性 1.0，攻击击退 0.1 不在本表）。",
                0.35D, 70.0D, 5.0D, 8.0D, 1.0D, 0.0D);
        SERPON_INTRUDER = Section.build(builder, "serpon_intruder",
                "蛇龙突进者（J 村蛇龙突进者）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.37 / 生命 55 / 护甲 2 / 攻击 7，攻击击退 0.1 不在本表）。",
                0.37D, 55.0D, 2.0D, 7.0D, 0.0D, 0.0D);
        SERPON_FLAME_WIZARD = Section.build(builder, "serpon_flame_wizard",
                "蛇龙火焰巫师（J 村蛇龙火焰巫师）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.4 / 生命 40 / 护甲 0 / 攻击 5，攻击击退 0.1 不在本表）。",
                0.4D, 40.0D, 0.0D, 5.0D, 0.0D, 0.0D);
        WAR_PYTHON = Section.build(builder, "war_python",
                "战蟒（J 村战蟒）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 45 / 护甲 2 / 攻击 8 / 击退抗性 0.3，飞行速度 1.0 不在本表）。",
                0.35D, 45.0D, 2.0D, 8.0D, 0.3D, 0.0D);
        ROYAL_WAR_PYTHON = Section.build(builder, "royal_war_python",
                "皇家战蟒（J 村皇家战蟒）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.35 / 生命 65 / 护甲 12 / 攻击 12 / 击退抗性 0.75，飞行速度 1.0 不在本表）。",
                0.35D, 65.0D, 12.0D, 12.0D, 0.75D, 0.0D);
        SERPON_COMBAT_EXECUTER = Section.build(builder, "serpon_combat_executer",
                "蛇龙执战者（J 村蛇龙执战者，Boss 级）本体属性；默认值照抄 J 村本体"
                        + "（速度 0.4 / 生命 270 / 护甲 5 / 攻击 8 / 击退抗性 0.8，攻击击退 0.5 不在本表）。"
                        + "注册名不带仆从，但走的是同一套仆从属性口径。",
                0.4D, 270.0D, 5.0D, 8.0D, 0.8D, 0.0D);

        SECOND_ROUNDER_ZOMBIE = GiantSection.build(builder, "second_rounder_zombie_servant",
                "二轮僵尸仆从的属性；默认值照抄 J 村本体。巨人形态按下面两项倍率放大生命与攻击。",
                0.26D, 35.0D, 2.0D, 3.0D, 0.0D, 0.0D, 2.0D, 2.0D);
        SECOND_ROUNDER_SKELETON = GiantSection.build(builder, "second_rounder_skeleton_servant",
                "二轮骷髅仆从的属性；默认值照抄 J 村本体（速度 0.3 / 生命 30 / 护甲 0 / 攻击 4）。"
                        + "巨人形态按下面两项倍率放大生命与攻击。",
                0.3D, 30.0D, 0.0D, 4.0D, 0.0D, 0.0D, 2.0D, 2.0D);
        SECOND_ROUNDER_SPIDER = Section.build(builder, "second_rounder_spider_servant",
                "二轮蜘蛛仆从的属性；默认值照抄 J 村本体。",
                0.34D, 28.0D, 2.0D, 4.0D, 0.0D, 0.0D);
        SECOND_ROUNDER_ANIMATED_ARMOR = Section.build(builder, "second_rounder_animated_armor_servant",
                "活化二轮盔甲（J 村活化二轮盔甲）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.28 / 生命 54 / 护甲 0 / 攻击 5 / 击退抗性 0.8，攻击击退 0.2 不在本表）。",
                0.28D, 54.0D, 0.0D, 5.0D, 0.8D, 0.0D);

        FLYING_FIREBALL = Section.build(builder, "flying_fireball_servant",
                "飞行火团（J 村飞行火团）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.15 / 生命 12 / 护甲 0 / 攻击 5，飞行速度 1.25 不在本表）。",
                0.15D, 12.0D, 0.0D, 5.0D, 0.0D, 0.0D);
        IRON_TEETH = Section.build(builder, "iron_teeth_servant",
                "铁齿（J 村铁齿）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.38 / 生命 16 / 护甲 15 / 攻击 18 / 击退抗性 0.6，攻击击退 0.1 不在本表）。",
                0.38D, 16.0D, 15.0D, 18.0D, 0.6D, 0.0D);

        BITTER_COLD_WIND = Section.build(builder, "bitter_cold_wind_servant",
                "苦寒寒风（J 村苦寒寒风）仆从的属性；默认值照抄 J 村本体"
                        + "（速度 0.38 / 生命 26 / 护甲 3.6 / 攻击 4；无击退抗性）。",
                0.38D, 26.0D, 3.6D, 4.0D, 0.0D, 0.0D);

        SERPON_WEAKNESS_IN_NON_HOT_BIOMES = builder
                .comment("蛇龙殖民兵是否保留 J 村的「非炎热群系虚弱」：默认 true，照 J 村 "
                        + "SerponWeaknessInNonHotBiomes——不在 #minecraft:snow_golem_melts 群系里"
                        + "（且没有 Prepare、不在水中、64 格内没有蛇龙战斗执行者）时持续挂 1 级虚弱。"
                        + "嫌仆从被削可以关掉。")
                .define("serpon_weakness_in_non_hot_biomes", true);

        CORROSIVER_SPECIAL_KNOCKBACK_CAP = builder
                .comment("蚀龙兽近战特殊击退 / 风形态接触光环的位移封顶（格），默认 2.0。"
                        + "J 村原版把「Boss 到目标」的原始位移直接乘以系数当推力（不归一化），"
                        + "目标离得越远飞得越夸张——风形态攻击框半宽约 4.2 格，"
                        + "水平初速能到 5～6 格/刻。这里把每轴位移夹在 ±本值内："
                        + "默认 2.0 约等于贴脸距离，远距离不再放大；"
                        + "想更接近 J 村原版就往大调（比如 8），想更温和就往小调。")
                .defineInRange("corrosiver_special_knockback_cap", 2.0D, 0.0D, Double.MAX_VALUE);

        CORROSIVER_TOUGH_INVULN_TICKS = builder
                .comment("苦寒强韧：蚀龙兽每次受击成功结算后的无敌帧（刻），默认 20（1 秒）。"
                        + "固定时长、与难度无关，替代 J 村原本的"
                        + "「40 刻吸收窗口 + 3 倍伤害破韧」；穿透无敌帧的伤害不吃这一帧，设 0 关闭。")
                .defineInRange("corrosiver_tough_invuln_ticks", 20, 0, 200);
        CORROSIVER_CAIRN_JAR_ENABLED = builder
                .comment("允许给蚀龙兽流髑死法的灵魂罐（主人右手持罐右击喂食，一次性消耗）。")
                .define("corrosiver_cairn_jar_enabled", true);
        CORROSIVER_CAIRN_JAR_HEALTH = builder
                .comment("流髑灵魂罐给蚀龙兽增加的最大生命值，默认 50。")
                .defineInRange("corrosiver_cairn_jar_health", 50.0D, 0.0D, Double.MAX_VALUE);
        CORROSIVER_CAIRN_JAR_COOLDOWN_SCALE = builder
                .comment("流髑灵魂罐后蚀龙兽五条法术线（吐息 / 冰锥 / 雪云 / 唤风 / 瞬移）冷却的统一倍率，"
                        + "默认 0.75（即全部降低四分之一）。")
                .defineInRange("corrosiver_cairn_jar_cooldown_scale", 0.75D, 0.0D, 1.0D);
        CORROSIVER_FROSTBORN_ENABLED = builder
                .comment("允许给蚀龙兽霜裔聚晶（goety:frostborn_focus，也吃本模组同款寒裔聚晶；"
                        + "主人右手持聚晶右击喂食，一次性消耗）。")
                .define("corrosiver_frostborn_enabled", true);
        CORROSIVER_FROSTBORN_HEALTH = builder
                .comment("霜裔聚晶给蚀龙兽增加的最大生命值，默认 25。")
                .defineInRange("corrosiver_frostborn_health", 25.0D, 0.0D, Double.MAX_VALUE);
        CORROSIVER_FROSTBORN_GOLEM_CHANCE = builder
                .comment("霜裔聚晶后，苦寒唤风召出寒风时附召一只冰傀儡仆从的概率，默认 0.5（50%）。")
                .defineInRange("corrosiver_frostborn_golem_chance", 0.5D, 0.0D, 1.0D);
        CORROSIVER_FROSTBORN_GOLEM_CAP = builder
                .comment("霜裔聚晶后蚀龙兽同时持有的冰傀儡仆从上限，默认 2（至多两只）。")
                .defineInRange("corrosiver_frostborn_golem_cap", 2, 0, Integer.MAX_VALUE);
        CORROSIVER_FROSTBORN_DAMAGE_SCALE = builder
                .comment("霜裔聚晶后所有伤害性招式（近战、吐息、锥形吐息、腐蚀冰锥）的伤害倍率，"
                        + "默认 1.2（即提高 20%）。")
                .defineInRange("corrosiver_frostborn_damage_scale", 1.2D, 0.0D, Double.MAX_VALUE);
        CORROSIVER_RIDING_ENABLED = builder
                .comment("主人穿齐 Goety 冰霜长袍（CuriosFinder.hasFrostRobes）时，能否空手右击骑乘蚀龙兽。"
                        + "蚀龙兽是地面坐骑（不是飞行生物）：WASD 机动、空格起跳、"
                        + "左 Shift 脱离（骑乘期间的「潜行下马」已禁用）。")
                .define("corrosiver_riding_enabled", true);
        CORROSIVER_RIDING_SPEED = builder
                .comment("骑乘蚀龙兽时的地面奔跑速度（格/刻），默认 0.55（与凋灵仆从同档）。")
                .defineInRange("corrosiver_riding_speed", 0.55D, 0.1D, Double.MAX_VALUE);

        builder.comment("J 村联动里没有聚晶来源的仆从的「同一主人同时在场」上限；"
                        + "有聚晶来源的（沉降三仆从、苍澜、焦炎、二轮僵尸 / 骷髅）以上限以 focus.toml 为准。")
                .push("servant_caps");
        CAP_SEDIMENT_LORD = builder.comment("沉降领主上限，默认 2（至多两个）。")
                .defineInRange("sediment_lord_servant", 2, 1, Integer.MAX_VALUE);
        CAP_CORROSIVER = builder.comment("蚀龙兽上限，默认 1（J 村群系 Boss，同一主人只应有一只）。")
                .defineInRange("corrosiver_servant", 1, 1, Integer.MAX_VALUE);
        CAP_LIGHTNING_WORM = builder.comment("闪电蠕虫上限，默认 1（J 村飞行精英怪）。")
                .defineInRange("lightning_worm_servant", 1, 1, Integer.MAX_VALUE);
        CAP_FLAMOAR = builder.comment("火灵上限，默认 2（J 村精英飞行生物；没有聚晶来源）。")
                .defineInRange("flamoar_servant", 2, 1, Integer.MAX_VALUE);
        CAP_PRIMORDIER = builder.comment("元凶雪人上限，默认 2（至多两个；没有聚晶来源，走冰霜召唤仪式）。")
                .defineInRange("primordier_servant", 2, 1, Integer.MAX_VALUE);
        CAP_FROST_YETI = builder.comment("寒霜雪怪上限，默认 2（至多两个；没有聚晶来源，走冰霜召唤仪式）。")
                .defineInRange("frost_yeti_servant", 2, 1, Integer.MAX_VALUE);
        CAP_SEA_WAVE = builder.comment("骇浪上限，默认 2（至多两个）。")
                .defineInRange("sea_wave_servant", 2, 1, Integer.MAX_VALUE);
        CAP_DROUGHT_WILDFIRE = builder.comment("旱火野火上限，默认 2（至多两个）。")
                .defineInRange("drought_wildfire_servant", 2, 1, Integer.MAX_VALUE);
        CAP_SECOND_ROUNDER_ZOMBIE = builder.comment("二轮僵尸仆从上限，默认 16（与 Goety 僵尸仆从上限相互独立）。")
                .defineInRange("second_rounder_zombie_servant", 16, 1, Integer.MAX_VALUE);
        CAP_SECOND_ROUNDER_SKELETON = builder.comment("二轮骷髅仆从上限，默认 16（与 Goety 骷髅仆从上限相互独立）。")
                .defineInRange("second_rounder_skeleton_servant", 16, 1, Integer.MAX_VALUE);
        CAP_SECOND_ROUNDER_SPIDER = builder.comment("二轮蜘蛛仆从上限，默认 16（与 Goety 蜘蛛仆从上限相互独立）。")
                .defineInRange("second_rounder_spider_servant", 16, 1, Integer.MAX_VALUE);
        CAP_ENDER_STOWAWAY = builder.comment("末影渡客仆从上限，默认 2（没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("ender_stowaway_servant", 2, 1, Integer.MAX_VALUE);
        CAP_SERPON_COLONIAL_SOLDIER = builder.comment("蛇龙殖民兵 / 先锋共享上限，默认 16（两种合占一个名额；没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("serpon_colonial_soldier", 16, 1, Integer.MAX_VALUE);
        CAP_SERPON_RIOTER = builder.comment("蛇龙暴动者 / 突进者共享上限，默认 8（两种合占一个名额；没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("serpon_rioter", 8, 1, Integer.MAX_VALUE);
        CAP_SERPON_PIONEER = builder.comment("蛇龙先锋的上限读数（与蛇龙殖民兵共享，两种合占一个名额，默认 16）。")
                .defineInRange("serpon_pioneer", 16, 1, Integer.MAX_VALUE);
        CAP_SERPON_FIRE_APOSTLE = builder.comment("蛇龙火焰使徒仆从上限，默认 2（没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("serpon_fire_apostle", 2, 1, Integer.MAX_VALUE);
        CAP_SERPON_INTRUDER = builder.comment("蛇龙突进者的上限读数（与蛇龙暴动者共享，两种合占一个名额，默认 8）。")
                .defineInRange("serpon_intruder", 8, 1, Integer.MAX_VALUE);
        CAP_SERPON_FLAME_WIZARD = builder.comment("蛇龙火焰巫师仆从上限，默认 4（没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("serpon_flame_wizard", 4, 1, Integer.MAX_VALUE);
        CAP_WAR_PYTHON = builder.comment("战蟒仆从上限，默认 4（没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("war_python", 4, 1, Integer.MAX_VALUE);
        CAP_ROYAL_WAR_PYTHON = builder.comment("皇家战蟒仆从上限，默认 2（没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("royal_war_python", 2, 1, Integer.MAX_VALUE);
        CAP_SERPON_COMBAT_EXECUTER = builder.comment("蛇龙执战者上限，默认 1（Boss 级；没有聚晶来源，走暗祭坛转化 / 召唤仪式）。")
                .defineInRange("serpon_combat_executer", 1, 1, Integer.MAX_VALUE);
        CAP_SECOND_ROUNDER_ANIMATED_ARMOR = builder.comment("活化二轮盔甲仆从上限，默认 8（活化聚晶 / 暗祭坛转化 / 锻造仪式共用本值）。")
                .defineInRange("second_rounder_animated_armor_servant", 8, 1, Integer.MAX_VALUE);
        CAP_FLYING_FIREBALL = builder.comment("飞行火团仆从上限，默认 16（与火团聚晶 summon_limit 同值，"
                        + "刷怪蛋 / 暗祭坛召唤也读这里）。")
                .defineInRange("flying_fireball_servant", 16, 1, Integer.MAX_VALUE);
        CAP_IRON_TEETH = builder.comment("铁齿仆从上限，默认 8（与铁齿聚晶 summon_limit 同值，"
                        + "刷怪蛋 / 暗祭坛召唤也读这里）。")
                .defineInRange("iron_teeth_servant", 8, 1, Integer.MAX_VALUE);
        builder.pop();
        builder.pop();
    }

    public static class Section {
        public final ForgeConfigSpec.DoubleValue MAX_HEALTH;
        public final ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_SPEED;
        public final ForgeConfigSpec.DoubleValue ARMOR;
        public final ForgeConfigSpec.DoubleValue ARMOR_TOUGHNESS;
        public final ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;

        Section(ForgeConfigSpec.DoubleValue maxHealth, ForgeConfigSpec.DoubleValue attackDamage,
                ForgeConfigSpec.DoubleValue movementSpeed, ForgeConfigSpec.DoubleValue armor,
                ForgeConfigSpec.DoubleValue armorToughness, ForgeConfigSpec.DoubleValue knockbackResistance) {
            this.MAX_HEALTH = maxHealth;
            this.ATTACK_DAMAGE = attackDamage;
            this.MOVEMENT_SPEED = movementSpeed;
            this.ARMOR = armor;
            this.ARMOR_TOUGHNESS = armorToughness;
            this.KNOCKBACK_RESISTANCE = knockbackResistance;
        }

        public void applyTo(net.minecraft.world.entity.LivingEntity entity) {
            float healthBefore = entity.getHealth();
            setBaseAttribute(entity, Attributes.MAX_HEALTH, MAX_HEALTH.get());
            setBaseAttribute(entity, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE.get());
            setBaseAttribute(entity, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED.get());
            setBaseAttribute(entity, Attributes.ARMOR, ARMOR.get());
            setBaseAttribute(entity, Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS.get());
            setBaseAttribute(entity, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE.get());
            float maxHealth = entity.getMaxHealth();
            if (healthBefore > maxHealth) {
                entity.setHealth(maxHealth);
            }
        }

        public static void setBaseAttribute(net.minecraft.world.entity.LivingEntity entity,
                net.minecraft.world.entity.ai.attributes.Attribute attribute, double value) {
            net.minecraft.world.entity.ai.attributes.AttributeInstance instance = entity.getAttribute(attribute);
            if (instance != null) {
                instance.setBaseValue(value);
            }
        }

        static <T extends Section> T build(ForgeConfigSpec.Builder builder, String name, String comment,
                double speed, double health, double armor, double attack, double knockback,
                double toughness, java.util.function.BiFunction<ForgeConfigSpec.Builder, String, T> factory,
                Consumer<ForgeConfigSpec.Builder> extra) {
            builder.comment(comment).push(name);
            T section = factory.apply(builder, name);
            if (extra != null) {
                extra.accept(builder);
            }
            builder.pop();
            return section;
        }

        static Section build(ForgeConfigSpec.Builder builder, String name, String comment,
                double speed, double health, double armor, double attack, double knockback,
                double toughness) {
            return build(builder, name, comment, speed, health, armor, attack, knockback, toughness,
                    (b, n) -> new Section(
                            defineHealth(b, health),
                            defineAttack(b, attack),
                            defineSpeed(b, speed),
                            defineArmor(b, armor),
                            defineToughness(b, toughness),
                            defineKnockback(b, knockback)),
                    null);
        }

        static ForgeConfigSpec.DoubleValue defineHealth(ForgeConfigSpec.Builder builder, double value) {
            return builder.comment("最大生命值。").defineInRange("max_health", value, 1.0D, Double.MAX_VALUE);
        }

        static ForgeConfigSpec.DoubleValue defineAttack(ForgeConfigSpec.Builder builder, double value) {
            return builder.comment("基础攻击伤害（近战面板值）。")
                    .defineInRange("attack_damage", value, 0.0D, Double.MAX_VALUE);
        }

        static ForgeConfigSpec.DoubleValue defineSpeed(ForgeConfigSpec.Builder builder, double value) {
            return builder.comment("移动速度。").defineInRange("movement_speed", value, 0.0D, Double.MAX_VALUE);
        }

        static ForgeConfigSpec.DoubleValue defineArmor(ForgeConfigSpec.Builder builder, double value) {
            return builder.comment("护甲值。").defineInRange("armor", value, 0.0D, Double.MAX_VALUE);
        }

        static ForgeConfigSpec.DoubleValue defineToughness(ForgeConfigSpec.Builder builder, double value) {
            return builder.comment("护甲韧性。").defineInRange("armor_toughness", value, 0.0D, Double.MAX_VALUE);
        }

        static ForgeConfigSpec.DoubleValue defineKnockback(ForgeConfigSpec.Builder builder, double value) {
            return builder.comment("击退抗性，范围为 0.0 至 1.0。")
                    .defineInRange("knockback_resistance", value, 0.0D, Double.MAX_VALUE);
        }
    }

    public static final class DroughtWildfireSection extends Section {
        public final ForgeConfigSpec.DoubleValue SHIELD_MAX_HEALTH;

        private DroughtWildfireSection(ForgeConfigSpec.DoubleValue shieldMaxHealth,
                ForgeConfigSpec.DoubleValue maxHealth, ForgeConfigSpec.DoubleValue attackDamage,
                ForgeConfigSpec.DoubleValue movementSpeed, ForgeConfigSpec.DoubleValue armor,
                ForgeConfigSpec.DoubleValue armorToughness, ForgeConfigSpec.DoubleValue knockbackResistance) {
            super(maxHealth, attackDamage, movementSpeed, armor, armorToughness, knockbackResistance);
            this.SHIELD_MAX_HEALTH = shieldMaxHealth;
        }

        static DroughtWildfireSection build(ForgeConfigSpec.Builder builder) {
            return Section.build(builder, "drought_wildfire_servant",
                    "旱火野火（J 村旱火野火）仆从的属性；默认值照抄 J 村本体。"
                            + "它举盾格挡时另有一套护盾值（J 村 10 点）。",
                    0.3D, 120.0D, 8.0D, 7.0D, 0.6D, 0.0D,
                    (b, name) -> new DroughtWildfireSection(
                            b.comment("护盾上限（J 村旱火野火举盾格挡时的独立血条）；默认 10。")
                                    .defineInRange("shield_max_health", 10.0D, 1.0D, Double.MAX_VALUE),
                            defineHealth(b, 120.0D),
                            defineAttack(b, 7.0D),
                            defineSpeed(b, 0.3D),
                            defineArmor(b, 8.0D),
                            defineToughness(b, 0.0D),
                            defineKnockback(b, 0.6D)),
                    null);
        }
    }

    public static final class GiantSection extends Section {
        public final ForgeConfigSpec.DoubleValue GIANT_HEALTH_MULTIPLIER;
        public final ForgeConfigSpec.DoubleValue GIANT_ATTACK_MULTIPLIER;

        private GiantSection(ForgeConfigSpec.DoubleValue giantHealthMultiplier,
                ForgeConfigSpec.DoubleValue giantAttackMultiplier,
                ForgeConfigSpec.DoubleValue maxHealth, ForgeConfigSpec.DoubleValue attackDamage,
                ForgeConfigSpec.DoubleValue movementSpeed, ForgeConfigSpec.DoubleValue armor,
                ForgeConfigSpec.DoubleValue armorToughness, ForgeConfigSpec.DoubleValue knockbackResistance) {
            super(maxHealth, attackDamage, movementSpeed, armor, armorToughness, knockbackResistance);
            this.GIANT_HEALTH_MULTIPLIER = giantHealthMultiplier;
            this.GIANT_ATTACK_MULTIPLIER = giantAttackMultiplier;
        }

        public double giantHealth() {
            return Math.max(1.0D, Math.round(MAX_HEALTH.get() * GIANT_HEALTH_MULTIPLIER.get()));
        }

        public double giantAttack() {
            return Math.max(0.0D, Math.round(ATTACK_DAMAGE.get() * GIANT_ATTACK_MULTIPLIER.get()));
        }

        static GiantSection build(ForgeConfigSpec.Builder builder, String name, String comment,
                double speed, double health, double armor, double attack, double knockback, double toughness,
                double giantHealthMultiplier, double giantAttackMultiplier) {
            return Section.build(builder, name, comment, speed, health, armor, attack, knockback, toughness,
                    (b, n) -> new GiantSection(
                            b.comment("巨人形态的最大生命倍率；默认 " + trim(giantHealthMultiplier)
                                            + "（J 村 35 → 70、30 → 60）。")
                                    .defineInRange("giant_health_multiplier", giantHealthMultiplier,
                                            1.0D, Double.MAX_VALUE),
                            b.comment("巨人形态的攻击伤害倍率；默认 " + trim(giantAttackMultiplier)
                                            + "（J 村 3 → 7、4 → 9 均按四舍五入取整）。")
                                    .defineInRange("giant_attack_multiplier", giantAttackMultiplier,
                                            1.0D, Double.MAX_VALUE),
                            defineHealth(b, health),
                            defineAttack(b, attack),
                            defineSpeed(b, speed),
                            defineArmor(b, armor),
                            defineToughness(b, toughness),
                            defineKnockback(b, knockback)),
                    null);
        }

        private static String trim(double value) {
            return value == Math.floor(value) ? String.valueOf((long) value) : String.valueOf(value);
        }
    }

    private JerotesVillageServantConfig() {
    }
}
