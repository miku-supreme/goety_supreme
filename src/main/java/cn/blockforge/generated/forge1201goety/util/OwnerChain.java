package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import cn.blockforge.generated.goetysupreme.util.OwnerChain;

public final class OwnerChain {

    private static final int MAX_DEPTH = 16;

    private OwnerChain() {
    }

    public static Player playerOwner(LivingEntity entity) {
        LivingEntity current = entity;
        Set<UUID> seen = new HashSet<>();
        for (int depth = 0; depth < MAX_DEPTH && current != null; depth++) {
            if (current instanceof Player player) {
                return player;
            }
            if (!seen.add(current.getUUID())) {
                return null;
            }
            LivingEntity next = null;
            if (current instanceof IOwned owned) {
                next = owned.getTrueOwner();
            } else if (current instanceof OwnableEntity ownable) {
                next = ownable.getOwner();
            }
            if (next == null || next == current) {
                return null;
            }
            current = next;
        }
        return null;
    }
}
