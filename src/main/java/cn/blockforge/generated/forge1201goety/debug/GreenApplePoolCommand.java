package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.item.GreenAppleItem;
import cn.blockforge.generated.goetysupreme.debug.AnnihilationProbeCommand;
import cn.blockforge.generated.goetysupreme.debug.PlayerMobCommand;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class GreenApplePoolCommand {

    private GreenApplePoolCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("greenapple")
                .requires(source -> source.hasPermission(2))
                .executes(GreenApplePoolCommand::report)
                .then(Commands.literal("pool").executes(GreenApplePoolCommand::report))
                .then(Commands.literal("why")
                        .then(Commands.argument("entity", StringArgumentType.greedyString())
                                .executes(GreenApplePoolCommand::why))));
    }

    private static int report(CommandContext<CommandSourceStack> context) {
        ServerLevel level = context.getSource().getLevel();
        List<String> lines = GreenAppleItem.debugReport(level);
        for (String line : lines) {
            reply(context, line);
        }
        reply(context, "提示：/greenapple why <实体id> 可以单查一只，"
                + "例如 /greenapple why goetyawaken:nameless_one");
        return lines.size();
    }

    private static int why(CommandContext<CommandSourceStack> context) {
        String idText = StringArgumentType.getString(context, "entity");
        EntityType<?> type = GreenAppleItem.entityType(idText);
        if (type == null) {
            context.getSource().sendFailure(Component.literal(
                    "这个包里没有注册实体 " + idText + "（检查注册名拼写；模组没装也会这样）。"));
            return 0;
        }
        reply(context, GreenAppleItem.explain(context.getSource().getLevel(), type));
        return 1;
    }

    private static void reply(CommandContext<CommandSourceStack> context, String message) {
        context.getSource().sendSuccess(() -> Component.literal(message), false);
    }
}
