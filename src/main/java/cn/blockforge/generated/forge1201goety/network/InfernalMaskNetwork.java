package cn.blockforge.generated.goetysupreme.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.event.InfernalMaskEvents;
import cn.blockforge.generated.goetysupreme.network.InfernalMaskNetwork;

public final class InfernalMaskNetwork {

    private static final String VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "infernal_mask"))
            .networkProtocolVersion(() -> VERSION)
            .clientAcceptedVersions(VERSION::equals)
            .serverAcceptedVersions(VERSION::equals)
            .simpleChannel();

    private InfernalMaskNetwork() {
    }

    public static void register() {
        CHANNEL.messageBuilder(ActionPacket.class, 0)
                .encoder(ActionPacket::encode)
                .decoder(ActionPacket::new)
                .consumerMainThread((packet, ctx) -> packet.handle(ctx.get()))
                .add();
    }

    public static void send(int action, int direction) {
        com.mojang.logging.LogUtils.getLogger().info(
                "[infernal_mask] C2S send action={} dir={}", action, direction);
        CHANNEL.sendToServer(new ActionPacket(action, direction));
    }

    public record ActionPacket(int action, int direction) {
        public ActionPacket(FriendlyByteBuf buf) {
            this(buf.readByte(), buf.readByte());
        }

        public void encode(FriendlyByteBuf buf) {
            buf.writeByte(this.action);
            buf.writeByte(this.direction);
        }

        public void handle(net.minecraftforge.network.NetworkEvent.Context ctx) {
            ServerPlayer player = ctx.getSender();
            if (player != null) {
                ctx.enqueueWork(() -> InfernalMaskEvents.onAction(player, this.action, this.direction));
            }
            ctx.setPacketHandled(true);
        }
    }
}
