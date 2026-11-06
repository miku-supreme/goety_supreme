package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import cn.blockforge.generated.goetysupreme.entity.AbstractWildMage;
import cn.blockforge.generated.goetysupreme.GeneratedMod;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class WildMageDebugCommand {

    private WildMageDebugCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("gs_debug")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("wildmage")
                        .then(Commands.literal("cast")
                                .then(Commands.argument("spell", StringArgumentType.word())
                                        .executes(context -> cast(context.getSource(),
                                                StringArgumentType.getString(context, "spell")))))));
        dispatcher.register(Commands.literal("gs_test")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("wildmage")
                        .executes(context -> testLarge(context.getSource()))));
    }

    private static AbstractWildMage findMage(ServerPlayer player) {
        AbstractWildMage nearest = null;
        double best = Double.MAX_VALUE;
        for (AbstractWildMage mage : player.serverLevel().getEntitiesOfClass(
                AbstractWildMage.class, player.getBoundingBox().inflate(16.0D))) {
            double d = mage.distanceToSqr(player);
            if (d < best) {
                best = d;
                nearest = mage;
            }
        }
        return nearest;
    }

    private static int testLarge(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("player only"));
            return 0;
        }
        AbstractWildMage target = findMage(player);
        if (target == null) {
            source.sendFailure(Component.literal("no wildmage nearby"));
            return 0;
        }
        scheduleTest(source, target, 1);
        source.sendSuccess(() -> Component.literal("scheduled 6 serial large summons"), false);
        return 1;
    }

    private static void scheduleTest(CommandSourceStack source, AbstractWildMage target, int run) {
        source.getServer().tell(new net.minecraft.server.TickTask(
                source.getServer().getTickCount() + 60, () -> {
                    com.mojang.logging.LogUtils.getLogger().info(
                            "[wildmage][large] run={} tick={} entity={} alive={}",
                            run, source.getServer().getTickCount(), target.getUUID(), target.isAlive());
                    if (target.isAlive()) {
                        target.debugStartCast(AbstractWildMage.CAST_LARGE_SUMMON);
                    }
                    if (run < 6) {
                        scheduleTest(source, target, run + 1);
                    } else {
                        com.mojang.logging.LogUtils.getLogger().info(
                                "[wildmage][large] test finished tick={}",
                                source.getServer().getTickCount());
                    }
                }));
    }

    private static int cast(CommandSourceStack source, String spell) {
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("player only"));
            return 0;
        }
        int animation = switch (spell.toLowerCase(java.util.Locale.ROOT)) {
            case "attack" -> AbstractWildMage.CAST_ATTACK;
            case "small" -> AbstractWildMage.CAST_SMALL_SUMMON;
            case "large" -> AbstractWildMage.CAST_LARGE_SUMMON;
            case "seed" -> AbstractWildMage.CAST_SEEDS;
            case "mine" -> AbstractWildMage.CAST_MINES;
            case "heal" -> AbstractWildMage.CAST_HEAL;
            case "buff" -> AbstractWildMage.CAST_BUFF;
            case "entangle" -> AbstractWildMage.CAST_ENTANGLE;
            default -> -1;
        };
        if (animation < 0) {
            source.sendFailure(Component.literal("unknown spell: " + spell));
            return 0;
        }
        List<AbstractWildMage> mages = player.serverLevel().getEntitiesOfClass(
                AbstractWildMage.class, player.getBoundingBox().inflate(16.0D));
        AbstractWildMage nearest = null;
        double best = Double.MAX_VALUE;
        for (AbstractWildMage mage : mages) {
            double d = mage.distanceToSqr(player);
            if (d < best) {
                best = d;
                nearest = mage;
            }
        }
        if (nearest == null) {
            source.sendFailure(Component.literal("no wildmage nearby"));
            return 0;
        }
        final AbstractWildMage target = nearest;
        com.mojang.logging.LogUtils.getLogger().info(
                "[wildmage][debug] command cast spell={} entity={}", spell, target.getUUID());
        target.debugStartCast(animation);
        source.sendSuccess(() -> Component.literal("cast " + spell + " on " + target.getUUID()), false);
        return 1;
    }
}
