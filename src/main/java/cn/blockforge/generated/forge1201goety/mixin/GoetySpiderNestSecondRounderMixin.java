package cn.blockforge.generated.goetysupreme.mixin;

import cn.blockforge.generated.goetysupreme.compat.JerotesVillageCompat;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import com.Polarice3.Goety.common.blocks.entities.SpiderNestBlockEntity;
import com.Polarice3.Goety.common.entities.ModEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SpiderNestBlockEntity.class, remap = false)
public abstract class GoetySpiderNestSecondRounderMixin {

    private static final ResourceLocation GOETY_SUPREME$SECOND_ROUND_WORLD =
            ResourceLocation.fromNamespaceAndPath("jerotesvillage", "second_round_world");

    @Inject(method = "setVariant", at = @At("TAIL"), require = 0)
    private void goetySupreme$secondRounderSpiderInSecondRoundWorld(ItemStack itemStack, Level level,
            BlockPos blockPos, CallbackInfo callbackInfo) {
        if (!JerotesVillageCompat.isJerotesVillageLoaded()) {
            return;
        }
        if (!(level instanceof ServerLevel)) {
            return;
        }
        if (!GOETY_SUPREME$SECOND_ROUND_WORLD.equals(level.dimension().location())) {
            return;
        }
        SpiderNestBlockEntity nest = (SpiderNestBlockEntity) (Object) this;
        if (nest.getTrainMob() == ModEntityType.SPIDER_SERVANT.get()) {
            nest.setEntityType(ModEntityTypes.SECOND_ROUNDER_SPIDER_SERVANT.get());
        }
    }
}
