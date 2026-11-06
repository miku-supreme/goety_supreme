package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.spell.ServantSummonSpell;
import cn.blockforge.generated.goetysupreme.config.FrostbornFocusConfig;

public final class NewFocusSummonConfig {

    public static Section IRON_TEETH;

    public static Section FIREBALL;

    public static Section BLAZING_SPIRIT;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        IRON_TEETH = Section.build(builder, "iron_teeth_focus",
                "铁齿聚晶：召唤铁齿仆从（地卜仆从）；数值对标 Goety 小兽聚晶。"
                        + "默认：普通施法 1 只、手持地卜杖 2-3 只、周身上限 8。",
                2400, 8, 8, 20, 200, 100, 2, 3);
        FIREBALL = Section.build(builder, "flying_fireball_focus",
                "火团聚晶：召唤飞行火团仆从（下界仆从）；数值对标 Goety 恼鬼聚晶。"
                        + "默认：普通施法 1 只、手持下界杖 2-4 只、周身上限 16。"
                        + "冷却 200 刻（10 秒）、召唤压制 100 刻（5 秒）。",
                2400, 16, 18, 100, 200, 100, 2, 4);
        BLAZING_SPIRIT = Section.build(builder, "blazing_spirit_focus",
                "燃灵聚晶：召唤火灵仆从（下界仆从）；数值前摇 / 冷却对标霜裔聚晶。"
                        + "默认：普通施法 1 只、手持下界魔杖 25% 概率 2 只、周身上限 4。",
                2400, 4, 24, 20, 1200, 300, 1, 1);
    }

    public static final class Section {
        public final ForgeConfigSpec.IntValue soulCost;
        public final ForgeConfigSpec.IntValue castDuration;
        public final ForgeConfigSpec.IntValue cooldown;
        public final ForgeConfigSpec.IntValue summonDownTicks;
        public final ForgeConfigSpec.IntValue summonLimit;
        public final ForgeConfigSpec.IntValue lifespanTicks;
        public final ForgeConfigSpec.IntValue durationBonusTicks;
        public final ForgeConfigSpec.IntValue staffSummonCountMin;
        public final ForgeConfigSpec.IntValue staffSummonCountMax;

        private Section(ForgeConfigSpec.IntValue soulCost, ForgeConfigSpec.IntValue castDuration,
                ForgeConfigSpec.IntValue cooldown, ForgeConfigSpec.IntValue summonDownTicks,
                ForgeConfigSpec.IntValue summonLimit, ForgeConfigSpec.IntValue lifespanTicks,
                ForgeConfigSpec.IntValue durationBonusTicks, ForgeConfigSpec.IntValue staffSummonCountMin,
                ForgeConfigSpec.IntValue staffSummonCountMax) {
            this.soulCost = soulCost;
            this.castDuration = castDuration;
            this.cooldown = cooldown;
            this.summonDownTicks = summonDownTicks;
            this.summonLimit = summonLimit;
            this.lifespanTicks = lifespanTicks;
            this.durationBonusTicks = durationBonusTicks;
            this.staffSummonCountMin = staffSummonCountMin;
            this.staffSummonCountMax = staffSummonCountMax;
        }

        static Section build(ForgeConfigSpec.Builder builder, String name, String comment,
                int lifespanDefaultTicks, int summonLimitDefault, int soulCostDefault,
                int castDurationDefault, int cooldownDefault, int summonDownDefault,
                int staffSummonCountMinDefault, int staffSummonCountMaxDefault) {
            builder.comment(comment).push(name);
            Section section = new Section(
                    builder.comment("一次施法消耗的灵魂能量。")
                            .defineInRange("soul_cost", soulCostDefault, 0, Integer.MAX_VALUE),
                    builder.comment("施法前摇（刻）。")
                            .defineInRange("cast_duration", castDurationDefault, 1, Integer.MAX_VALUE),
                    builder.comment("施法后的冷却（刻）。")
                            .defineInRange("cooldown", cooldownDefault, 0, Integer.MAX_VALUE),
                    builder.comment("召唤压制（Summon Down）持续时间（刻）；0 关闭。")
                            .defineInRange("summon_down_ticks", summonDownDefault, 0, Integer.MAX_VALUE),
                    builder.comment("同一主人同时存在的该类仆从上限。")
                            .defineInRange("summon_limit", summonLimitDefault, 1, Integer.MAX_VALUE),
                    builder.comment("普通召唤的仆从寿命（刻），默认 2400（120 秒）。")
                            .defineInRange("lifespan_ticks", lifespanDefaultTicks, 1, Integer.MAX_VALUE),
                    builder.comment("持久附魔每一级额外增加的寿命（刻）。")
                            .defineInRange("duration_bonus_ticks", 2400, 0, Integer.MAX_VALUE),
                    builder.comment("手持本系法杖（铁齿=地卜杖 / 火团=下界杖）施法时召唤数量的下限。")
                            .defineInRange("staff_summon_count_min", staffSummonCountMinDefault, 1,
                                    Integer.MAX_VALUE),
                    builder.comment("手持本系法杖施法时召唤数量的上限（含）。")
                            .defineInRange("staff_summon_count_max", staffSummonCountMaxDefault, 1,
                                    Integer.MAX_VALUE));
            builder.pop();
            return section;
        }
    }

    private NewFocusSummonConfig() {
    }
}
