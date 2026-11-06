package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.debug.AnnihilationProbeCommand;
import cn.blockforge.generated.goetysupreme.debug.GreenApplePoolCommand;
import cn.blockforge.generated.goetysupreme.debug.NecroBehaviorProbe;
import cn.blockforge.generated.goetysupreme.debug.PlayerMobCommand;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class NecroBehaviorProbeCommand {

    private static final int DEFAULT_SECONDS = 300;
    private static final int DEFAULT_DUMP_LINES = 40;

    private NecroBehaviorProbeCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("necroprobe")
                .requires(source -> source.hasPermission(2))
                .executes(NecroBehaviorProbeCommand::status)
                .then(Commands.literal("on")
                        .executes(context -> enable(context, DEFAULT_SECONDS))
                        .then(Commands.argument("seconds", IntegerArgumentType.integer(5, 3600))
                                .executes(context -> enable(context, IntegerArgumentType.getInteger(context, "seconds")))))
                .then(Commands.literal("off").executes(NecroBehaviorProbeCommand::disable))
                .then(Commands.literal("status").executes(NecroBehaviorProbeCommand::status))
                .then(Commands.literal("dump")
                        .executes(context -> dump(context, DEFAULT_DUMP_LINES))
                        .then(Commands.argument("lines", IntegerArgumentType.integer(1, 200))
                                .executes(context -> dump(context, IntegerArgumentType.getInteger(context, "lines")))))
                .then(Commands.literal("clear").executes(NecroBehaviorProbeCommand::clear))
                .then(Commands.literal("snap")
                        .executes(context -> snap(context, 0))
                        .then(Commands.argument("radius", IntegerArgumentType.integer(4, 256))
                                .executes(context -> snap(context, IntegerArgumentType.getInteger(context, "radius")))))
                .then(Commands.literal("interval")
                        .then(Commands.argument("ticks", IntegerArgumentType.integer(1, 200))
                                .executes(context -> interval(context, IntegerArgumentType.getInteger(context, "ticks")))))
                .then(Commands.literal("radius")
                        .then(Commands.argument("blocks", IntegerArgumentType.integer(8, 256))
                                .executes(context -> radius(context, IntegerArgumentType.getInteger(context, "blocks"))))));
    }

    private static int enable(CommandContext<CommandSourceStack> context, int seconds) {
        NecroBehaviorProbe.setEnabled(true, seconds);
        reply(context, "死法行为检测已开启，最多 " + seconds + " 秒。"
                + "现在去打一场，让死法召完兵之后接近敌人；测完 /necroprobe off。");
        reply(context, "日志：" + NecroBehaviorProbe.logPathText());
        reply(context, NecroBehaviorProbe.sessionSummary());
        reply(context, "上一局的日志已自动归档成同目录的 *.bak.log；测完把最新日志拖进对话即可，"
                + "脚本按会话头切段，不会再把两次测试混在一起。");
        return 1;
    }

    private static int disable(CommandContext<CommandSourceStack> context) {
        NecroBehaviorProbe.setEnabled(false, 0L);
        reply(context, "检测已关闭，共记录 " + NecroBehaviorProbe.written() + " 行。"
                + "日志：" + NecroBehaviorProbe.logPathText());
        return 1;
    }

    private static int status(CommandContext<CommandSourceStack> context) {
        reply(context, "检测=" + (NecroBehaviorProbe.isEnabled()
                ? "开启（剩 " + NecroBehaviorProbe.remainingSeconds() + " 秒）" : "关闭")
                + "，本次已写 " + NecroBehaviorProbe.written() + " 行");
        reply(context, "采样间隔=" + NecroBehaviorProbe.intervalTicks() + " 刻（状态一变立刻补行），"
                + "半径=" + (int) NecroBehaviorProbe.radius() + " 格");
        reply(context, "日志文件：" + NecroBehaviorProbe.logPathText());
        reply(context, NecroBehaviorProbe.sessionSummary());
        ServerLevel level = context.getSource().getLevel();
        Vec3 center = context.getSource().getPosition();
        List<String> near = NecroBehaviorProbe.describeNear(level,
                new AABB(center, center).inflate(NecroBehaviorProbe.radius()));
        if (near.isEmpty()) {
            reply(context, "附近 " + (int) NecroBehaviorProbe.radius() + " 格内没有找到死法实体。");
        } else {
            reply(context, "附近共 " + near.size() + " 只：");
            for (String line : near) {
                reply(context, "  " + line);
            }
        }
        return 1;
    }

    private static int dump(CommandContext<CommandSourceStack> context, int lines) {
        List<String> tail = NecroBehaviorProbe.tail(lines);
        if (tail.isEmpty()) {
            reply(context, "还没有记录。先 /necroprobe on，再打一场；也可以 /necroprobe snap 立刻采一次。");
            return 0;
        }
        reply(context, "最近 " + tail.size() + " 行（完整日志：" + NecroBehaviorProbe.logPathText() + "）：");
        for (String line : tail) {
            context.getSource().sendSystemMessage(Component.literal(line));
        }
        return tail.size();
    }

    private static int clear(CommandContext<CommandSourceStack> context) {
        NecroBehaviorProbe.clearLog();
        reply(context, "已清空记录并删除日志文件，新会话 " + NecroBehaviorProbe.sessionId()
                + "（*.bak.log 归档不动）。");
        return 1;
    }

    private static int snap(CommandContext<CommandSourceStack> context, int radius) {
        if (radius > 0) {
            NecroBehaviorProbe.setRadius(radius);
        }
        ServerLevel level = context.getSource().getLevel();
        Vec3 center = context.getSource().getPosition();
        AABB box = new AABB(center, center).inflate(NecroBehaviorProbe.radius());
        List<String> near = NecroBehaviorProbe.describeNear(level, box);
        if (near.isEmpty()) {
            reply(context, "半径 " + (int) NecroBehaviorProbe.radius() + " 格内没有死法实体。");
            return 0;
        }
        NecroBehaviorProbe.snapNow(level, box);
        reply(context, "已立刻采样，半径内 " + near.size() + " 只：");
        for (String line : near) {
            reply(context, "  " + line);
        }
        reply(context, "详细一行一只已写入 " + NecroBehaviorProbe.logPathText());
        return near.size();
    }

    private static int interval(CommandContext<CommandSourceStack> context, int ticks) {
        NecroBehaviorProbe.setIntervalTicks(ticks);
        reply(context, "采样间隔改为 " + NecroBehaviorProbe.intervalTicks()
                + " 刻（约 " + NecroBehaviorProbe.fmt(NecroBehaviorProbe.intervalTicks() / 20.0D) + " 秒）。");
        return 1;
    }

    private static int radius(CommandContext<CommandSourceStack> context, int blocks) {
        NecroBehaviorProbe.setRadius(blocks);
        reply(context, "采样半径改为 " + (int) NecroBehaviorProbe.radius() + " 格。");
        return 1;
    }

    private static void reply(CommandContext<CommandSourceStack> context, String message) {
        context.getSource().sendSuccess(() -> Component.literal(message), false);
    }
}
