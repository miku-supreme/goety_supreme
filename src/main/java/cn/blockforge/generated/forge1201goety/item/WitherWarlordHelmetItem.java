package cn.blockforge.generated.goetysupreme.item;

import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import cn.blockforge.generated.goetysupreme.entity.AbstractWitherWarlord;
import cn.blockforge.generated.goetysupreme.util.WitherWarlordArmorMaterial;
import cn.blockforge.generated.goetysupreme.util.WitherWarlordHelmetEffects;
import cn.blockforge.generated.goetysupreme.item.WitherWarlordHelmetItem;
import cn.blockforge.generated.goetysupreme.model.WitherWarlordHelmetModel;
import cn.blockforge.generated.goetysupreme.mixin.CuriosFinderNetherCrownMixin;

public final class WitherWarlordHelmetItem extends ArmorItem implements ICurioItem {

    public WitherWarlordHelmetItem() {
        super(WitherWarlordArmorMaterial.INSTANCE, ArmorItem.Type.HELMET,
                new Item.Properties().rarity(Rarity.RARE));
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity,
            Consumer<T> onBroken) {
        if (entity instanceof AbstractWitherWarlord) {
            return 0;
        }
        return super.damageItem(stack, amount, entity, onBroken);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {

            private HumanoidModel<?> model;

            @Override
            public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack,
                    EquipmentSlot slot, HumanoidModel<?> original) {
                if (this.model == null) {
                    this.model = new WitherWarlordHelmetModel(Minecraft.getInstance()
                            .getEntityModels().bakeLayer(WitherWarlordHelmetModel.LAYER));
                }
                this.model.head.visible = slot == EquipmentSlot.HEAD;
                this.model.hat.visible = slot == EquipmentSlot.HEAD;
                this.model.body.visible = false;
                this.model.rightArm.visible = false;
                this.model.leftArm.visible = false;
                this.model.rightLeg.visible = false;
                this.model.leftLeg.visible = false;
                return this.model;
            }
        });
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return stack.copy();
    }
}
