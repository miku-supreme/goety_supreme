package cn.blockforge.generated.goetysupreme.util;

import com.Polarice3.Goety.common.entities.neutral.AbstractWitherNecromancer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.WitherNecromancerAttackBoost;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WitherNecromancerAttackBoost {

    private static final UUID ATTACK_BOOST_ID = UUID.fromString("6f2f0d1a-3f5b-4f2e-9d0a-8b7c1e4d5a20");

    private static final String NBT_FLAG = "GoetySupremeWitherNecroAttack";

    private static final double ATTACK_BONUS = 6.0D;

    private WitherNecromancerAttackBoost() {
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) {
            return;
        }
        if (!(event.getEntity() instanceof AbstractWitherNecromancer necromancer)) {
            return;
        }
        CompoundTag data = necromancer.getPersistentData();
        if (data.getBoolean(NBT_FLAG)) {
            return;
        }
        AttributeInstance attack = necromancer.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attack != null && attack.getModifier(ATTACK_BOOST_ID) == null) {
            attack.addPermanentModifier(new AttributeModifier(
                    ATTACK_BOOST_ID, "goety_supreme:wither_necromancer_attack",
                    ATTACK_BONUS, AttributeModifier.Operation.ADDITION));
        }
        data.putBoolean(NBT_FLAG, true);
    }
}
