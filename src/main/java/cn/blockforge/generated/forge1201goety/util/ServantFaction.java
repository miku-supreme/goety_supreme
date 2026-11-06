package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.api.entities.IOwned;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import cn.blockforge.generated.goetysupreme.util.ServantFaction;

public final class ServantFaction {
    private ServantFaction() {
    }

    public static boolean isSameFaction(Entity first, Entity second) {
        if (first == second) {
            return true;
        }
        UUID firstOwner = ownerId(first);
        UUID secondOwner = ownerId(second);
        return firstOwner != null && firstOwner.equals(secondOwner);
    }

    public static UUID ownerId(Entity entity) {
        if (entity == null) {
            return null;
        }
        Entity current = entity;
        Set<UUID> visited = new HashSet<>();
        for (int i = 0; i < 8 && current != null; i++) {
            UUID currentId = current.getUUID();
            if (!visited.add(currentId)) {
                return currentId;
            }
            if (current instanceof IOwned owned) {
                LivingEntity owner = owned.getTrueOwner();
                if (owner != null && owner != current) {
                    current = owner;
                    continue;
                }
                UUID ownerId = owned.getOwnerId();
                return ownerId == null ? currentId : ownerId;
            }
            if (current instanceof OwnableEntity ownable) {
                LivingEntity owner = ownable.getOwner();
                if (owner != null && owner != current) {
                    current = owner;
                    continue;
                }
            }
            return currentId;
        }
        return current == null ? null : current.getUUID();
    }
}
