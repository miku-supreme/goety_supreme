package cn.blockforge.generated.goetysupreme.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import cn.blockforge.generated.goetysupreme.item.FellSpearItem;
import cn.blockforge.generated.goetysupreme.network.FellSpearStabPacket;

public final class FellSpearStabPacket {

    public static final FellSpearStabPacket INSTANCE = new FellSpearStabPacket();

    private FellSpearStabPacket() {
    }

    public static void encode(FellSpearStabPacket packet, FriendlyByteBuf buffer) {
    }

    public static FellSpearStabPacket decode(FriendlyByteBuf buffer) {
        return INSTANCE;
    }

    public static void handle(FellSpearStabPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (sender == null || !sender.isAlive() || sender.isSpectator()) {
                return;
            }
            ItemStack stack = sender.getMainHandItem();
            if (!FellSpearItem.isSpear(stack)) {
                return;
            }
            FellSpearItem.stabAttack(sender, stack);
        });
        context.setPacketHandled(true);
    }
}
