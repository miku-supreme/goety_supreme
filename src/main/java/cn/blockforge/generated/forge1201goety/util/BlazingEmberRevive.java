package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.items.revive.ReviveServantItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.entity.AbstractJvillageInventoryServant;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;
import cn.blockforge.generated.goetysupreme.item.BlazingEmberItem;
import cn.blockforge.generated.goetysupreme.util.BlazingEmberRevive;
import cn.blockforge.generated.goetysupreme.item.CryptSoulJarItem;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.item.GreenSmithSoulJarItem;
import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.compat.JvillageNativeGoals;
import cn.blockforge.generated.goetysupreme.compat.JvillageServantShieldBreak;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.item.ReaperSoulJarItem;
import cn.blockforge.generated.goetysupreme.util.ReviveCooldown;
import cn.blockforge.generated.goetysupreme.item.SedimentLordTotemItem;
import cn.blockforge.generated.goetysupreme.entity.SerponFireApostle;
import cn.blockforge.generated.goetysupreme.util.ServantLimits;
import cn.blockforge.generated.goetysupreme.item.WildMageSoulJarItem;

public final class BlazingEmberRevive {

    private BlazingEmberRevive() {
    }

    public static boolean reviveAtFire(ServerLevel server, Player player, ItemStack stack, BlockPos firePos) {
        EntityType<SerponFireApostle> type = ModEntityTypes.SERPON_FIRE_APOSTLE.get();
        if (!ServantLimits.hasRoom(server, player, type)) {
            return false;
        }
        Entity stored = ReviveServantItem.getSummon(stack, server);
        SerponFireApostle apostle = null;
        boolean fromRemains = false;
        if (stored instanceof SerponFireApostle revived) {
            apostle = revived;
            fromRemains = true;
        } else if (stored != null) {
            stored.discard();
        }
        if (apostle == null) {
            apostle = type.create(server);
            if (apostle == null) {
                return false;
            }
            apostle.moveTo(firePos.getX() + 0.5D, firePos.getY(), firePos.getZ() + 0.5D,
                    player.getYRot(), 0.0F);
            apostle.finalizeSpawn(server, server.getCurrentDifficultyAt(firePos),
                    MobSpawnType.MOB_SUMMONED, null, null);
        }
        apostle.moveTo(firePos.getX() + 0.5D, firePos.getY(), firePos.getZ() + 0.5D,
                player.getYRot(), 0.0F);
        apostle.setTrueOwner(player);
        apostle.setHealth(apostle.getMaxHealth());
        apostle.deathTime = 0;
        apostle.setYBodyRot(player.yBodyRot);
        apostle.setYHeadRot(player.getYRot());
        apostle.setYRot(player.getYRot());
        apostle.setXRot(0.0F);
        apostle.setLifespan(0);
        apostle.setHasLifespan(false);
        apostle.setPersistenceRequired();
        if (!server.addFreshEntity(apostle)) {
            apostle.discard();
            return false;
        }
        portalBurst(server, firePos);
        player.displayClientMessage(Component.translatable(
                fromRemains ? "item.goety_supreme.blazing_ember.revived"
                        : "item.goety_supreme.blazing_ember.revived_new",
                apostle.getDisplayName()), true);
        ReviveCooldown.grantAfterRevive(player, stack);
        stack.shrink(1);
        return true;
    }

    private static void portalBurst(ServerLevel level, BlockPos pos) {
        level.sendParticles(ParticleTypes.PORTAL,
                pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                48, 0.45D, 0.45D, 0.45D, 0.6D);
        level.sendParticles(ParticleTypes.FLAME,
                pos.getX() + 0.5D, pos.getY() + 0.25D, pos.getZ() + 0.5D,
                12, 0.3D, 0.2D, 0.3D, 0.01D);
    }
}
