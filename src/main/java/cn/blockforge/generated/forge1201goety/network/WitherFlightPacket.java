package cn.blockforge.generated.goetysupreme.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.network.WitherFlightPacket;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;

public final class WitherFlightPacket {
    private final boolean flyUp;
    private final boolean flyDown;
    private final boolean dismount;

    public WitherFlightPacket(boolean flyUp, boolean flyDown, boolean dismount) {
        this.flyUp = flyUp;
        this.flyDown = flyDown;
        this.dismount = dismount;
    }

    public static void encode(WitherFlightPacket packet, FriendlyByteBuf buf) {
        buf.writeBoolean(packet.flyUp);
        buf.writeBoolean(packet.flyDown);
        buf.writeBoolean(packet.dismount);
    }

    public static WitherFlightPacket decode(FriendlyByteBuf buf) {
        return new WitherFlightPacket(buf.readBoolean(), buf.readBoolean(), buf.readBoolean());
    }

    public static void handle(WitherFlightPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender == null || !(sender.getVehicle() instanceof WitherServant
                    || sender.getVehicle() instanceof CorrosiverServant)) {
                return; // 不骑在凋灵仆从 / 蚀龙兽上的包一律忽略（也防止无主包乱改玩家状态）
            }
            if (sender.isShiftKeyDown()) {
                sender.setShiftKeyDown(false);
            }
            if (packet.dismount) {
                sender.stopRiding();
            }
        });
        ctx.setPacketHandled(true);
    }
}
