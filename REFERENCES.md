# 上游源码地址（优先看源码，少反编译）

用户提供的官方源码仓库，之后排查/移植尽量直接读源码，避免 CFR 反编译（更清晰、无混淆、有注释）：

- **Goety（诡厄巫法）**：https://github.com/Polarice3/Goety-2
- **Jerotes / J 村**：https://github.com/Jerotes/JerotesVillage1.20.1
  - **本地已下载**：`ref/JerotesVillage1.20.1-main/`（main 分支 zip，1772 个 .java）
  - 关键路径：`src/main/java/com/jerotes/jerotesvillage/`
- **Jerotes 核心库**（`jerotes` jar 的源码）：https://github.com/Jerotes/JerotesWarehouse
- **The Engender Mod（召唤师）**：https://github.com/Mrbt0907/The-Engender-Mod
  - 本地已有：`ref/engender-src/src`

## 其它联动 mod（默认也「搬对方」）

以下 mod 的内容同样按「直接搬 / 调对方本体」处理；有公开源码的优先源码，没有的允许反编译并按需注明版本：

| mod | 本地 jar | 源码 |
|---|---|---|
| Goety | `libs/goety-2.5.59.2.jar` | https://github.com/Polarice3/Goety-2 ；本地 `ref/Goety-2-1.20` |
| Goety: Ominous（凶兆） | `goetyominous-*.jar` | **完整源码本地已有：`mod/ref/goetyominus-src/Goety-Ominous-master/`（3.1.2-1.20.1，2026-11-01）**；旧反编译 `mod/ref/goetyominus/dec`；GitHub 地址未确认 |
| Goety: Awaken（觉醒） | `goetyawaken-*.jar` | 反编译（`.tmp/awaken-dec`；`mod/ref/goetyawaken` 为旧反编译，无公开源码引用） |
| Jerotes 核心 | `libs/jerotes-1.20.1-1.3.4.jar` | https://github.com/Jerotes/JerotesWarehouse |
| JerotesVillage（J 村） | `libs/jerotesvillage-1.20.1-1.1.6.jar` | 本地源码 `ref/JerotesVillage1.20.1-main/` |
| 召唤师 Engender | — | `ref/engender-src/src`（GitHub https://github.com/Mrbt0907/The-Engender-Mod） |
| 灾变 Cataclysm / 突变生物 Mutant Monsters / DNL | `libs/*.jar` | 无公开源码时反编译（灾变本体；Goety Cataclysm 源码在 `ref/Goety_Cataclysm-master`） |

## 凶兆动画系统（2026-11-03 记录，参考其解决「姿势切换/闪绑定姿势」的思路）

- `client/render/model/animation/PoseBlend.java`（26 行）：`state.updateTime(ageInTicks, speed)` 后用
  `state.ifStarted(anim -> KeyframeAnimations.animate(model, definition, anim.getAccumulatedTime(), weight, VECTOR_CACHE))`
  —— **原版 `KeyframeAnimations.animate` 自带 weight 参数做姿势加权混合**，不用自写 pose 缓冲。
- `client/render/model/animation/PoseTransition.java`（31 行）：取目标 `AnimationDefinition` 每个通道第 0 帧值，
  `build(pose, length, intoPose)` 自动生成「0 → 该姿势」或「该姿势 → 0」的 LINEAR 过渡动画——进入/离开姿势时平滑过渡，避免闪绑定姿势。
- 使用方：`client/render/model/CerberusModel.java`、`WargModel.java`（各 9 处 `PoseBlend.animate`，按权重叠加多个动作）。

## 约定

- 涉及 Goety / J 村 / 召唤师 的类、数值、招式、法术时，**先去源码仓库/本地源码找对应 `.java`**；
- 只有源码里找不到（旧版本、未开源）或必须核对**已编译 jar 的注册名/字节码**时，才用 CFR 反编译 `libs/*.jar`；
- 本仓库里已有的反编译产物 `ref/jvillage/dec`、`ref/jerotes133/dec`、`.tools/*` 是历史遗留，只作交叉参考，**不作为新结论的依据**。
## 用户提供的联动物 jar（2026-10-29）
路径前缀：`C:\Users\Administrator\Downloads\喵\面包服一周目客户端_1.20.1Fabric\.minecraft\versions\1.20.1-Forge_47.4.0\mods\`

- `[暮色森林] twilightforest-1.20.1-4.3.2508-universal.jar`
- `[灾变] L_Enders_Cataclysm-3.31.jar`
- `[灾厄侵扰] IllagerInvasion-v8.0.7-1.20.1-Forge.jar`
- `[诡厄暮色] GoetyTwilight-1.20.1Forge-1.2.0.jar`
- `[诡厄灾变] goety_cataclysm-1.20-1.9.2.jar`

需要联动以上模组时，先看它们的语言文件/源码，再决定软引用方式。
