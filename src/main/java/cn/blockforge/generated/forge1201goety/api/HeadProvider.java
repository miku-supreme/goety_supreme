package cn.blockforge.generated.goetysupreme.api;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import cn.blockforge.generated.goetysupreme.model.AbominableSnowGolemModel;
import cn.blockforge.generated.goetysupreme.model.EngenderGolemServantModel;
import cn.blockforge.generated.goetysupreme.api.HeadProvider;

public interface HeadProvider {

    ModelPart getHead();

    default void translateHead(PoseStack poseStack) {
        getHead().translateAndRotate(poseStack);
    }
}
