package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class MountFocusConfig {

    public static Section SKELETON;

    public static Section WITHER;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        SKELETON = Section.build(builder, "skeleton_mount_focus",
                "骸骨坐骑聚晶：召唤骷髅马仆从（亡灵仆从），可空手右键骑乘。"
                        + "默认：灵魂 16、前摇 60 刻、冷却 100 刻、召唤压制 200 刻。",
                2400, 4, 16, 60, 100, 200);
        WITHER = Section.build(builder, "wither_skeleton_mount_focus",
                "凋亡坐骑聚晶：召唤凋灵骷髅马仆从（下界 + 亡灵仆从），可空手右键骑乘。"
                        + "默认：灵魂 24、前摇 80 刻、冷却 150 刻、召唤压制 300 刻。",
                2400, 4, 24, 80, 150, 300);
    }

    public static final class Section {
        public final ForgeConfigSpec.IntValue soulCost;
        public final ForgeConfigSpec.IntValue castDuration;
        public final ForgeConfigSpec.IntValue cooldown;
        public final ForgeConfigSpec.IntValue summonDownTicks;
        public final ForgeConfigSpec.IntValue summonLimit;
        public final ForgeConfigSpec.IntValue lifespanTicks;
        public final ForgeConfigSpec.IntValue durationBonusTicks;

        private Section(ForgeConfigSpec.IntValue soulCost, ForgeConfigSpec.IntValue castDuration,
                ForgeConfigSpec.IntValue cooldown, ForgeConfigSpec.IntValue summonDownTicks,
                ForgeConfigSpec.IntValue summonLimit, ForgeConfigSpec.IntValue lifespanTicks,
                ForgeConfigSpec.IntValue durationBonusTicks) {
            this.soulCost = soulCost;
            this.castDuration = castDuration;
            this.cooldown = cooldown;
            this.summonDownTicks = summonDownTicks;
            this.summonLimit = summonLimit;
            this.lifespanTicks = lifespanTicks;
            this.durationBonusTicks = durationBonusTicks;
        }

        static Section build(ForgeConfigSpec.Builder builder, String name, String comment,
                int lifespanDefaultTicks, int summonLimitDefault, int soulCostDefault,
                int castDurationDefault, int cooldownDefault, int summonDownDefault) {
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
                    builder.comment("同一主人同时存在的该类坐骑上限。")
                            .defineInRange("summon_limit", summonLimitDefault, 1, Integer.MAX_VALUE),
                    builder.comment("普通召唤的仆从寿命（刻），默认 2400（120 秒）。")
                            .defineInRange("lifespan_ticks", lifespanDefaultTicks, 1, Integer.MAX_VALUE),
                    builder.comment("持久附魔每一级额外增加的寿命（刻）。")
                            .defineInRange("duration_bonus_ticks", 2400, 0, Integer.MAX_VALUE));
            builder.pop();
            return section;
        }
    }

    private MountFocusConfig() {
    }
}
