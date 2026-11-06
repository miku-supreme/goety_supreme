package cn.blockforge.generated.goetysupreme.config;

import java.util.List;
import net.minecraftforge.common.ForgeConfigSpec;

public final class PerformanceConfig {

    public static ForgeConfigSpec.BooleanValue CLASS_WARMUP_ENABLED;

    public static ForgeConfigSpec.BooleanValue CLASS_WARMUP_GOETY_FAMILY;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> CLASS_WARMUP_MODS;

    public static ForgeConfigSpec.BooleanValue SKULL_CLOUD_SMOKE_NO_COLLISION;

    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Goety Supreme 客户端性能选项：预热与烟雾碰撞优化；不减少粒子数量。",
                "修改后重启游戏生效。").push("performance");

        CLASS_WARMUP_ENABLED = builder.comment(
                        "菜单期类预热开关。",
                        "打开后，游戏停在标题界面 / 加载界面时，本模组会把 Goety 家族与本模组自己的 class",
                        "提前读出来、装进 JVM（只加载类、不执行任何静态初始化）。",
                        "",
                        "为什么需要它：那份 goety_supreme-lag-8.log 里，13:18:21 那一次 5.27 秒的冻结写着",
                        "cls=+2589 jit=+16612ms，而同一刻 wither=0、particles=247——冻结的时间全花在",
                        "「第一次碰到某段代码时现读 .class 字节 + 变换 + 定义」上，不是凋灵、不是粒子。",
                        "把这些类提前在菜单里啃掉，打架时就不会再撞上那几秒的个位数帧。")
                .define("class_warmup_enabled", true);

        CLASS_WARMUP_GOETY_FAMILY = builder.comment(
                        "自动把 id 里含 \"goety\" 或 \"goetic\" 的模组全部纳入预热（例如 goety、",
                        "goety_supreme、goeticlegacy、goetyawaken、goetyominous、goetydelight）。",
                        "为什么是两个词根：goeticlegacy 的 id 里是 goeti 而不是 goety，只判 goety 会漏掉它。",
                        "缺哪个模组就自动跳过。",
                        "这些附属普遍带一批「装了某个可选模组才有用」的联动类；那个模组没装时它们的父类",
                        "在游戏里不存在。预热会先确认父类 / 接口链在场再加载，不在场的直接跳过，",
                        "所以不会因为预热它们而在 latest.log 里刷出一串事件总线的 ERROR。")
                .define("class_warmup_goety_family", true);

        CLASS_WARMUP_MODS = builder.comment(
                        "额外要预热的模组 id 名单，与上面的 Goety 家族名单取并集；本模组自己永远排第一。",
                        "默认额外带上 jerotes / jerotesvillage（蛇龙系仆从第一次出场会现加载它们的类）、",
                        "mutantmonsters / mutantmore（本模组的突变僵尸转换与化学 X 直接挂进这两个模组的类）、",
                        "cataclysm / citadel（goety_cataclysm 的底层库，德劳格亡灵法师等 mixin 打在它们的子类上）。",
                        "这份名单只做「再加」：同样的基线也写在代码里并恒取并集，所以从旧版升级上来的实例",
                        "（performance.toml 里还是旧列表）不会漏掉新补的模组；这里删条目也去不掉代码基线。",
                        "写不存在的 id 不会报错，只是跳过。")
                .defineListAllowEmpty("class_warmup_mods",
                        List.of("goety", "goety_supreme", "jerotes", "jerotesvillage",
                                "mutantmonsters", "mutantmore", "cataclysm", "citadel"),
                        entry -> entry instanceof String name && !name.isBlank());

        SKULL_CLOUD_SMOKE_NO_COLLISION = builder.comment(
                        "凋灵头落点药水云的烟雾跳过方块碰撞查询，默认开启，减少粒子更新开销。",
                        "不改云数量、喷发量、烟雾寿命、尺寸、贴图、药效和持续时间；不会限制粒子总数。",
                        "仅影响本模组普通及强化凋灵头的落点云；灭绝浓烟、拖尾、爆炸及其它烟雾不变。",
                        "开启后烟雾可能穿过墙壁或地面，不再被方块挡住；药水云的伤害判定不变。",
                        "设为 false 并重启可恢复原版烟雾碰撞，便于同场景对照；两档喷发量完全相同。")
                .define("skull_cloud_smoke_no_collision", true);

        builder.pop();
        SPEC = builder.build();
    }

    private PerformanceConfig() {
    }
}
