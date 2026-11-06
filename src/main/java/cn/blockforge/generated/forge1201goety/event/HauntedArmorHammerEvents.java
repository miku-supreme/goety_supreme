package cn.blockforge.generated.goetysupreme.event;

import com.Polarice3.Goety.client.particles.SmashParticleOption;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.ally.undead.HauntedArmorServant;
import com.Polarice3.Goety.common.items.equipment.HammerItem;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.ColorUtil;
import com.Polarice3.Goety.utils.MobUtil;
import com.Polarice3.Goety.utils.ServerParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.event.FellHammerEvents;
import cn.blockforge.generated.goetysupreme.item.FellHammerItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.HauntedArmorHammerEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class HauntedArmorHammerEvents {

    private static final float SWEEP_RADIUS = 1.75F;

    private static final double SWEEP_KNOCKBACK = 0.4D;

    private static final ThreadLocal<Boolean> SWEEPING = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private HauntedArmorHammerEvents() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (SWEEPING.get()) {
            return;
        }
        DamageSource source = event.getSource();
        if (!(source.getDirectEntity() instanceof HauntedArmorServant servant)) {
            return;
        }
        if (servant.level().isClientSide() || !servant.isAlive()) {
            return;
        }
        ItemStack weapon = servant.getMainHandItem();
        if (weapon.isEmpty() || !(weapon.getItem() instanceof HammerItem)) {
            return;
        }
        LivingEntity victim = event.getEntity();
        if (!victim.isAlive() || event.getAmount() <= 0.0F) {
            return;
        }
        SWEEPING.set(Boolean.TRUE);
        try {
            sweepAround(servant, weapon, victim);
        } finally {
            SWEEPING.set(Boolean.FALSE);
        }
    }

    private static void sweepAround(HauntedArmorServant servant, ItemStack weapon, LivingEntity victim) {
        Level level = servant.level();
        float radius = SWEEP_RADIUS + weapon.getEnchantmentLevel(ModEnchantments.RADIUS.get());
        float damage = (float) servant.getAttributeValue(Attributes.ATTACK_DAMAGE)
                + EnchantmentHelper.getDamageBonus(weapon, victim.getMobType());
        int fire = EnchantmentHelper.getFireAspect(servant);
        AABB range = victim.getBoundingBox().inflate(radius, 0.25D, radius);
        DamageSource source = servant.damageSources().mobAttack(servant);
        for (LivingEntity nearby : level.getEntitiesOfClass(LivingEntity.class, range)) {
            if (nearby == servant || nearby == victim || !nearby.isAlive()) {
                continue;
            }
            if (MobUtil.areAllies(servant, nearby) || nearby.isAlliedTo(servant)) {
                continue;
            }
            if (nearby instanceof ArmorStand stand && stand.isMarker()) {
                continue;
            }
            if (nearby.getVehicle() == servant) {
                continue;
            }
            nearby.knockback(SWEEP_KNOCKBACK,
                    Mth.cos(servant.getYRot() * ((float) Math.PI / 180F)),
                    -Mth.sin(servant.getYRot() * ((float) Math.PI / 180F)));
            if (nearby.hurt(source, damage)) {
                if (fire > 0) {
                    nearby.setSecondsOnFire(fire * 4);
                }
                EnchantmentHelper.doPostHurtEffects(nearby, servant);
                EnchantmentHelper.doPostDamageEffects(servant, nearby);
            }
        }
        level.playSound(null, servant.getX(), servant.getY(), servant.getZ(),
                ModSounds.HAMMER_IMPACT.get(), servant.getSoundSource(), 1.0F, 1.0F);
        level.playSound(null, servant.getX(), servant.getY(), servant.getZ(),
                ModSounds.HAMMER_SWING.get(), servant.getSoundSource(), 1.0F, 1.0F);
        if (victim.onGround()) {
            level.playSound(null, victim.getX(), victim.getY(), victim.getZ(),
                    ModSounds.DIRT_DEBRIS.get(), servant.getSoundSource(), 1.0F, 1.0F);
        }
        if (level instanceof ServerLevel server) {
            BlockPos groundPos = BlockPos.containing(victim.getX(), victim.getY() - 1.0D, victim.getZ());
            BlockState ground = server.getBlockState(groundPos);
            BlockParticleOption debris = new BlockParticleOption(ParticleTypes.BLOCK, ground);
            for (int i = 0; i < 8; ++i) {
                ServerParticleUtil.circularParticles(server, debris,
                        victim.getX(), victim.getY() + 0.25D, victim.getZ(), radius);
            }
            MapColor mapColor = ground.getMapColor(server, groundPos);
            ColorUtil color = mapColor.col == 0 ? new ColorUtil(0xFFFFFF) : new ColorUtil(mapColor.col);
            server.sendParticles(new SmashParticleOption(color, radius * 2.0F, 5),
                    victim.getX(), victim.getY() + 0.25D, victim.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        }
    }
}
