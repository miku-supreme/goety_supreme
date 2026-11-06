package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import cn.blockforge.generated.goetysupreme.config.GolemServantConfig;
import cn.blockforge.generated.goetysupreme.config.GuardianServantConfig;
import cn.blockforge.generated.goetysupreme.config.JerotesVillageServantConfig;
import cn.blockforge.generated.goetysupreme.config.MutantEndermanServantConfig;
import cn.blockforge.generated.goetysupreme.config.MutantSkeletonServantConfig;
import cn.blockforge.generated.goetysupreme.config.MutantSnowGolemServantConfig;
import cn.blockforge.generated.goetysupreme.config.MutantZombieServantConfig;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.config.SakuraSpiritConfig;
import cn.blockforge.generated.goetysupreme.config.ServantCapConfig;
import cn.blockforge.generated.goetysupreme.config.SnowGolemServantConfig;
import cn.blockforge.generated.goetysupreme.config.WitherServantConfig;

public final class SupremeServantConfigs {

    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Goety Supreme 仆从配置：每个仆从一个小节；修改后重启游戏生效。",
                "聚晶相关配置在同目录的 focus.toml 中。");
        builder.comment("四个突变生物仆从（僵尸 / 雪傀儡 / 骷髅 / 末影人）的统一大节。")
                .push("mutant_monsters");
        MutantZombieServantConfig.buildInto(builder);
        MutantSnowGolemServantConfig.buildInto(builder);
        MutantSkeletonServantConfig.buildInto(builder);
        MutantEndermanServantConfig.buildInto(builder);
        builder.pop();
        SnowGolemServantConfig.buildInto(builder);
        PlayerMobServantConfig.buildInto(builder);
        GuardianServantConfig.buildInto(builder);
        SakuraSpiritConfig.buildInto(builder);
        WitherServantConfig.buildInto(builder);
        NamelessWitherConfig.buildInto(builder);
        builder.comment("四个傀儡仆从（铁 / 熔炉 / 岩浆 / 监狱）的统一大节，进去各占一小节。")
                .push("golem");
        GolemServantConfig.buildInto(builder);
        builder.pop();
        JerotesVillageServantConfig.buildInto(builder);
        ServantCapConfig.linkJerotesCaps();
        ServantCapConfig.buildInto(builder);
        SPEC = builder.build();
    }

    private SupremeServantConfigs() {
    }
}
