package cn.blockforge.generated.goetysupreme.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
import cn.blockforge.generated.goetysupreme.network.WitherRoarPacket;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

public final class WitherRoarPacket {

    public static final WitherRoarPacket INSTANCE = new WitherRoarPacket();

    private WitherRoarPacket() {
    }

    public static void encode(WitherRoarPacket packet, FriendlyByteBuf buf) {
    }

    public static WitherRoarPacket decode(FriendlyByteBuf buf) {
        return INSTANCE;
    }

    public static void handle(WitherRoarPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender == null || !sender.isPassenger()) {
                return;
            }
            Entity vehicle = sender.getVehicle();
            if (vehicle instanceof WitherServant servant) {
                servant.handleRoar(sender);
            }
        });
        ctx.setPacketHandled(true);
    }
}
