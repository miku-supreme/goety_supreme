package cn.blockforge.generated.goetysupreme.mixin;

import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {
        "com.Polarice3.Goety.common.entities.neutral.AbstractNecromancer$SummonZombieSpell",
        "com.Polarice3.Goety.common.entities.neutral.AbstractMossyNecromancer$SummonServantSpell",
        "com.Polarice3.Goety.common.entities.neutral.AbstractCairnNecromancer$SummonServantSpell",
        "com.Polarice3.Goety.common.entities.neutral.AbstractWitherNecromancer$SummonServantSpell",
        "com.Polarice3.Goety.common.entities.neutral.DrownedNecromancer$SummonServantSpell"
}, remap = false)
public abstract class GoetyNativeSummonLogMixin {

    @Unique
    private static final Logger GOETY_SUPREME$LOG = LogUtils.getLogger();

    @Unique
    private static final String GOETY_SUPREME$PREFIX = "[goety_supreme][native-summon] ";

    @Unique
    private int goetySupreme$nativeOwned;
    @Unique
    private int goetySupreme$nativeRoll = -1;
    @Unique
    private int goetySupreme$nativeSpawned;

    @Inject(method = "castSpell", at = @At("HEAD"), require = 0)
    private void goetySupreme$resetNativeLog(CallbackInfo callback) {
        this.goetySupreme$nativeOwned = 0;
        this.goetySupreme$nativeRoll = -1;
        this.goetySupreme$nativeSpawned = 0;
    }

    @Redirect(method = "castSpell", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;m_6443_(Ljava/lang/Class;"
                            + "Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"),
            require = 0)
    private List<LivingEntity> goetySupreme$captureNativeOwned(Level level, Class<LivingEntity> type,
            AABB area, Predicate<LivingEntity> predicate) {
        List<LivingEntity> list = level.getEntitiesOfClass(type, area, predicate);
        this.goetySupreme$nativeOwned = list.size();
        return list;
    }

    @Redirect(method = "castSpell", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;m_188503_(I)I"),
            require = 0)
    private int goetySupreme$captureNativeRoll(RandomSource random, int bound) {
        int roll = random.nextInt(bound);
        this.goetySupreme$nativeRoll = roll;
        return roll;
    }

    @Redirect(method = "castSpell", at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;m_7967_(Lnet/minecraft/world/entity/Entity;)Z"),
            require = 0)
    private boolean goetySupreme$captureNativeSpawned(ServerLevel level, Entity entity) {
        boolean added = level.addFreshEntity(entity);
        if (added) {
            ++this.goetySupreme$nativeSpawned;
        }
        return added;
    }

    @Inject(method = "castSpell", at = @At("RETURN"), require = 0)
    private void goetySupreme$logNativeSpawn(CallbackInfo callback) {
        GOETY_SUPREME$LOG.info("{}variant={} owned={} roll={} spawned={}",
                GOETY_SUPREME$PREFIX, goetySupreme$nativeVariant(),
                this.goetySupreme$nativeOwned, this.goetySupreme$nativeRoll,
                this.goetySupreme$nativeSpawned);
    }

    @Unique
    private String goetySupreme$nativeVariant() {
        String name = this.getClass().getName();
        if (name.contains("SummonZombieSpell")) {
            return "zombie";
        }
        if (name.contains("Mossy")) {
            return "mossy";
        }
        if (name.contains("Cairn")) {
            return "cairn";
        }
        if (name.contains("Wither")) {
            return "wither";
        }
        if (name.contains("Drowned")) {
            return "drowned";
        }
        return name.substring(name.lastIndexOf('.') + 1);
    }
}
