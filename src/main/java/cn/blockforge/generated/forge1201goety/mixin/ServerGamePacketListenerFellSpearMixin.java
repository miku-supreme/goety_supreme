package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.api.FellSpearKnownMovement;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerGamePacketListenerImpl.class, remap = false)
public abstract class ServerGamePacketListenerFellSpearMixin {

    @Unique
    private double goetySupreme$vehicleX;
    @Unique
    private double goetySupreme$vehicleY;
    @Unique
    private double goetySupreme$vehicleZ;

    @Unique
    private boolean goetySupreme$vehicleCaptured;

    @Redirect(method = {"m_7185_", "handleMovePlayer"},
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerPlayer;m_289603_(ZLnet/minecraft/world/phys/Vec3;)V"),
            require = 0)
    private void goetySupreme$captureSpearMotion(ServerPlayer player, boolean onGround, Vec3 movement) {
        if (player instanceof FellSpearKnownMovement known) {
            known.goetySupreme$setSpearMotion(movement);
        }
        player.setOnGroundWithKnownMovement(onGround, movement);
    }

    @Inject(method = {"m_5659_", "handleMoveVehicle"}, at = @At("HEAD"), require = 0)
    private void goetySupreme$vehicleHead(ServerboundMoveVehiclePacket packet, CallbackInfo callback) {
        this.goetySupreme$vehicleCaptured = false;
        Entity vehicle = this.goetySupreme$player().getRootVehicle();
        if (vehicle == this.goetySupreme$player()) {
            return;
        }
        this.goetySupreme$vehicleX = vehicle.getX();
        this.goetySupreme$vehicleY = vehicle.getY();
        this.goetySupreme$vehicleZ = vehicle.getZ();
        this.goetySupreme$vehicleCaptured = true;
    }

    @Inject(method = {"m_5659_", "handleMoveVehicle"}, at = @At("TAIL"), require = 0)
    private void goetySupreme$vehicleTail(ServerboundMoveVehiclePacket packet, CallbackInfo callback) {
        if (!this.goetySupreme$vehicleCaptured) {
            return;
        }
        ServerPlayer player = this.goetySupreme$player();
        if (!(player instanceof FellSpearKnownMovement known)) {
            return;
        }
        Entity vehicle = player.getRootVehicle();
        known.goetySupreme$setSpearMotion(new Vec3(vehicle.getX() - this.goetySupreme$vehicleX,
                vehicle.getY() - this.goetySupreme$vehicleY, vehicle.getZ() - this.goetySupreme$vehicleZ));
    }

    @Unique
    private ServerPlayer goetySupreme$player() {
        return ((ServerGamePacketListenerImpl) (Object) this).getPlayer();
    }
}
