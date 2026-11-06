package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.debug.WandFocusProbe;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WandFocusProbeCommand {

    private static final int DEFAULT_SECONDS = 1800;
    private static final int DEFAULT_DUMP_LINES = 80;

    private WandFocusProbeCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("gswand")
                .requires(source -> source.hasPermission(2))
                .executes(WandFocusProbeCommand::status)
                .then(Commands.literal("on")
                        .executes(context -> enable(context, DEFAULT_SECONDS))
                        .then(Commands.argument("seconds", IntegerArgumentType.integer(5, 3600))
                                .executes(context -> enable(context,
                                        IntegerArgumentType.getInteger(context, "seconds")))))
                .then(Commands.literal("off").executes(WandFocusProbeCommand::disable))
                .then(Commands.literal("status").executes(WandFocusProbeCommand::status))
                .then(Commands.literal("dump")
                        .executes(context -> dump(context, DEFAULT_DUMP_LINES))
                        .then(Commands.argument("lines", IntegerArgumentType.integer(1, 300))
                                .executes(context -> dump(context,
                                        IntegerArgumentType.getInteger(context, "lines")))))
                .then(Commands.literal("clear").executes(WandFocusProbeCommand::clear))
                .then(Commands.literal("watch")
                        .then(Commands.argument("spell", StringArgumentType.word())
                                .executes(context -> watch(context,
                                        StringArgumentType.getString(context, "spell")))))
                .then(Commands.literal("unwatch")
                        .then(Commands.argument("spell", StringArgumentType.word())
                                .executes(context -> unwatch(context,
                                        StringArgumentType.getString(context, "spell"))))));
    }

    private static int enable(CommandContext<CommandSourceStack> context, int seconds) {
        WandFocusProbe.setEnabled(true, seconds);
        reply(context, "魔杖聚晶检测已开启，最多 " + seconds + " 秒，关注="
                + WandFocusProbe.watchedText() + "。带上雷霆之怒聚晶的仆从去打一架即可。");
        return 1;
    }

    private static int disable(CommandContext<CommandSourceStack> context) {
        WandFocusProbe.setEnabled(false, 0L);
        reply(context, "检测已关闭，共记录 " + WandFocusProbe.written() + " 行。日志："
                + WandFocusProbe.logPathText());
        return 1;
    }

    private static int status(CommandContext<CommandSourceStack> context) {
        reply(context, "魔杖聚晶检测=" + (WandFocusProbe.isEnabled()
                ? "开启（剩 " + WandFocusProbe.remainingSeconds() + " 秒）" : "关闭")
                + "，本次已写 " + WandFocusProbe.written() + " 行");
        reply(context, "关注法术（只管决策/结算/中断；起手全量记）：" + WandFocusProbe.watchedText());
        reply(context, "日志文件：" + WandFocusProbe.logPathText());
        return 1;
    }

    private static int dump(CommandContext<CommandSourceStack> context, int lines) {
        List<String> tail = WandFocusProbe.tail(lines);
        if (tail.isEmpty()) {
            reply(context, "还没有记录。先 /gswand on，再带雷霆之怒聚晶的仆从打一架。");
            return 0;
        }
        reply(context, "最近 " + tail.size() + " 行（完整日志：" + WandFocusProbe.logPathText() + "）：");
        for (String line : tail) {
            context.getSource().sendSystemMessage(Component.literal(line));
        }
        return tail.size();
    }

    private static int clear(CommandContext<CommandSourceStack> context) {
        WandFocusProbe.clearLog();
        reply(context, "已清空记录并删除日志文件。");
        return 1;
    }

    private static int watch(CommandContext<CommandSourceStack> context, String spell) {
        if (WandFocusProbe.watch(spell)) {
            reply(context, "已关注 " + spell + "。现在关注：" + WandFocusProbe.watchedText());
        } else {
            reply(context, spell + " 已经在关注名单里。现在关注：" + WandFocusProbe.watchedText());
        }
        return 1;
    }

    private static int unwatch(CommandContext<CommandSourceStack> context, String spell) {
        if (WandFocusProbe.unwatch(spell)) {
            reply(context, "已取消关注 " + spell + "。现在关注：" + WandFocusProbe.watchedText());
        } else {
            reply(context, spell + " 不在关注名单里。现在关注：" + WandFocusProbe.watchedText());
        }
        return 1;
    }

    private static void reply(CommandContext<CommandSourceStack> context, String message) {
        context.getSource().sendSuccess(() -> Component.literal(message), false);
    }
}
