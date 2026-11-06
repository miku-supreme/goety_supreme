package cn.blockforge.generated.goetysupreme.spell;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.SpellType;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.config.EndermanHandFocusConfig;
import cn.blockforge.generated.goetysupreme.entity.EnderHandHeldBlockEntity;
import cn.blockforge.generated.goetysupreme.block.EndermanHandCarriedBlock;
import cn.blockforge.generated.goetysupreme.spell.EndermanHandFocusSpell;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;

public final class EndermanHandFocusSpell extends Spell {
    public static final String HELD_BLOCK_TAG = "GoetySupremeHeldBlock";
    private static final String LAST_TELEPORT_TICK = "GoetySupremeLastTeleportTick";

    private static final double TELEPORT_RANGE = 512.0D;

    @Override
    public int defaultSoulCost() {
        return EndermanHandFocusConfig.SOUL_COST.get();
    }

    @Override
    public int defaultCastDuration() {
        return 0;
    }

    @Override
    public int defaultSpellCooldown() {
        return 0;
    }

    public static boolean teleportReadyFor(LivingEntity caster) {
        if (caster == null) {
            return false;
        }
        long now = caster.level().getGameTime();
        return now - caster.getPersistentData().getLong(LAST_TELEPORT_TICK)
                >= EndermanHandFocusConfig.TELEPORT_COOLDOWN.get();
    }

    @Override
    public SpellType getSpellType() {
        return SpellType.VOID;
    }

    @Override
    public List<Enchantment> acceptedEnchantments() {
        List<Enchantment> enchantments = new ArrayList<>();
        enchantments.add(ModEnchantments.POTENCY.get());
        return enchantments;
    }

    @Override
    public void SpellResult(ServerLevel level, LivingEntity caster, ItemStack wandStack, SpellStat stats) {
        if (caster instanceof Player && !caster.isShiftKeyDown()) {
            toggleCarriedBlock(level, caster, wandStack);
        } else {
            teleportToCrosshair(level, caster);
        }
    }

    private void teleportToCrosshair(ServerLevel level, LivingEntity caster) {
        long now = level.getGameTime();
        long last = caster.getPersistentData().getLong(LAST_TELEPORT_TICK);
        if (now - last < EndermanHandFocusConfig.TELEPORT_COOLDOWN.get()) {
            return;
        }
        Vec3 start = caster.getEyePosition();
        Vec3 look = caster.getLookAngle();
        Vec3 end = start.add(look.scale(TELEPORT_RANGE));
        HitResult hit = level.clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, caster));
        BlockPos target = hit.getType() == HitResult.Type.BLOCK
                ? ((BlockHitResult) hit).getBlockPos()
                : level.getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING, caster.blockPosition());
        BlockPos safe = findSafeSpot(level, target);
        if (safe == null) {
            return;
        }
        Vec3 from = caster.position();
        level.playSound(null, from.x, from.y + 0.5D, from.z,
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        caster.teleportTo(safe.getX() + 0.5D, safe.getY(), safe.getZ() + 0.5D);
        if (caster instanceof ServerPlayer serverPlayer) {
            level.playSound(serverPlayer, caster.getX(), caster.getY(0.5D), caster.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
            serverPlayer.playNotifySound(SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        } else {
            level.playSound(null, caster.getX(), caster.getY(0.5D), caster.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        level.sendParticles(ParticleTypes.PORTAL, caster.getX(), caster.getY(0.5D), caster.getZ(),
                32, 0.4D, 0.4D, 0.4D, 0.2D);
        caster.getPersistentData().putLong(LAST_TELEPORT_TICK, now);
    }

    private static BlockPos findSafeSpot(ServerLevel level, BlockPos origin) {
        BlockPos start = origin.getY() <= level.getMinBuildHeight() ? origin : origin;
        for (int dY = 0; dY <= 8; dY++) {
            BlockPos candidate = start.above(dY);
            if (isSafe(level, candidate)) {
                return candidate;
            }
        }
        for (int dY = 0; dY <= 16; dY++) {
            BlockPos candidate = start.above(dY);
            if (level.isEmptyBlock(candidate) && level.getBlockState(candidate.below()).isSolid()) {
                return candidate;
            }
        }
        return null;
    }

    private static boolean isSafe(ServerLevel level, BlockPos pos) {
        return level.isEmptyBlock(pos) && level.isEmptyBlock(pos.above())
                && level.getBlockState(pos.below()).isSolid();
    }

    private void toggleCarriedBlock(ServerLevel level, LivingEntity caster, ItemStack wandStack) {
        if (!(caster instanceof Player player)) {
            return;
        }
        CompoundTag data = player.getPersistentData();
        if (data.contains(HELD_BLOCK_TAG)) {
            throwCarriedBlock(level, player, wandStack, data);
        } else {
            pickUpBlock(level, player, data);
        }
    }

    private void pickUpBlock(ServerLevel level, Player player, CompoundTag data) {
        Vec3 start = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.scale(EndermanHandFocusConfig.PICK_RANGE.get()));
        HitResult hit = level.clip(new ClipContext(start, end,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (hit.getType() != HitResult.Type.BLOCK) {
            return;
        }
        BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || state.getDestroySpeed(level, pos) < 0.0F || state.is(Blocks.BEDROCK)) {
            return;
        }
        CompoundTag block = NbtUtils.writeBlockState(state);
        data.put(HELD_BLOCK_TAG, block);
        data.putLong(EndermanHandCarriedBlock.HELD_POS_TAG, pos.asLong());
        level.removeBlock(pos, false);
        EndermanHandCarriedBlock.spawn(level, player, state, pos);
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 0.8F,
                0.7F + player.getRandom().nextFloat() * 0.6F);
        level.sendParticles(ParticleTypes.PORTAL, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                24, 0.3D, 0.3D, 0.3D, 0.2D);
    }

    private void throwCarriedBlock(ServerLevel level, Player player, ItemStack wandStack, CompoundTag data) {
        BlockState state = NbtUtils.readBlockState(level.holderLookup(net.minecraft.core.registries.Registries.BLOCK),
                data.getCompound(HELD_BLOCK_TAG));
        data.remove(HELD_BLOCK_TAG);
        data.remove(EndermanHandCarriedBlock.HELD_POS_TAG);
        if (state.isAir()) {
            return;
        }
        int potency = focusEnchantmentLevel(wandStack, ModEnchantments.POTENCY.get());
        ThrowableBlock block = EndermanHandCarriedBlock.takeForThrow(level, player);
        boolean fresh = block == null;
        if (fresh) {
            Vec3 spawn = player.getEyePosition().add(player.getLookAngle().scale(0.6D));
            ThrowableBlock template = new ThrowableBlock(player, state, BlockPos.containing(spawn));
            CompoundTag tag = new CompoundTag();
            template.saveWithoutId(tag);
            EnderHandHeldBlockEntity freshBlock = new EnderHandHeldBlockEntity(
                    ModEntityTypes.ENDER_HAND_HELD_BLOCK.get(), level);
            freshBlock.load(tag);
            freshBlock.setUUID(java.util.UUID.randomUUID());
            freshBlock.setOwner(player);
            freshBlock.setPos(spawn.x, spawn.y, spawn.z);
            freshBlock.releaseForThrow();
            block = freshBlock;
        }
        block.getPersistentData().putInt("GoetySupremePotency", potency);
        if (!wandStack.isEmpty() && wandStack.is(ModItems.VOID_STAFF.get())) {
            block.getPersistentData().putBoolean("GoetySupremeVoidEclipse", true);
        }
        Vec3 look = player.getLookAngle();
        LivingEntity target = getTarget(player, 24);
        if (target != null && target != player && target.isAlive() && !target.isAlliedTo(player)) {
            double dx = target.getX() - block.getX();
            double dy = target.getY(0.33D) - block.getY();
            double dz = target.getZ() - block.getZ();
            double horizontal = Math.sqrt(dx * dx + dz * dz);
            block.shoot(dx, dy + horizontal * 0.1D, dz, 1.4F, 0.0F);
        } else {
            block.setDeltaMovement(look.scale(1.35D));
        }
        if (fresh) {
            level.addFreshEntity(block);
        }
        level.playSound(null, player.getX(), player.getY(0.7D), player.getZ(),
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.6F, 1.4F);
        level.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY(1.0D), player.getZ(),
                12, 0.3D, 0.3D, 0.3D, 0.2D);
    }

    private int focusEnchantmentLevel(ItemStack wandStack, Enchantment enchantment) {
        if (wandStack == null || wandStack.isEmpty() || !(wandStack.getItem() instanceof IWand)
                || enchantment == null) {
            return 0;
        }
        ItemStack focus = IWand.getFocus(wandStack);
        return focus == null || focus.isEmpty() ? 0 : focus.getEnchantmentLevel(enchantment);
    }

    public static int potencyOf(fuzs.mutantmonsters.world.entity.projectile.ThrowableBlock block) {
        return block.getPersistentData().getInt("GoetySupremePotency");
    }
}
