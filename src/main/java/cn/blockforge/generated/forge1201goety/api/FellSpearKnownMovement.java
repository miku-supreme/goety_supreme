package cn.blockforge.generated.goetysupreme.api;

import net.minecraft.world.phys.Vec3;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.api.FellSpearKnownMovement;
import cn.blockforge.generated.goetysupreme.mixin.ServerGamePacketListenerFellSpearMixin;
import cn.blockforge.generated.goetysupreme.mixin.ServerPlayerFellSpearMixin;

public interface FellSpearKnownMovement {

    Vec3 goetySupreme$spearMotion();

    void goetySupreme$setSpearMotion(Vec3 movement);
}
