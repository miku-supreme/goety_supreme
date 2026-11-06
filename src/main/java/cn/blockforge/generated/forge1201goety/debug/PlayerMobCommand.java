package cn.blockforge.generated.goetysupreme.debug;

import com.Polarice3.Goety.utils.MobUtil;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.config.PlayerMobServantConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.PlayerMobServant;
import cn.blockforge.generated.goetysupreme.data.SerponAwayData;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class PlayerMobCommand {
    private PlayerMobCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("goety_supreme")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("playermobs")
                        .then(Commands.literal("spawn")
                                .then(Commands.argument("name", StringArgumentType.word())
                                        .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                                PlayerMobServantConfig.mobNames(), builder))
                                        .executes(PlayerMobCommand::spawn))))
                .then(Commands.literal("serponaway")
                        .executes(PlayerMobCommand::serponAwayQuery)
                        .then(Commands.argument("value", BoolArgumentType.bool())
                                .executes(PlayerMobCommand::serponAwaySet))));
    }

    private static int serponAwayQuery(CommandContext<CommandSourceStack> context) {
        SerponAwayData data = SerponAwayData.get(context.getSource().getLevel());
        boolean value = data != null && data.isSerponAway();
        context.getSource().sendSuccess(
                () -> Component.literal("蛇龙远行（SerponAway）当前：" + (value ? "开启" : "关闭")), false);
        return value ? 1 : 0;
    }

    private static int serponAwaySet(CommandContext<CommandSourceStack> context) {
        boolean value = BoolArgumentType.getBool(context, "value");
        SerponAwayData data = SerponAwayData.get(context.getSource().getLevel());
        if (data == null) {
            context.getSource().sendFailure(Component.literal("只能在服务端维度切换蛇龙远行状态。"));
            return 0;
        }
        data.setSerponAway(value);
        context.getSource().sendSuccess(
                () -> Component.literal("蛇龙远行（SerponAway）已设为：" + (value ? "开启" : "关闭")), true);
        return 1;
    }

    private static int spawn(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer owner = source.getPlayer();
        if (owner == null) {
            source.sendFailure(Component.literal("只有玩家可以使用此命令。"));
            return 0;
        }

        String name = StringArgumentType.getString(context, "name").trim();
        if (!PlayerMobServantConfig.isMobName(name)) {
            source.sendFailure(Component.literal("玩家生物名称无效：" + name));
            return 0;
        }

        ServerLevel level = owner.serverLevel();
        PlayerMobServant servant = ModEntityTypes.PLAYER_MOB_SERVANT.get().create(level);
        if (servant == null) {
            source.sendFailure(Component.literal("无法创建玩家生物仆从。"));
            return 0;
        }

        servant.moveTo(owner.getX(), owner.getY(), owner.getZ(), owner.getYRot(), owner.getXRot());
        servant.setTrueOwner(owner);
        servant.setMobName(name);
        MobUtil.summonTame(servant, owner);
        servant.finalizeSpawn(level, level.getCurrentDifficultyAt(owner.blockPosition()),
                MobSpawnType.COMMAND, null, null);
        servant.setTrueOwner(owner);
        servant.setMobName(name);
        servant.setPersistenceRequired();

        if (!level.addFreshEntity(servant)) {
            servant.discard();
            source.sendFailure(Component.literal("玩家生物仆从生成失败。"));
            return 0;
        }

        source.sendSuccess(() -> Component.literal("已生成玩家生物仆从：").append(servant.getDisplayName()), true);
        return 1;
    }
}
