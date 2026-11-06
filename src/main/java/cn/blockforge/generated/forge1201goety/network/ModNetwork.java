package cn.blockforge.generated.goetysupreme.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import cn.blockforge.generated.goetysupreme.network.FellSpearStabPacket;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.network.ModNetwork;
import cn.blockforge.generated.goetysupreme.network.WitherFlightPacket;
import cn.blockforge.generated.goetysupreme.network.WitherRoarPacket;

public final class ModNetwork {
    private static final String PROTOCOL = "1.2";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals);

    private ModNetwork() {
    }

    public static void init() {
        CHANNEL.messageBuilder(WitherFlightPacket.class, 0)
                .encoder(WitherFlightPacket::encode)
                .decoder(WitherFlightPacket::decode)
                .consumerMainThread(WitherFlightPacket::handle)
                .add();
        CHANNEL.messageBuilder(WitherRoarPacket.class, 1)
                .encoder(WitherRoarPacket::encode)
                .decoder(WitherRoarPacket::decode)
                .consumerMainThread(WitherRoarPacket::handle)
                .add();
        CHANNEL.messageBuilder(FellSpearStabPacket.class, 2)
                .encoder(FellSpearStabPacket::encode)
                .decoder(FellSpearStabPacket::decode)
                .consumerMainThread(FellSpearStabPacket::handle)
                .add();
    }
}
