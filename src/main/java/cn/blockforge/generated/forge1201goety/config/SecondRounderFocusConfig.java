package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.spell.SecondRounderSummonSpell;

public final class SecondRounderFocusConfig {

    public static Values ROTTING;

    public static Values OSSEOUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        ROTTING = Values.build(builder, "second_rounder_rotting_focus",
                "二轮腐烂聚晶：换色版腐烂聚晶，召唤 J 村的二轮僵尸仆从。"
                        + "消耗 10、前摇 30 刻（1.5 秒）、冷却 100 刻、"
                        + "召唤压制比本体腐烂聚晶（120）多 2 秒 = 160 刻。",
                10, 30, 100, 160);
        OSSEOUS = Values.build(builder, "second_rounder_osseous_focus",
                "二轮骨质聚晶：换色版骨质聚晶，召唤 J 村的二轮骷髅仆从。"
                        + "消耗 12、前摇 80 刻（4 秒）、冷却 100 刻、"
                        + "召唤压制比本体骨质聚晶（120）多 2 秒 = 160 刻。",
                12, 80, 100, 160);
    }

    public static final class Values {
        public final ForgeConfigSpec.IntValue soulCost;
        public final ForgeConfigSpec.IntValue castDuration;
        public final ForgeConfigSpec.IntValue cooldown;
        public final ForgeConfigSpec.IntValue summonDownTicks;

        private Values(ForgeConfigSpec.IntValue soulCost, ForgeConfigSpec.IntValue castDuration,
                ForgeConfigSpec.IntValue cooldown, ForgeConfigSpec.IntValue summonDownTicks) {
            this.soulCost = soulCost;
            this.castDuration = castDuration;
            this.cooldown = cooldown;
            this.summonDownTicks = summonDownTicks;
        }

        static Values build(ForgeConfigSpec.Builder builder, String name, String comment,
                int soulCost, int castDuration, int cooldown, int summonDownTicks) {
            builder.comment(comment).push(name);
            Values values = new Values(
                    builder.comment("一次施法消耗的灵魂能量。")
                            .defineInRange("soul_cost", soulCost, 0, Integer.MAX_VALUE),
                    builder.comment("施法前摇（刻）。")
                            .defineInRange("cast_duration", castDuration, 1, Integer.MAX_VALUE),
                    builder.comment("施法后的冷却（刻）。")
                            .defineInRange("cooldown", cooldown, 0, Integer.MAX_VALUE),
                    builder.comment("连发压制（Summon Down）持续时间（刻）；0 关闭。")
                            .defineInRange("summon_down_ticks", summonDownTicks, 0, Integer.MAX_VALUE));
            builder.pop();
            return values;
        }
    }

    private SecondRounderFocusConfig() {
    }
}
