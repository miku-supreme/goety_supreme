package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.FellSpearKnownMovement;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import cn.blockforge.generated.goetysupreme.mixin.ServerGamePacketListenerFellSpearMixin;

@Mixin(value = ServerPlayer.class, remap = false)
public abstract class ServerPlayerFellSpearMixin implements FellSpearKnownMovement {

    @Unique
    private Vec3 goetySupreme$spearMotion = Vec3.ZERO;

    @Override
    public Vec3 goetySupreme$spearMotion() {
        return this.goetySupreme$spearMotion;
    }

    @Override
    public void goetySupreme$setSpearMotion(Vec3 movement) {
        this.goetySupreme$spearMotion = movement;
    }
}
