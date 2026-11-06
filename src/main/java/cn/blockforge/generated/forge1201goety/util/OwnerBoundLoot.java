package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.projectiles.FlyingItem;
import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.util.OwnerBoundLoot;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;

public final class OwnerBoundLoot {

    private OwnerBoundLoot() {
    }

    public static void deliver(LivingEntity dying, ItemStack stack) {
        deliver(dying, stack, ParticleTypes.SOUL);
    }

    public static void deliver(LivingEntity dying, ItemStack stack, ParticleOptions particle) {
        deliver(dying, stack, particle, true);
    }

    public static void deliverNoCooldown(LivingEntity dying, ItemStack stack) {
        deliver(dying, stack, ParticleTypes.SOUL, false);
    }

    public static void deliverNoCooldown(LivingEntity dying, ItemStack stack, ParticleOptions particle) {
        deliver(dying, stack, particle, false);
    }

    private static void deliver(LivingEntity dying, ItemStack stack, ParticleOptions particle,
            boolean reviveTokenCooldown) {
        if (dying == null || stack == null || stack.isEmpty() || dying.level().isClientSide()) {
            return;
        }
        LivingEntity owner = dying instanceof IOwned owned ? owned.getTrueOwner() : null;
        boolean reviveToken = reviveTokenCooldown && stack.getItem() instanceof ReviveServantItem;
        if (owner == null || owner.isRemoved() || !owner.isAlive() || owner.level() != dying.level()) {
            if (reviveToken && owner instanceof Player player) {
                ReviveCooldown.grantOnDelivery(player, stack);
            }
            dying.spawnAtLocation(stack);
            return;
        }
        FlyingItem flying = new FlyingItem(ModEntityType.FLYING_ITEM.get(), dying.level(),
                dying.getX(), dying.getY() + dying.getBbHeight() * 0.5D, dying.getZ());
        flying.setOwner(owner);
        flying.setItem(stack.copy());
        flying.setParticle(particle);
        if (reviveToken) {
            flying.setSecondsCool(ReviveCooldown.deliverySeconds());
        }
        dying.level().addFreshEntity(flying);
    }
}
