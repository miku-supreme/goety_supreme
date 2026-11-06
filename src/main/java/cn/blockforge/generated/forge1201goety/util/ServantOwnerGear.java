package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import com.Polarice3.Goety.common.items.curios.RingItem;
import com.Polarice3.Goety.utils.CuriosFinder;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import cn.blockforge.generated.goetysupreme.entity.AbstractFrostGolemServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.util.ServantOwnerGear;
import cn.blockforge.generated.goetysupreme.mixin.CuriosFinderServantOwnerMixin;

public final class ServantOwnerGear {

    private static final int MAX_CHAIN = 16;

    private ServantOwnerGear() {
    }

    public static boolean inheritsOwnerGear(LivingEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity instanceof PlayerMobServant) {
            return true;
        }
        LivingEntity current = entity;
        for (int i = 0; i < MAX_CHAIN && current != null; ++i) {
            if (current instanceof Player) {
                return false;
            }
            LivingEntity owner = current instanceof IOwned owned ? owned.getTrueOwner() : null;
            if (owner == null || owner == current) {
                return false;
            }
            if (owner instanceof PlayerMobServant) {
                return true;
            }
            current = owner;
        }
        return false;
    }

    public static Player topOwner(LivingEntity entity) {
        LivingEntity current = entity;
        for (int i = 0; i < MAX_CHAIN && current != null; ++i) {
            if (current instanceof Player player) {
                return player;
            }
            LivingEntity owner = current instanceof IOwned owned ? owned.getTrueOwner() : null;
            if (owner == null || owner == current) {
                return null;
            }
            current = owner;
        }
        return null;
    }

    public static boolean ownerHasCurio(LivingEntity entity, Predicate<ItemStack> filter) {
        Player owner = topOwner(entity);
        if (owner == null) {
            return false;
        }
        ItemStack matched = CuriosFinder.findCurio(owner, filter);
        return !matched.isEmpty() && !isRing(matched.getItem());
    }

    public static boolean ownerHasCurio(LivingEntity entity, Item item) {
        if (isRing(item)) {
            return false;
        }
        Player owner = topOwner(entity);
        return owner != null && CuriosFinder.hasCurio(owner, item);
    }

    private static boolean isRing(Item item) {
        if (item == null || item == Items.AIR) {
            return false;
        }
        return item instanceof RingItem
                || item == GeneratedMod.TEMPERED_RING.get()
                || item == GeneratedMod.IMMUTABLE_RING.get();
    }
}
