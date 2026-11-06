package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.event.CorruptedFocusLaserEvents;
import cn.blockforge.generated.goetysupreme.entity.GreenSmithLaserBeam;
import cn.blockforge.generated.goetysupreme.renderer.GreenSmithLaserRenderer;

public final class CorruptedFocusLaserConfig {

    public static ForgeConfigSpec.BooleanValue ENABLED;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.comment("Goety 腐化聚晶（corruption_focus）联动：玩家按住施法时改用绿豆同款激光。"
                        + "默认关闭；改动后重启游戏生效。")
                .push("corrupted_focus_laser");
        ENABLED = builder.comment("true：玩家用腐化聚晶射出的光束换成绿豆的蓝紫三层激光外观"
                        + "（伤害 / 射线 / 跟随视线逻辑不变）；false：保持 Goety 原版腐化光束。")
                .define("enabled", false);
        builder.pop();
    }

    public static boolean enabled() {
        try {
            return ENABLED != null && Boolean.TRUE.equals(ENABLED.get());
        } catch (Throwable ignored) {
            return false;
        }
    }

    private CorruptedFocusLaserConfig() {
    }
}
