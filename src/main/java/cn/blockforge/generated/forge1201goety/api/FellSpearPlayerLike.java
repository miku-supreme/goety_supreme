package cn.blockforge.generated.goetysupreme.api;

import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.api.FellSpearPlayerLike;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;

public interface FellSpearPlayerLike {

    boolean goetySupreme$useSpearAsPlayer();

    public static boolean is(LivingEntity entity) {
        return entity instanceof FellSpearPlayerLike like && like.goetySupreme$useSpearAsPlayer();
    }
}
