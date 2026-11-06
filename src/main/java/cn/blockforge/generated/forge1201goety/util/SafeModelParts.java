package cn.blockforge.generated.goetysupreme.util;

import java.util.List;
import java.util.Map;

import net.minecraft.client.model.geom.ModelPart;
import cn.blockforge.generated.goetysupreme.util.SafeModelParts;

public final class SafeModelParts {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("goety_supreme/models");

    private static final ModelPart DUMMY = new ModelPart(List.of(), Map.of());

    private SafeModelParts() {
    }

    public static ModelPart dummy() {
        return DUMMY;
    }

    public static ModelPart findOrNull(ModelPart parent, String name) {
        if (parent == null || parent == DUMMY) {
            return null;
        }
        return parent.hasChild(name) ? parent.getChild(name) : null;
    }

    public static ModelPart find(ModelPart parent, String name) {
        ModelPart part = findOrNull(parent, name);
        if (part == null) {
            LOGGER.warn("goety_supreme: 模型缺少骨骼 \"{}\"，已用空部件兜底（不影响游戏运行）", name);
            return DUMMY;
        }
        return part;
    }

    public static ModelPart findPath(ModelPart root, String... path) {
        ModelPart part = root;
        for (String name : path) {
            part = findOrNull(part, name);
            if (part == null) {
                LOGGER.warn("goety_supreme: 模型缺少骨骼路径 \"{}\"，已用空部件兜底（不影响游戏运行）",
                        String.join("/", path));
                return DUMMY;
            }
        }
        return part;
    }
}
