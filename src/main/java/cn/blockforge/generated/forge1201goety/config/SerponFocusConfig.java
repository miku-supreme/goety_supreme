package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.spell.SerponSummonSpell;

public final class SerponFocusConfig {

    public static Section HEAD;
    public static Section CROWN;
    public static Section FLAME;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        HEAD = Section.build(builder, "serpon_head_focus",
                "蛇首聚晶：召唤蛇龙先锋或蛇龙殖民兵。普通魔杖 50% 二选一；下界魔杖二者各召唤 2 个。",
                60 * 20, 16, 16, 100, 200, 400);
        CROWN = Section.build(builder, "serpon_crown_focus",
                "蛇冠聚晶：召唤蛇龙暴动者或蛇龙突进者。普通魔杖 50% 二选一；下界魔杖必定两种各召唤 1 个。"
                        + "数值对标「诡厄灾变」的渊没浪涌聚晶（sunken_swell_focus）。",
                60 * 20, 8, 24, 60, 100, 300);
        FLAME = Section.build(builder, "serpon_flame_focus",
                "蛇焰聚晶：召唤蛇龙火焰巫师。普通魔杖召唤 1 个；下界魔杖召唤 2 个。"
                        + "数值对标「诡厄灾变」的渊没领头聚晶（sunken_tribune_focus，用户写作「渊没民宫」）。",
                60 * 20, 4, 24, 100, 1200, 300);
    }

    public static final class Section {
        public final ForgeConfigSpec.IntValue soulCost;
        public final ForgeConfigSpec.IntValue castDuration;
        public final ForgeConfigSpec.IntValue cooldown;
        public final ForgeConfigSpec.IntValue summonLimit;
        public final ForgeConfigSpec.IntValue summonDownTicks;
        public final ForgeConfigSpec.IntValue lifespanTicks;
        public final ForgeConfigSpec.IntValue durationBonusTicks;

        private Section(ForgeConfigSpec.IntValue soulCost, ForgeConfigSpec.IntValue castDuration,
                ForgeConfigSpec.IntValue cooldown, ForgeConfigSpec.IntValue summonLimit,
                ForgeConfigSpec.IntValue summonDownTicks, ForgeConfigSpec.IntValue lifespanTicks,
                ForgeConfigSpec.IntValue durationBonusTicks) {
            this.soulCost = soulCost;
            this.castDuration = castDuration;
            this.cooldown = cooldown;
            this.summonLimit = summonLimit;
            this.summonDownTicks = summonDownTicks;
            this.lifespanTicks = lifespanTicks;
            this.durationBonusTicks = durationBonusTicks;
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
                    builder.comment("同一主人同时存在的该类蛇龙仆从上限。")
                            .defineInRange("summon_limit", summonLimitDefault, 1, Integer.MAX_VALUE),
                    builder.comment("连发压制（Summon Down）持续时间（刻）；0 关闭。")
                            .defineInRange("summon_down_ticks", summonDownDefault, 0, Integer.MAX_VALUE),
                    builder.comment("普通召唤的仆从寿命（刻），默认 1200（60 秒）。")
                            .defineInRange("lifespan_ticks", lifespanDefaultTicks, 1, Integer.MAX_VALUE),
                    builder.comment("持久附魔每一级额外增加的寿命（刻）。")
                            .defineInRange("duration_bonus_ticks", 120 * 20, 0, Integer.MAX_VALUE));
            builder.pop();
            return section;
        }
    }

    private SerponFocusConfig() {
    }
}
