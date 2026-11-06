package cn.blockforge.generated.goetysupreme.event;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.entity.CorrosiverServant;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.network.ModNetwork;
import cn.blockforge.generated.goetysupreme.network.WitherFlightPacket;
import cn.blockforge.generated.goetysupreme.network.WitherRoarPacket;
import cn.blockforge.generated.goetysupreme.entity.WitherServant;
import cn.blockforge.generated.goetysupreme.util.WitherServantClientControls;
import cn.blockforge.generated.goetysupreme.event.WitherServantClientEvents;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID, value = Dist.CLIENT)
public final class WitherServantClientEvents {
    private static boolean lastAttackDown;
    private static boolean wasMounted;

    private WitherServantClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || mc.getConnection() == null) {
            WitherServantClientControls.releaseAll();
            lastAttackDown = false;
            wasMounted = false;
            return;
        }
        spawnAnnihilationClouds(mc);
        boolean ridingWither = player.getVehicle() instanceof WitherServant;
        boolean riding = ridingWither || player.getVehicle() instanceof CorrosiverServant;
        if (!riding) {
            if (wasMounted) {
                ModNetwork.CHANNEL.sendToServer(new WitherFlightPacket(false, false, false));
            }
            WitherServantClientControls.releaseAll();
            wasMounted = false;
            lastAttackDown = false;
            return;
        }
        wasMounted = true;
        boolean up = mc.options.keyJump.isDown();
        boolean down = mc.options.keySprint.isDown();
        boolean dismount = mc.options.keyShift.isDown();
        WitherServantClientControls.ascendHeld = up;
        WitherServantClientControls.descendHeld = down;
        ModNetwork.CHANNEL.sendToServer(new WitherFlightPacket(up, down, dismount));

        boolean attackDown = ridingWither && mc.options.keyAttack.isDown() && mc.isWindowActive();
        if (attackDown && !lastAttackDown) {
            ModNetwork.CHANNEL.sendToServer(WitherRoarPacket.INSTANCE);
        }
        lastAttackDown = attackDown;
    }

    private static void spawnAnnihilationClouds(Minecraft mc) {
        ClientLevel level = mc.level;
        if (level == null) {
            return;
        }
        for (Entity entity : level.entitiesForRendering()) {
            if (entity instanceof WitherServant servant && servant.isAnnihilating()) {
                spawnAnnihilationCloud(level, servant);
            }
        }
    }

    private static void spawnAnnihilationCloud(ClientLevel level, WitherServant servant) {
        RandomSource random = level.random;
        for (int i = 0; i < 64; ++i) {
            level.addParticle(ParticleTypes.LARGE_SMOKE,
                    servant.getX() + (random.nextDouble() - 0.5D) * 3.6D,
                    servant.getY() + random.nextDouble() * 3.8D,
                    servant.getZ() + (random.nextDouble() - 0.5D) * 3.6D,
                    random.nextDouble() - 0.5D, random.nextDouble() - 0.5D, random.nextDouble() - 0.5D);
        }
        for (int i = 0; i < 2; ++i) {
            level.addParticle(ParticleTypes.SMOKE,
                    servant.getX() + (random.nextDouble() - 0.5D) * servant.getBbWidth(),
                    servant.getY() + random.nextDouble() * servant.getBbHeight(),
                    servant.getZ() + (random.nextDouble() - 0.5D) * servant.getBbWidth(),
                    0.0D, 0.0D, 0.0D);
        }
    }
}
