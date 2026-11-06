package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.PlayerMobServantSkinFolder;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class PlayerMobServantSkinCommand {

    private PlayerMobServantSkinCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterClientCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = cast(event.getDispatcher());
        dispatcher.register(Commands.literal("servant_skins")
                .executes(PlayerMobServantSkinCommand::open)
                .then(Commands.literal("open").executes(PlayerMobServantSkinCommand::open))
                .then(Commands.literal("rescan").executes(PlayerMobServantSkinCommand::rescan))
                .then(Commands.literal("path").executes(PlayerMobServantSkinCommand::path)));
    }

    @SuppressWarnings("unchecked")
    private static CommandDispatcher<CommandSourceStack> cast(CommandDispatcher<?> dispatcher) {
        return (CommandDispatcher<CommandSourceStack>) dispatcher;
    }

    private static int open(CommandContext<CommandSourceStack> context) {
        PlayerMobServantSkinFolder.openFolder();
        context.getSource().sendSuccess(() -> Component.translatable(
                "command.goety_supreme.servant_skins.opened",
                Component.literal(PlayerMobServantSkinFolder.folder().toString())), false);
        return 1;
    }

    private static int rescan(CommandContext<CommandSourceStack> context) {
        PlayerMobServantSkinFolder.forceRescan();
        context.getSource().sendSuccess(() -> Component.translatable(
                "command.goety_supreme.servant_skins.rescanned",
                Component.literal(Integer.toString(PlayerMobServantSkinFolder.skinCount()))), false);
        report(context, false);
        return 1;
    }

    private static int path(CommandContext<CommandSourceStack> context) {
        report(context, true);
        return 1;
    }

    private static void report(CommandContext<CommandSourceStack> context, boolean withNames) {
        List<String> names = PlayerMobServantSkinFolder.skinNames();
        context.getSource().sendSuccess(() -> Component.translatable(
                "command.goety_supreme.servant_skins.path",
                Component.literal(PlayerMobServantSkinFolder.folder().toString())), false);
        context.getSource().sendSuccess(() -> Component.translatable(
                "command.goety_supreme.servant_skins.count",
                Component.literal(Integer.toString(names.size()))), false);
        if (withNames && !names.isEmpty()) {
            context.getSource().sendSuccess(() -> Component.literal(String.join(", ", names)), false);
        }
    }
}
