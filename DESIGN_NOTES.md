# DESIGN_NOTES —— 设计决策与数值来源记录

> 铁律 99：设计决策 / 用户口径 / 数值来源行号 / 为什么这么写，全部记在这里，不写进 .java。

## 无名凋灵 2026-11-02 全面优化（rev 302）

### 1. 冲锋前 0.25 秒球壳（无名魔杖球壳，不放大绿豆）
- 用户口径：球壳 = 无名魔杖定点渲染的球壳，不是放大绿豆；0.25 秒内由小变大包裹自身，成型后立刻冲锋。
- 实现：`NamelessWither.tickSkills` 前摇期间每 tick `NamelessSphereShell.spawnAt`（size = wrapRadius × 进度 1/5→5/5）。颜色 = 无名魔杖冲击波球壳同款 `ShockwaveSpell.java:28` `NAMELESS_COLOR = NecroVFXPalette.BRIGHT_HEX = 0x36dc0b`（激光的 0xA7FF3E 是用户另选的激光色，两者不同）。
- 前摇时长：`NamelessWitherConfig.SLAM_CHARGE_TICKS` 默认 20 → 5（0.25 秒）。
- 删除：绿豆 SHELL 放大动画触发（tickClientAnimations 与 setupAnim 中的 shellAnimationState）。

### 2. 冲锋严格对标湮灭凋灵（WitherServant）+ 删减动作
- 依据：`WitherServant` RAM_TOTAL=200 / 原 RAM_LOCK=170（蓄力 30 tick）/ RAM_DIVE_END=140 / RAM_RECOVER=100。
- 改动：三值改成实例字段（`ramLockTicks`/`ramDiveEndTicks`/`ramRecoverTicks`，默认保持 170/140/100 不变），无名凋灵构造器覆写为 195 / 195 / 155：蓄力 5 tick、松开滑行 0 tick、俯冲 40 tick（与原俯冲段等长）。
- 冲锋冲突修复：`customServerAiStep` 的站桩/冲锋分支抽成 `protected runCombatBranch()`，无名凋灵覆写「ramTicks>0 只跑 updateCharge」，避免站桩飞行与冲锋互相覆盖速度。
- `beginRamCharge`：`slamFired = false` 复位（修复第二次冲锋无冲击波/分身的历史 bug）；`ramCooldown = 160 + rand(120)`（照 WitherServant 原值，阻断收尾后立即连冲）。
- CHARGE 动画 2.875s → 2.25s（蓄力 0.25 + 俯冲 2.0），删掉 2.458/2.875 关键帧、各通道补 2.25 收尾回 0。用户口径：「根据实际冲锋时长来判断」。

### 3. 分身双阶段
- 用户口径：冲击波后本体左右各 1 个（水平 6 格、前后上下 ±2 格随机偏差）；延迟 1 秒后敌人旁 4 个（头顶 +3 格、水平 6 格、45° 菱形、轻微一起公转）。
- 实现：`NamelessClone.spawnSidePair`（yBodyRot 左右 ±90° × 6 格 + 前后上下 ±2 漂移）；`NamelessClone.spawnDiamond`（baseAngle = 45°+90°×slot）；公转 = 共同相位 `sin(gameTime × 0.02) × 0.35` 弧度摆动。
- 延迟：`NamelessWither.diamondSpawnDelay = 20`（tickSkills 递减，归零 spawnDiamond）。

### 4. 分身射击固定组合
- 用户口径：身边 2 分身每轮固定 1 无名束 + 1 凋零之首（随机谁射哪个）；4 分身每轮总共 2 束 + 2 首（随机分配）；跟随本体索敌。
- 实现：组代表（cloneSlot 0）每 VOLLEY_INTERVAL（20 tick）触发一轮；`fireSideRound` shuffle 2 个分身随机分配 1 束 1 首；`fireDiamondRound` shuffle [束,束,首,首] 随机分配。弹体复用 `ServantWitherSkull`（`ProjectileIntercept` 预判）与 `NamelessBeam`。

### 5. 分身音效（绿豆分身同款）
- 出现：`wind_blast` 1.4F/0.8F + `GreenSmithFx.necroFire()` 12 颗（`GreenSmithSpells:271-275`）。
- 消失：`lich_teleport_out` 1.0F/0.9F（`GreenSmithClones:511`）。音效名在 goety sounds.json 存在（7539 / 8511 行）。

### 6. 无名束落雷后绿火（绿豆领域同款）
- 依据：`AbstractGreenSmith.java:2502-2505`（skyFocus 落雷：necroFire 24 颗 + 5 团 `spawnGreenFireFor`，中心+四向偏移 {0,0},{1,0},{-1,0},{0,1},{0,-1}）。
- 实现：`NamelessBeam.spawnLightningTrap` 末尾照抄；owner 为 null 时跳过绿火（NPE 保护）。

### 7. 凋零之首共享射击间隔 0.5 秒
- 用户口径：任何两次凋零之首之间 ≥ 0.5 秒，主首副首共享。
- 实现：`WitherServant.sharedSkullGap()` 钩子（默认 0，凋灵仆从行为不变），无名凋灵覆写读 `HEAD_MIN_SPACING_TICKS`（默认 6 → 10）；`launchSkull` 后设 `sharedSkullCooldown`，递减统一放 `tick()`（各战斗分支都生效）；主首在 `tickHeadFire` 开头检查、侧头在 `fireSideHeads` 先检查共享再重置自身冷却。

### 8. 亮绿三层激光 + 初始偏移
- 用户口径（选择题选定）：0xA7FF3E 无名魔杖黄绿；照绿豆紫色激光三层做法；初始发射略微偏移。
- 贴图：原 `green_smith_laser_beam.png` 实测是白色（平均 (255,255,255,84)），紫色来自顶点系数——之前绿色顶点系数过暗所以「颜色没变化」。新生成 `nameless_laser_beam.png`（亮度保留、色调染 0xA7FF3E），渲染器无名凋灵分支用绿贴图 + 白顶点（照原版腐化 `CorruptedBeamRenderer` 白顶点+彩色贴图做法）。
- 偏移：照 `GreenSmithLaserSkill.java:39-43` + `AbstractGreenSmith.java:2185-2191`（aimBeside）：yaw ±(14~24)°（LASER_START_YAW_MIN/MAX）、pitch ±3°（LASER_START_PITCH_OFFSET），`NamelessWither.castCorruptedLaser` 发射前调用。

### 9. 击杀加血
- 用户口径：≤50 +2 / 51-100 +5 / >100 +10 / 小 boss +20 / boss +50 / 上限 500 / 每 50 血体型 +5% 上限 20%。
- 实现：新增 `AMBITION_MINIBOSS` 配置档（20），`growAmbition` 里 `isMiniBoss` 单独一档；后两条原有 `getScale`（`NamelessWither:766-768`）已符合，未动。

### 10. 凋零之首无药水云 + NecroticPool
- 用户口径：无名凋灵的凋零之首不生成药水云；击杀生物后原地留 NecroticPool（grave cube 死后同款）。
- 实现：`ServantWitherSkull.onHitBlock` owner 是 NamelessWither 时跳过 `spawnImpactCloud`；`healOwnerOnKill` 里 owner 是 NamelessWither 时按 `GraveCube.java:276-297` 生成 NecroticPool：setWarmupDelayTicks(0) / setDamage(攻击力×0.5) / setDuration(100) / setSoundEvent(SOUL_ORB_EXPLODE) / setWarmupColor+setColor(0x2f4e17 = POST_DEATH_COLOR，`GraveCube:54`)。

### 11. 跟踪丢失直线飞
- 依据：`GoetyScatterBombHomingMixin.java:38-48`（防空弹失去目标后保持速度）；NecroBolt 惯性 = 0.9（`NecroBolt.getInertia` = 0.9F + boltSpeed，boltSpeed 默认 0，`SpellHurtingProjectile:21`）。
- 实现：`NamelessBeam.steer` homingLost 分支补偿惯性（motion × 1/0.9），保持速度前飞。

### 12. 无名束射击音效
- 用户口径（选择题选定）：NecroBolt 原版发射音效。
- 依据：`SoundUtil.playNecroBolt`（上游 `SoundUtil.java:18-24`：SWING 0.75/0.6 + LICH_TELEPORT_OUT 1.0/1.0 + PROJECTILE_SPELL 1.0/0.8）。
- 实现：`NamelessBeam.create` 统一播（本体与分身所有发射点覆盖）。

### 13. 悬停高度 3~5 格
- 用户口径（选择题选定）：3~5 格。
- 实现：`WitherServant.hoverHeightOffset()` 钩子（默认 2.0+rand×4.0 不变），无名凋灵覆写 3.0+rand×2.0。

### 14. 绿豆回到胸口（附带修复）
- 之前改挂根骨骼时 y 合并算错：正确 = bone7.y(-22) + 原 bone23.y(7.4) = -14.6，误写 -11.6（偏高 3 像素）。`createBodyLayer` 与 `setupAnim.resetBone` 统一 -14.6；z 取 -4（身体前方）。

### 15. 碰撞箱错位（已修复，菈喵 2026-11-02 20:31 报告并批准）
- 用户报告 2026-11-02 20:31：「碰撞箱不对。。犯了刚刚才犯的。。。」；20:38 回「行」批准。
- 根因：`port_bb.py` root 分支 `24 - pv[1]` 对「凋灵」origin [0,-3,0] 生成的 bone0=(0,27,0) 是正确值；我 18:45 误判「下沉」改成 0，后经 0/3/32 反复，现为 3 → 模型 y -24~-3（脚底上方 27~48 像素 ×2.0 = 3.375~6 格），整体飘在 3.5 格碰撞箱上方。
- 对照（凋灵仆从，老模型认可状态）：`WitherServantModel` y 0~17（脚底上方 7~24 像素 ×2.0 = 0.875~3 格，在箱内）；MC 模型 y 正 = 向下、渲染原点 = 实体脚底、模型 y 值 N = 脚底上方 (24-N) 像素。
- 修复：`NamelessWitherModel` 两处——createBodyLayer bone0 PartPose y `3F→27F`、setupAnim `resetBone(this.namelessBone, 0.0F, 3.0F, ...)→27.0F`。头/身体/绿豆/动画均相对 bone0，整体下移、相对关系不变。

### 16. 实机反馈 5 条修复（rev 303，菈喵 2026-11-02 20:50 报告）
1. 球壳没出现：粒子球壳（SphereExplodeParticleOption）被否 → 用户方案「绿豆中心放一个特别小的无名魔杖的球」。实现：`NamelessWitherModel` bone23（绿豆）下加「球壳」4x4x4 cube，独立贴图 `nameless_shell.png`（16x16，0x36dc0b，alpha 160），CoreLayer 用 entityTranslucentEmissive 渲染；SHELL 动画目标「绿豆」→「球壳」，scale 1→10 @0.25s（觉醒无名者 staffhead 同款节奏）；平时 scale 1（小点），前摇 0.25s 由小变大包裹，之后消失。
2. 冲锋停留太久：`ramRecoverTicks` 155（7.75 秒）→ 15（0.75 秒回升）。
3. 冲击波半空就放：`ramHitInflateXZ/Y` 2.8/2.0 → 1.0/1.0（实例字段，WitherServant 默认不变）；`ramHitStopSqr` 9.0 → 1.0；`doChargeDive` 覆写——onGround/horizontalCollision 时 fireRamImpact（与撞目标共用）+ 收尾。
4. 激光拖尾仍紫：`CorruptedLaserTrailRenderer` 的 TRAIL/HEAD 顶点色按 owner 是 NamelessWither 换绿（拖尾 0.65/1.00/0.24、头 0.80/1.00/0.55，0xA7FF3E 色系；renderHead/headVertex 加颜色参数）。
5. 强化凋零之首：`WitherServant.enhancedSkullChance` private → protected（默认行为不变），`NamelessWither` 覆写返回 0。

### 17. 飞行动画结束段延长（rev 304，菈喵 2026-11-02 21:00 要求，子代理 cc598db7 实施）
- 用户口径：「适当延长飞行动作的结束段，第二秒到第 2.25 秒这段，作者做的飞行停止太快、不连贯」。
- 改动：`NamelessWitherAnimations.build_fly` withLength 2.25→3.0；「除了头」62.5° 保持到 2.25s、2.25→3.0s 回 0（结束段 0.2s→0.75s）；「披风」「绿豆」同样末段 2.25s 保持、3.0s 回 0；POSITION 通道全不动。

### 18. 实机反馈 5 条修复（rev 305，菈喵 2026-11-02 21:13 报告）
1. 球壳 = 无名魔杖法杖头的真球：照 `NamelessStaffModel.java:47-51` 三层球（center/mid/outer，CubeDeformation -1/-3.75/-5）；贴图引用 goety `textures/item/nameless_staff_orb.png / _mid / _center`；渲染照 `CustomItemsRenderer.java:65-78`（outer/mid 用 RenderType.eyes、center 用 ModRenderType.orbCenter）；texOffs 换算 128 空间 = 32 空间 ×4 → (64,0)/(64,32)/(64,64)。
2. 绿豆飘到头上：bone0 改 27 后身体走 root.render（含 27），但 CoreLayer.renderCore 没补 → 绿豆比身体高 27 像素。renderCore 补 translate(0,27,0)、renderShell* 补 translate(0.9,12.4,-4)（绿豆绝对位）。
3. side 分身等高跟随：`CloneState` 加 offX/offZ，spawnSidePair 保存相对偏移（y 与本体等高），tick kind==0 每 tick `本体位置+偏移`。
4. 冲击波补特效（菈喵选择题：三项全补→后撤销②③；保留 bombSoul）：① `SoulSummonVFX.emitter(CUBE_SPAWN)`（`ShockwaveFocus.java:46`）；②聚气 `ServerParticleUtil.gatheringParticles(ParticleTypes.SOUL, this, server)`（`AbstractSoulReaper.java:898` 同款）——菈喵 21:27 更正：撤销爆前聚气与二段爆，聚气改到 `castWitherSlam`（冲锋前摇球壳放大时）。
5. 头自转：三个头骨骼 pivot 移到各自 cube 中心（left_head (-8,-23,0)/right_head (10,-23,0)/main_head (1,-26,1)，cube 改中心对称 addBox），王冠 offset 同步校正（cube5/6 (-2,0,-2)、cube4 (-3,0,-3)）。

### 19. 激光紫色残留修复（rev 305，菈喵 2026-11-02 21:22 报告「还他妈是紫色」）
- 实测贴图像素：trail.png（goety）白色 (255,255,255,135)；`laser_glow.png`（我们）紫色 (167,59,215,66) ← 头光球紫色元凶；gs_beam.png 白色；原版腐化 beacon_glow 紫 (128,0,255)/main 白/core 暗紫（颜色来自贴图）。
- 修复：生成 `nameless_laser_glow.png`（亮度保留、染 0xA7FF3E，实测 140,215,51），`CorruptedLaserTrailRenderer` nameless 分支 HEAD_TEXTURE 切换。
- 残留第二处（本轮补）：`laser_spark.png` 实测白色（无问题）、`laser_glow.png` 紫色被 `CORRUPTED_LASER_GLOW` 粒子（撞击溅射光点）引用。新增 `NAMELESS_LASER_GLOW` 粒子（GeneratedMod 注册 + particles/nameless_laser_glow.json + ClientModEvents GlowProvider），`ClientCorruptedLaserParticles.spawnImpactSplash` 按 owner 切换绿/紫。绿豆激光的紫光球/紫光点/紫顶点全部未动。






### 附带修复
- `slamFired` 复位（第二次冲锋冲击波/分身失效的历史 bug）。
- 侧头共享冷却检查顺序（先查共享再重置自身冷却，避免白等一轮）。
