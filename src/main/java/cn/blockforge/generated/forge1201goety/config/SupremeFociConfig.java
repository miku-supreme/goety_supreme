package cn.blockforge.generated.goetysupreme.config;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.util.MalignasaurSpells;
import cn.blockforge.generated.goetysupreme.config.AnimatedIronFocusConfig;
import cn.blockforge.generated.goetysupreme.config.AsphyxiaFocusConfig;
import cn.blockforge.generated.goetysupreme.config.BitterColdWindFocusConfig;
import cn.blockforge.generated.goetysupreme.config.CorrosiveIceSpikeFocusConfig;
import cn.blockforge.generated.goetysupreme.config.CorrosiverBreathFocusConfig;
import cn.blockforge.generated.goetysupreme.config.CorruptedFocusLaserConfig;
import cn.blockforge.generated.goetysupreme.config.DroughtBlazeFocusConfig;
import cn.blockforge.generated.goetysupreme.config.DroughtMissileFocusConfig;
import cn.blockforge.generated.goetysupreme.config.EndermanHandFocusConfig;
import cn.blockforge.generated.goetysupreme.config.EndermanServantFocusConfig;
import cn.blockforge.generated.goetysupreme.config.FrostbornFocusConfig;
import cn.blockforge.generated.goetysupreme.config.FrostSnowFocusConfig;
import cn.blockforge.generated.goetysupreme.config.GiantHammerFocusConfig;
import cn.blockforge.generated.goetysupreme.config.HauntedArmorFocusConfig;
import cn.blockforge.generated.goetysupreme.config.HotWindFocusConfig;
import cn.blockforge.generated.goetysupreme.config.IceShardFocusConfig;
import cn.blockforge.generated.goetysupreme.config.IronGuardFocusConfig;
import cn.blockforge.generated.goetysupreme.config.MalignasaurBombFocusConfig;
import cn.blockforge.generated.goetysupreme.config.MeteorFocusConfig;
import cn.blockforge.generated.goetysupreme.config.MountFocusConfig;
import cn.blockforge.generated.goetysupreme.config.NewFocusSummonConfig;
import cn.blockforge.generated.goetysupreme.config.OceanBreathFocusConfig;
import cn.blockforge.generated.goetysupreme.config.PikeRainFocusConfig;
import cn.blockforge.generated.goetysupreme.config.PlayerMobFocusConfig;
import cn.blockforge.generated.goetysupreme.config.RagingSharkFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SecondRounderFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SedimentFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SedimentMissileFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SerponBlazingFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SerponFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SnowballFocusConfig;
import cn.blockforge.generated.goetysupreme.config.SpinScytheFocusConfig;
import cn.blockforge.generated.goetysupreme.config.WarPythonFocusConfig;
import cn.blockforge.generated.goetysupreme.config.WaveBoltFocusConfig;
import cn.blockforge.generated.goetysupreme.config.WhirlpoolFocusConfig;

public final class SupremeFociConfig {

    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Goety Supreme 聚晶配置：每种聚晶一个小节；修改后重启游戏生效。",
                "仆从相关配置在同目录的 servants.toml 中。");
        CorruptedFocusLaserConfig.buildInto(builder);
        PlayerMobFocusConfig.buildInto(builder);
        SnowballFocusConfig.buildInto(builder);
        IceShardFocusConfig.buildInto(builder);
        EndermanHandFocusConfig.buildInto(builder);
        GiantHammerFocusConfig.buildInto(builder);
        FrostSnowFocusConfig.buildInto(builder);
        FrostbornFocusConfig.buildInto(builder);
        IronGuardFocusConfig.buildInto(builder);
        EndermanServantFocusConfig.buildInto(builder);
        SakuraSpiritFocusConfig.buildInto(builder);
        MountFocusConfig.buildInto(builder);
        HauntedArmorFocusConfig.buildInto(builder);
        RagingSharkFocusConfig.buildInto(builder);
        SpinScytheFocusConfig.buildInto(builder);
        builder.comment("J 村（jerotesvillage）联动聚晶的统一大节：沉降系 / 苍澜 / 澜弹 / 焦炎、"
                        + "沉降飞弹 / 焦燃飞弹、二轮腐烂 / 骨质、蛇龙三聚晶（蛇首 / 蛇冠 / 蛇焰）、"
                        + "炽焰 / 蚀龙吐息与活化聚晶，进去各占一小节；修改后重启游戏生效。")
                .push("jerotes_village");
        SedimentFocusConfig.buildInto(builder);
        DroughtBlazeFocusConfig.buildInto(builder);
        DroughtMissileFocusConfig.buildInto(builder);
        SedimentMissileFocusConfig.buildInto(builder);
        WaveBoltFocusConfig.buildInto(builder);
        SecondRounderFocusConfig.buildInto(builder);
        SerponFocusConfig.buildInto(builder);
        WarPythonFocusConfig.buildInto(builder);
        SerponBlazingFocusConfig.buildInto(builder);
        CorrosiverBreathFocusConfig.buildInto(builder);
        AnimatedIronFocusConfig.buildInto(builder);
        CorrosiveIceSpikeFocusConfig.buildInto(builder);
        BitterColdWindFocusConfig.buildInto(builder);
        HotWindFocusConfig.buildInto(builder);
        MeteorFocusConfig.buildInto(builder);
        PikeRainFocusConfig.buildInto(builder);
        OceanBreathFocusConfig.buildInto(builder);
        WhirlpoolFocusConfig.buildInto(builder);
        AsphyxiaFocusConfig.buildInto(builder);
        MalignasaurBombFocusConfig.buildInto(builder);
        NewFocusSummonConfig.buildInto(builder);
        builder.pop();
        SPEC = builder.build();
    }

    public static int randomSummonCount(LivingEntity caster, ForgeConfigSpec.IntValue min,
            ForgeConfigSpec.IntValue max) {
        int low = Math.max(1, min.get());
        int high = Math.max(low, max.get());
        return high == low ? low : low + caster.getRandom().nextInt(high - low + 1);
    }

    private SupremeFociConfig() {
    }
}
