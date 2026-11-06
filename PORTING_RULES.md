# Goety 跨模组仆从实现规范（必须遵守）

本文件是硬性约定。做任何“把 DNL / 其他模组生物做成 Goety 仆从”的功能前，先读这里。

## 实施前必须声明的清单（硬性）

动手前逐条自查，任何一条是“简化/替代/近似”，都必须先停下来说明并得到确认：

1. 是不是用了 proxy / fake entity / 第二只视觉实体？
2. 是不是把目标模组的 goal / 状态机 / 动画写成了“简化版”？
3. 是不是改了原版的随机数、时长、阈值、优先级、owner 顺序？
4. 是不是用字符串类名/反射替代直接调用，且没有说明版本边界？
5. 是不是只在“能编译”后就当完成，没有实机验证？

## 完成后必须做的 diff

- **一切移植逐行 1:1，100% 一致**（用户 2026-10-26 硬性口径）：Java 逻辑、**粒子**（尺寸 / 寿命 / 重力 / 渲染类型 / 贴图 / sprite 集）、渲染器（RenderType / 贴图 / 顶点写法 / 全部常量）、音效、动画、NBT，全部照上游源码逐行对照，禁止「差不多」「等效实现」「我觉得更好」。
- 目标模组的 goal / 状态机 / 动画：把反编译源码放旁边，逐方法对照，禁止凭印象。
- 数值、随机范围、优先级、owner 顺序：以反编译字节码为准，不用“差不多”。
- 每次交付前，把“与目标模组有差异的地方”逐条写出来；没有差异也要说明“已逐行对照”。

## 目标模组 goal / 状态机 移植标准（硬性）

目标模组的 goal 类不是孤立的，必须连同它依赖的实体基础设施一起照搬，禁止只搬 goal 类本身：

- 实体状态枚举 + `isState()/setState()`
- `trigger*Animation()` 等真正驱动动画状态机的方法
- `onSyncedDataUpdated()` 里对动画数据的客户端启停处理
- 自定义 `MoveControl` / `LookControl` / `PathNavigation` / `NodeEvaluator`
- `getFollowDistance()`、音效封装等 goal 里引用的辅助方法

只搬 goal、不搬支撑 = 必然出现“不攻击/不索敌/动画错位/走路消失”，一律判为不合格。

## 沟通规则（硬性）

- 不允许为了省事、省时间、省 token 擅自采用降级方案。
- 任何代理渲染、假实体、双实体、隐藏模型、复制源码改成简化版等方案，**必须先说明**，得到用户明确同意后再做。
- 不能先做再解释，不能把“能编译”当成“已完成”。
- 用户要求严格对标的，必须按原版源码/字节码逐项核对，不能凭印象或比例猜。

## 软依赖规则（硬性）

- 可选模组的类**不得出现在 common 事件/注册类的 `instanceof`、方法签名、字段类型、静态引用里**；否则对方缺席时会在事件触发或类加载阶段 `NoClassDefFoundError`。
- 可选实体判断优先用**类名字符串 / 注册名**，或把整段逻辑隔离到只在 `ModList.isLoaded(...)` 后才 `Class.forName` 的独立类中。
- 绝不能只靠“调用前有个 if”来保证安全；类加载/验证可能发生在 if 之前。
- 新增任何软依赖后，必须分别在**装了/没装**该模组的实例各验证一次加载和事件路径。

## 召唤机制规则（硬性）

- 召唤/生成仆从的机制、顺序、owner 设置时机，一律以 Goety 原版流程为准，禁止自创顺序。
- 关键顺序：**先 `setOwnerId` / `setOwnerClientId`，再 `finalizeSpawn`**。装备、戒指等判定发生在 `finalizeSpawn` 里，owner 必须在之前就绪。
- 熔铸不渝之戒沿 `getTrueOwner()` 主人链生效；淬炼之戒只认直接玩家 owner。不得为了戒指反过来改 owner。

## 正确模式

1. 仆从实体**必须继承 Goety 的 `Summoned`**（或 Goety 对应的 `Owned`/`AnimalSummon` 仆从基类）。
2. 生物模型：**复制目标模组的模型类**，把泛型宿主改成我们自己的仆从类。
3. 渲染器：`MobRenderer<我们的仆从, 复制的模型>`，注册到我们自己的 EntityType。
4. 笼内预览、特殊 layer：同样复制，泛型宿主改成我们的仆从。
5. 动画状态直接放在我们的仆从实体上，由复制模型消费；不要在渲染器里另造一套状态机。
6. 数值：按目标模组 `createAttributes()` 的**实际字段**抄；注意 SRG 映射：
   - `f_22276_` MAX_HEALTH
   - `f_22277_` FOLLOW_RANGE
   - `f_22278_` KNOCKBACK_RESISTANCE
   - `f_22279_` MOVEMENT_SPEED
   - `f_22281_` ATTACK_DAMAGE
   - `f_22282_` ATTACK_KNOCKBACK（不是 ARMOR）
   - `f_22284_` ARMOR
7. 动画时长、命中帧、召唤间隔等按目标模组原值抄，不凭感觉改。

## 文件写入与编码规范（硬性，2026-10-26 新增）

- **禁止用 PowerShell 内联中文写文件**：`Set-Content` / `Out-File` / `>` 重定向在中文 Windows 上会按 GBK 落盘，把 UTF-8 文件写成 `鐢ㄦ埛` 这类乱码（`SecondRounderSummonSpell.java` 就是这么坏的）。
- 写 / 改含中文的文件，**一律用 write / edit 工具**（UTF-8 直写）。
- 必须用脚本处理中文时：先把内容写到 UTF-8 临时文件，脚本只读该文件，输出统一用
  `[System.IO.File]::WriteAllText($path, $text, (New-Object System.Text.UTF8Encoding($false)))`。
- 交付前扫乱码：`Select-String -Pattern '鐢|鈥|閮|鍙|鍦|杩|娴|鏄|娓|锛|銆|绋|杞|鍔|鑳|绂|鍚|鍛'`；命中就要重写该文件。
- 注意：PowerShell 的 `Get-Content` 读 UTF-8 文件也会显示乱码，**判断是否真坏要先用 read 工具看**，别拿 PS 的输出当依据。


## 禁止事项

- ❌ 客户端 `new` 一个目标实体、不加入世界，只喂给 renderer 的“假实体”。
- ❌ 世界里额外生成一只真实目标实体作为“视觉载体”，再让 Summoned 不渲染。
- ❌ `setInvisible(true)` 遮盖重复模型。
- ❌ no-op renderer 遮盖本体模型。
- ❌ 自己用爆炸/暴击粒子替代目标模组特效。
- ❌ 在渲染器每帧 `stop()` / `startIfStopped()` 重置动画。
- ❌ 把 `ATTACK_KNOCKBACK` 当成 `ARMOR`。

## 已确认的参考实现

- Goety 本体：`ZombieServant extends Summoned` + 自有 model/renderer。
- 觉醒：`CreeperServant extends AbstractCreeperServant` + `CreeperServantModel`。
- Ominous：`AtlatitanServant extends AnimalSummon` + 复制的 `ModelAtlatitanServant`。
- Supreme：同样 `Summoned` + 自有 model/renderer。

## DNL 载笼怪关键值

- 动画总长 `1.3s`；命中 `0.75s = 15 tick`；范围 `3.5`；击退 `1.1`。
- 属性：100 HP / 15 ATK / 1.25 ATTACK_KNOCKBACK / 0.30 speed / 0.5 KB resist / 32 follow / 0 armor。
- 召唤：200 tick 充能；每次最多 3 只；单载笼怪上限 8（用户指定）。
- 默认池：zombie / skeleton / spider / cave_spider（无 creeper）。
- 召唤物 owner：载笼怪自己。

## DNL 弩炮关键值

- 100 HP / 19 ATK / 1.0 ATTACK_KNOCKBACK / 0.35 speed / 1.0 KB resist / 32 follow / **15 armor**。
- 箭袋 6；装填 60 tick。
