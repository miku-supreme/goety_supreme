# Goety Supreme

仓库地址：<https://github.com/miku-supreme/goety_supreme>

基于 **Minecraft 1.20.1 + Forge** 的 Goety 扩展模组，为 Goety 增添了一整套「仆从 / 使徒 / Boss」内容与战斗机制。

> 本仓库只包含本模组**自己的**源码与资源。
> 出于版权原因，`libs/` 下的第三方模组 jar、`ref/` 下的反编译参考代码都不包含在内。

## 内容概览

- **镶星仆从体系**：僵尸、骷髅、末影人仆从可被给予狂暴之星，获得通用强化（攻击力、血量、护甲与韧性提升）与各自的专属技能。
- **狂暴红石巨兽**：给 Goety 的红石巨兽喂狂暴之星后，整套骨架动作换成作者上传的《红石巨兽》模型动画，并追加重型粉碎、单手拍地、怒吼、喷射、龙车、红石怪、对空炮、擦桌子等技能。
- **召唤师 / 使徒 / 等多种自定义生物**，以及配套的法术焦点、聚晶、贴图与音效。

## 环境要求

| 项目 | 版本 |
| --- | --- |
| Minecraft | 1.20.1 |
| Forge | 47.4.0 |
| Java | 17 |
| Goety（必需） | 2.5.56.0 及以上（开发基线 2.5.60.0） |

可选软依赖（未安装时相关内容整块不注册，不影响启动）：

- Mutant Monsters 8.0.3+（突变生物仆从、可憎雪傀儡）
- PuzzlesLib（跟随 Mutant Monsters）
- jerotes / jerotesvillage（J 村联动内容）
- Jade
- Curios

## 从源码构建

本仓库**不包含**第三方模组 jar。克隆后请自行准备依赖，放入工程根目录的 `libs/`：

```
libs/goety-2.5.60.0.jar
libs/MutantMonsters-v8.0.8-1.20.1-Forge.jar
libs/PuzzlesLib-v8.1.33-1.20.1-Forge.jar
libs/Jade-1.20.1-Forge-11.13.3.jar
libs/jerotes-1.20.1-1.3.4.jar
libs/jerotesvillage-1.20.1-1.1.6.jar
libs/curios-forge-5.14.1+1.20.1.jar
```

然后使用本机 **Gradle 8.x** 构建（工程未内置 Gradle Wrapper）：

```bash
gradle build
```

产物：

- `build/libs/goety_supreme-<版本>.jar` —— 主模组

## 安装

把构建出的 jar 放进 `.minecraft/mods/`，并确保已安装上面列出的必需前置（Goety）。
客户端与服务端都需要安装。

## 许可

本项目采用 **MIT License**，详见 [LICENSE](LICENSE)。

第三方模组（Goety、Mutant Monsters、PuzzlesLib、Jade、jerotes 等）版权归各自作者所有，本项目与它们没有隶属关系。
