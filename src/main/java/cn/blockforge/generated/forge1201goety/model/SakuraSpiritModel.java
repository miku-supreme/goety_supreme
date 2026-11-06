package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.model.SakuraSpiritModel;

public class SakuraSpiritModel<T extends LivingEntity> extends HumanoidModel<T> {
    public SakuraSpiritModel(ModelPart root) {
        super(root);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
            float red, float green, float blue, float alpha) {
    }
}
