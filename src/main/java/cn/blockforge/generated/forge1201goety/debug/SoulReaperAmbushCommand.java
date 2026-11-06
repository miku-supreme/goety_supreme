package cn.blockforge.generated.goetysupreme.debug;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.config.SoulReaperConfig;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.SoulReaperAmbush;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class SoulReaperAmbushCommand {

    private static final ResourceLocation WIGHT_ID =
            ResourceLocation.fromNamespaceAndPath("goety", "wight");

    private SoulReaperAmbushCommand() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        if (!Boolean.getBoolean("goety_supreme.debugCommands")) {
            return;
        }
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("goety_supreme")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("ambush")
                        .executes(context -> trigger(context, false))
                        .then(Commands.literal("force")
                                .executes(context -> trigger(context, true)))));
    }

    private static int trigger(CommandContext<CommandSourceStack> context, boolean force) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        if (player == null) {
            source.sendFailure(Component.literal("只有玩家可以使用此命令。"));
            return 0;
        }

        ServerLevel level = player.serverLevel();
        Mob wight = spawnWight(level, player);
        if (wight == null) {
            source.sendFailure(Component.literal("附近找不到能放下孤魂尸鬼的平地，换个开阔点的地方再试。"));
            return 0;
        }

        boolean queued = SoulReaperAmbush.hasPending(player);
        if (!queued && force) {
            queued = SoulReaperAmbush.forceAmbush(level, player, wight);
        }
        if (queued) {
            source.sendSuccess(() -> Component.literal("孤魂尸鬼已现身，渡魂之镰来袭仪式已排定："
                    + "开场 " + SoulReaperConfig.SPAWN_RITUAL_TICKS.get() + " 刻黑雾 / 特效；"
                    + "视线正对尸鬼（或贴到它 4 格内，和尸鬼自己的索敌判定一致）就在它死处顶替出场，"
                    + "一直没看到（最多等 " + SoulReaperConfig.SPAWN_SEEK_TIMEOUT.get()
                    + " 刻）也会在半空现身。"), true);
            return 1;
        }

        int souls = com.Polarice3.Goety.utils.SEHelper.getSESouls(player);
        source.sendSuccess(() -> Component.literal("孤魂尸鬼已现身，但这次没有触发渡魂之镰来袭："
                + "灵魂能量 " + souls + " / 门槛 " + SoulReaperConfig.SPAWN_SOUL_THRESHOLD.get()
                + "，达标后基础概率 "
                + (int) Math.round(SoulReaperConfig.SPAWN_CHANCE.get() * 100.0D) + "%。"), false);
        return 1;
    }

    @Nullable
    private static Mob spawnWight(ServerLevel level, ServerPlayer player) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(WIGHT_ID);
        if (type == null) {
            return null;
        }
        RandomSource random = player.getRandom();
        for (int attempt = 0; attempt < 32; attempt++) {
            double angle = random.nextDouble() * Math.PI * 2.0D;
            double distance = 6.0D + random.nextDouble() * 8.0D;
            double x = player.getX() + Math.cos(angle) * distance;
            double z = player.getZ() + Math.sin(angle) * distance;
            BlockPos ground = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    BlockPos.containing(x, player.getY(), z));
            if (Math.abs(ground.getY() - player.getY()) > 6.0D) {
                continue;
            }

            Entity created = type.create(level);
            if (!(created instanceof Mob mob)) {
                if (created != null) {
                    created.discard();
                }
                continue;
            }
            mob.moveTo(x, ground.getY(), z, random.nextFloat() * 360.0F, 0.0F);
            if (!level.noCollision(mob)) {
                mob.discard();
                continue;
            }
            mob.finalizeSpawn(level, level.getCurrentDifficultyAt(mob.blockPosition()),
                    MobSpawnType.COMMAND, null, null);
            if (!level.addFreshEntity(mob)) {
                mob.discard();
                continue;
            }
            return mob;
        }
        return null;
    }
}
