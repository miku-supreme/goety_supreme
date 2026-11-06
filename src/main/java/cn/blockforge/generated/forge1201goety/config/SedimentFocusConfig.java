package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.spell.SedimentSummonSpell;

public final class SedimentFocusConfig {

    public static Section GUARD;
    public static Section SOUL_STABILIZER;
    public static Section HEAVY_GUARD;
    public static Section BULGE;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        GUARD = Section.build(builder, "sediment_guard_focus",
                "沉降戍卫聚晶：召唤 J 村的沉降守卫。",
                100 * 20, 16, 24, 180, 100, 300);
        SOUL_STABILIZER = Section.build(builder, "sediment_soul_stabilizer_focus",
                "沉降魂守聚晶：召唤 J 村的沉降定灵人（远程沉降法术弹 + 近战）。",
                100 * 20, 12, 24, 60, 100, 300);
        HEAVY_GUARD = Section.build(builder, "heavy_armored_sediment_guard_focus",
                "沉降重卫聚晶：召唤 J 村的重装沉降守卫（重甲近战 + 命中横扫）。",
                100 * 20, 8, 24, 180, 400, 300);
        BULGE = Section.build(builder, "bulge_focus",
                "苍澜聚晶：召唤 J 村的波涛人（水陆两栖、远程波涛弹）；数值照搬燃烧聚晶，"
                        + "召唤压制 200 刻（10 秒）。",
                100 * 20, 16, 16, 100, 200, 200);
    }

    public static final class Section {
        public final ForgeConfigSpec.IntValue soulCost;
        public final ForgeConfigSpec.IntValue castDuration;
        public final ForgeConfigSpec.IntValue cooldown;
        public final ForgeConfigSpec.IntValue summonDownTicks;
        public final ForgeConfigSpec.IntValue summonLimit;
        public final ForgeConfigSpec.IntValue lifespanTicks;
        public final ForgeConfigSpec.IntValue durationBonusTicks;
        public final ForgeConfigSpec.IntValue summonCountMin;
        public final ForgeConfigSpec.IntValue summonCountMax;

        private Section(ForgeConfigSpec.IntValue soulCost, ForgeConfigSpec.IntValue castDuration,
                ForgeConfigSpec.IntValue cooldown, ForgeConfigSpec.IntValue summonDownTicks,
                ForgeConfigSpec.IntValue summonLimit, ForgeConfigSpec.IntValue lifespanTicks,
                ForgeConfigSpec.IntValue durationBonusTicks, ForgeConfigSpec.IntValue summonCountMin,
                ForgeConfigSpec.IntValue summonCountMax) {
            this.soulCost = soulCost;
            this.castDuration = castDuration;
            this.cooldown = cooldown;
            this.summonDownTicks = summonDownTicks;
            this.summonLimit = summonLimit;
            this.lifespanTicks = lifespanTicks;
            this.durationBonusTicks = durationBonusTicks;
            this.summonCountMin = summonCountMin;
            this.summonCountMax = summonCountMax;
        }

        static Section build(ForgeConfigSpec.Builder builder, String name, String comment,
                int lifespanDefaultTicks, int summonLimitDefault,
                int soulCostDefault, int castDurationDefault, int cooldownDefault,
                int summonDownDefault) {
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
                    builder.comment("同一主人同时存在的该种仆从上限。")
                            .defineInRange("summon_limit", summonLimitDefault, 1, Integer.MAX_VALUE),
                    builder.comment("普通召唤的仆从寿命（刻），默认见各聚晶说明。")
                            .defineInRange("lifespan_ticks", lifespanDefaultTicks, 1, Integer.MAX_VALUE),
                    builder.comment("持久附魔每一级额外增加的寿命（刻）。")
                            .defineInRange("duration_bonus_ticks", 120 * 20, 0, Integer.MAX_VALUE),
                    builder.comment("普通施法召唤数量的下限。")
                            .defineInRange("summon_count_min", 1, 1, Integer.MAX_VALUE),
                    builder.comment("普通施法召唤数量的上限（含）。")
                            .defineInRange("summon_count_max", 1, 1, Integer.MAX_VALUE));
            builder.pop();
            return section;
        }
    }

    private SedimentFocusConfig() {
    }
}
