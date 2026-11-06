package cn.blockforge.generated.goetysupreme.entity;

import net.minecraft.world.entity.LivingEntity;
import cn.blockforge.generated.goetysupreme.entity.RagingStarServant;
import cn.blockforge.generated.goetysupreme.mixin.EsotericTesseractStarSizeMixin;

public interface RagingStarServant {

    boolean isRagingStarred();

    void setRagingStarred(boolean starred);

    LivingEntity getTrueOwner();
}
