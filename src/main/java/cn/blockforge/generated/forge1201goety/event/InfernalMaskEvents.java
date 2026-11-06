package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.IfritClone;
import cn.blockforge.generated.goetysupreme.event.InfernalMaskEvents;
import cn.blockforge.generated.goetysupreme.util.MinionGroundPlacement;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.item.InfernalMaskItem;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class InfernalMaskEvents {

    private static final String CLONE_CD = "InfernalMaskCloneCd";
    private static final String DODGE_CD = "InfernalMaskDodgeCd";
    private static final String DODGE_LAST = "InfernalMaskDodgeLast";
    private static final int CLONE_COOLDOWN = 3600;
    private static final int DODGE_SHORT = 100;
    private static final int DODGE_LONG = 200;

    private InfernalMaskEvents() {
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.player instanceof ServerPlayer player)) {
            return;
        }
        var data = player.getPersistentData();
        int cloneCd = data.getInt(CLONE_CD);
        if (cloneCd > 0) {
            data.putInt(CLONE_CD, cloneCd - 1);
        }
        int dodgeCd = data.getInt(DODGE_CD);
        if (dodgeCd > 0) {
            data.putInt(DODGE_CD, dodgeCd - 1);
        }
        if (player.tickCount % 20 == 0 && !InfernalMaskItem.isWorn(player)) {
            IfritClone.discardFor(player);
        }
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            IfritClone.discardFor(player);
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getSlot() == net.minecraft.world.entity.EquipmentSlot.HEAD
                && event.getEntity() instanceof ServerPlayer player
                && !InfernalMaskItem.isWorn(player)) {
            IfritClone.discardFor(player);
        }
    }

    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (InfernalMaskItem.isWorn(player) && event.getSource().is(DamageTypeTags.IS_FIRE)) {
            com.mojang.logging.LogUtils.getLogger().info(
                    "[infernal_mask] fire damage canceled: {}", event.getSource().getMsgId());
            event.setCanceled(true);
        }
        if (!player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD)
                .is(GeneratedMod.INFERNAL_MASK.get())) {
            top.theillusivec4.curios.api.CuriosApi.getCuriosInventory(player).ifPresent(handler ->
                    handler.findFirstCurio(stack -> stack.is(GeneratedMod.INFERNAL_MASK.get()))
                            .ifPresent(result -> result.stack().hurtAndBreak(1, player,
                                    p -> p.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.HEAD))));
        }
    }

    public static void onAction(ServerPlayer player, int action, int direction) {
        com.mojang.logging.LogUtils.getLogger().info(
                "[infernal_mask] packet player={} action={} dir={} worn={} head={}",
                player.getGameProfile().getName(), action, direction,
                InfernalMaskItem.isWorn(player),
                player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD));
        if (action == 0) {
            summon(player);
        } else if (action == 1) {
            dodge(player, direction);
        }
    }

    private static void summon(ServerPlayer player) {
        if (!(player.level() instanceof ServerLevel level)) {
            return;
        }
        if (!InfernalMaskItem.isWorn(player)) {
            com.mojang.logging.LogUtils.getLogger().info("[infernal_mask] summon rejected: not worn");
            return;
        }
        var data = player.getPersistentData();
        if (data.getInt(CLONE_CD) > 0) {
            com.mojang.logging.LogUtils.getLogger().info(
                    "[infernal_mask] summon rejected: cooldown={}", data.getInt(CLONE_CD));
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable(
                    "message.goety_supreme.infernal_mask.cooldown"), true);
            return;
        }
        List<IfritClone> existing = level.getEntitiesOfClass(IfritClone.class,
                player.getBoundingBox().inflate(128.0D),
                clone -> player.getUUID().equals(clone.getMaskOwner() == null ? null : clone.getMaskOwner().getUUID()));
        if (!existing.isEmpty()) {
            com.mojang.logging.LogUtils.getLogger().info(
                    "[infernal_mask] summon rejected: clone already exists ({})", existing.size());
            player.displayClientMessage(net.minecraft.network.chat.Component.translatable(
                    "message.goety_supreme.infernal_mask.exists"), true);
            return;
        }
        IfritClone clone = new IfritClone(ModEntityTypes.IFRIT_CLONE.get(), level);
        clone.setMaskOwner(player);
        double sx = player.getX() + player.getLookAngle().z * 1.5D;
        double sz = player.getZ() - player.getLookAngle().x * 1.5D;
        double sy = MinionGroundPlacement.groundFeetY(level, sx, sz, player.getY(),
                MinionGroundPlacement.SKELETON_CLEARANCE);
        clone.moveTo(sx, sy, sz, player.getYRot(), 0.0F);
        boolean added = level.addFreshEntity(clone);
        com.mojang.logging.LogUtils.getLogger().info(
                "[infernal_mask] addFreshEntity={} pos={},{},{} alive={} removed={}",
                added, sx, sy, sz, clone.isAlive(), clone.isRemoved());
        if (added) {
            com.Polarice3.Goety.utils.ServerParticleUtil.sendGodRay(level, sx, sy, sz,
                    new com.Polarice3.Goety.utils.ColorUtil(0x8FE6DF));
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL,
                    sx, sy + 1.0D, sz, 24, 0.5D, 0.8D, 0.5D, 0.02D);
            level.sendParticles(net.minecraft.core.particles.ParticleTypes.LARGE_SMOKE,
                    sx, sy + 0.8D, sz, 16, 0.4D, 0.6D, 0.4D, 0.01D);
            data.putInt(CLONE_CD, CLONE_COOLDOWN);
        }
    }

    private static void dodge(ServerPlayer player, int direction) {
        if (!InfernalMaskItem.isWorn(player)) {
            com.mojang.logging.LogUtils.getLogger().info("[infernal_mask] dodge rejected: not worn");
            return;
        }
        var data = player.getPersistentData();
        if (data.getInt(DODGE_CD) > 0) {
            com.mojang.logging.LogUtils.getLogger().info(
                    "[infernal_mask] dodge rejected: cooldown={}", data.getInt(DODGE_CD));
            return;
        }
        com.mojang.logging.LogUtils.getLogger().info(
                "[infernal_mask] dodge accepted dir={}", direction);
        long now = player.level().getGameTime();
        long last = data.getLong(DODGE_LAST);
        boolean chained = now - last <= 300L && last > 0;
        data.putLong(DODGE_LAST, now);
        data.putInt(DODGE_CD, chained ? DODGE_LONG : DODGE_SHORT);

        double yaw = Math.toRadians(player.getYRot());
        double sign = direction == 0 ? -1.0D : 1.0D;
        double sideX = -Math.cos(yaw) * sign;
        double sideZ = -Math.sin(yaw) * sign;
        double fromX = player.getX();
        double fromZ = player.getZ();
        player.push(sideX * 1.4D, 0.0D, sideZ * 1.4D);
        player.setDeltaMovement(sideX * 1.4D, 0.0D, sideZ * 1.4D);
        player.invulnerableTime = 20;
        player.hasImpulse = true;
        player.hurtMarked = true;
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                net.minecraft.sounds.SoundEvents.PLAYER_ATTACK_CRIT,
                net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);
        com.mojang.logging.LogUtils.getLogger().info(
                "[infernal_mask] dodge moved from={},{} to={},{} vel={},{}",
                fromX, fromZ, player.getX(), player.getZ(),
                player.getDeltaMovement().x, player.getDeltaMovement().z);
        if (player.level() instanceof ServerLevel server) {
            for (int i = 1; i <= 8; i++) {
                server.sendParticles(net.minecraft.core.particles.ParticleTypes.SOUL_FIRE_FLAME,
                        player.getX() - sideX * i * 0.35D, player.getY() + 0.9D,
                        player.getZ() - sideZ * i * 0.35D, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
