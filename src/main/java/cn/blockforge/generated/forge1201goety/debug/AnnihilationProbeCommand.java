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
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.debug.AnnihilationProbe;
import cn.blockforge.generated.goetysupreme.debug.PlayerMobCommand;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class AnnihilationProbeCommand {

    private static final int DEFAULT_SECONDS = 600;
    private static final int DEFAULT_DUMP_LINES = 60;

    private AnnihilationProbeCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("gsprobe")
                .requires(source -> source.hasPermission(2))
                .executes(AnnihilationProbeCommand::status)
                .then(Commands.literal("on")
                        .executes(context -> enable(context, DEFAULT_SECONDS))
                        .then(Commands.argument("seconds", IntegerArgumentType.integer(5, 3600))
                                .executes(context -> enable(context, IntegerArgumentType.getInteger(context, "seconds")))))
                .then(Commands.literal("off").executes(AnnihilationProbeCommand::disable))
                .then(Commands.literal("status").executes(AnnihilationProbeCommand::status))
                .then(Commands.literal("dump")
                        .executes(context -> dump(context, DEFAULT_DUMP_LINES))
                        .then(Commands.argument("lines", IntegerArgumentType.integer(1, 200))
                                .executes(context -> dump(context, IntegerArgumentType.getInteger(context, "lines")))))
                .then(Commands.literal("clear").executes(AnnihilationProbeCommand::clear))
                .then(Commands.literal("detonate")
                        .executes(context -> detonate(context, 64.0D))
                        .then(Commands.argument("radius", IntegerArgumentType.integer(4, 128))
                                .executes(context -> detonate(context, IntegerArgumentType.getInteger(context, "radius"))))));
    }

    private static int enable(CommandContext<CommandSourceStack> context, int seconds) {
        AnnihilationProbe.setEnabled(true, seconds);
        reply(context, "检测已开启，最多 " + seconds + " 秒。现在去打使徒，"
                + "或者站到凋灵仆从旁边用 /gsprobe detonate 立刻引爆一次。");
        return 1;
    }

    private static int disable(CommandContext<CommandSourceStack> context) {
        AnnihilationProbe.setEnabled(false, 0L);
        reply(context, "检测已关闭，共记录 " + AnnihilationProbe.written() + " 行。"
                + "日志：" + AnnihilationProbe.logPathText());
        return 1;
    }

    private static int status(CommandContext<CommandSourceStack> context) {
        reply(context, "检测=" + (AnnihilationProbe.isEnabled()
                ? "开启（剩 " + AnnihilationProbe.remainingSeconds() + " 秒）" : "关闭")
                + "，本次已写 " + AnnihilationProbe.written() + " 行");
        reply(context, "日志文件：" + AnnihilationProbe.logPathText());
        reply(context, "Goety 配置：" + AnnihilationProbe.goetyConfig());
        return 1;
    }

    private static int dump(CommandContext<CommandSourceStack> context, int lines) {
        List<String> tail = AnnihilationProbe.tail(lines);
        if (tail.isEmpty()) {
            reply(context, "还没有记录。先 /gsprobe on，再打一次使徒。");
            return 0;
        }
        reply(context, "最近 " + tail.size() + " 行（完整日志：" + AnnihilationProbe.logPathText() + "）：");
        for (String line : tail) {
            context.getSource().sendSystemMessage(Component.literal(line));
        }
        return tail.size();
    }

    private static int clear(CommandContext<CommandSourceStack> context) {
        AnnihilationProbe.clearLog();
        reply(context, "已清空记录并删除日志文件。");
        return 1;
    }

    private static int detonate(CommandContext<CommandSourceStack> context, double radius) {
        CommandSourceStack source = context.getSource();
        ServerLevel level = source.getLevel();
        Vec3 center = source.getPosition();
        List<WitherServant> servants = level.getEntitiesOfClass(WitherServant.class,
                new AABB(center, center).inflate(radius));
        if (servants.isEmpty()) {
            source.sendFailure(Component.literal("半径 " + (int) radius + " 格内没有本模组的凋灵仆从。"));
            return 0;
        }
        WitherServant nearest = null;
        double best = Double.MAX_VALUE;
        for (WitherServant servant : servants) {
            double distance = servant.distanceToSqr(center);
            if (distance < best) {
                best = distance;
                nearest = servant;
            }
        }
        if (nearest == null) {
            return 0;
        }
        boolean ok = nearest.debugDetonateNow();
        reply(context, "已强制引爆 " + AnnihilationProbe.entity(nearest)
                + "（距离 " + AnnihilationProbe.trim(Math.sqrt(best)) + " 格）。");
        return ok ? 1 : 0;
    }

    private static void reply(CommandContext<CommandSourceStack> context, String message) {
        context.getSource().sendSuccess(() -> Component.literal(message), false);
    }
}
