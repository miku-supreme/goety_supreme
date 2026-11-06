package cn.blockforge.generated.goetysupreme.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import cn.blockforge.generated.goetysupreme.entity.AbstractSerpon;

public class SerponHornItem extends Item {

    private static final int PREPARE_TICKS = 2400;
    private static final int COOLDOWN_TICKS = 2400;
    private static final double RANGE = 32.0D;

    public SerponHornItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            AABB area = player.getBoundingBox().inflate(RANGE);
            for (AbstractSerpon serpon : level.getEntitiesOfClass(AbstractSerpon.class, area)) {
                serpon.setPrepareTimed(PREPARE_TICKS);
            }
            if (ModList.get().isLoaded("jerotesvillage")) {
                JerotesSerponCompat.prepare(level, area);
            }
            SoundEvent horn = ForgeRegistries.SOUND_EVENTS.getValue(
                    ResourceLocation.fromNamespaceAndPath("jerotesvillage", "carved_horn_use"));
            if (horn != null) {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), horn,
                        SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
            stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private static final class JerotesSerponCompat {

        private static void prepare(Level level, AABB area) {
            Class<?> serponClass;
            try {
                serponClass = Class.forName("com.jerotes.jerotesvillage.entity.Humanoid.Serpon.SerponEntity");
            } catch (ClassNotFoundException absent) {
                return;
            }
            java.lang.reflect.Method setPrepare;
            try {
                setPrepare = serponClass.getMethod("setPrepare", boolean.class, boolean.class);
            } catch (NoSuchMethodException absent) {
                return;
            }
            for (Entity entity : level.getEntitiesOfClass(Entity.class, area)) {
                if (!serponClass.isInstance(entity)) {
                    continue;
                }
                try {
                    setPrepare.invoke(entity, true, true);
                } catch (ReflectiveOperationException ignored) {
                }
            }
        }
    }
}
