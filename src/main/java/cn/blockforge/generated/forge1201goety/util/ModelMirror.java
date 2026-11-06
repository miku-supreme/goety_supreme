package cn.blockforge.generated.goetysupreme.util;

import java.lang.reflect.Field;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;
import cn.blockforge.generated.goetysupreme.util.ModelMirror;

@OnlyIn(Dist.CLIENT)
public final class ModelMirror {

    private static Field cubePolygonsField;
    private static Field polygonVerticesField;
    private static Field polygonNormalField;
    private static Field vertexPositionField;
    private static Field cubesField;

    private ModelMirror() {
    }

    public static void mirrorCubeGeometry(ModelPart root) {
        for (ModelPart part : root.getAllParts().toList()) {
            for (Object cube : cubesOf(part)) {
                mirrorCube(cube);
            }
        }
    }

    private static java.util.List<?> cubesOf(ModelPart part) {
        try {
            if (cubesField == null) {
                for (Field f : ModelPart.class.getDeclaredFields()) {
                    if (!java.util.List.class.isAssignableFrom(f.getType())) {
                        continue;
                    }
                    java.lang.reflect.Type generic = f.getGenericType();
                    String hint = generic.getTypeName();
                    if (hint.contains("Cube") || f.getName().toLowerCase().contains("cube")) {
                        f.setAccessible(true);
                        cubesField = f;
                        break;
                    }
                }
            }
            if (cubesField != null) {
                Object value = cubesField.get(part);
                if (value instanceof java.util.List<?> list) {
                    return list;
                }
            }
        } catch (ReflectiveOperationException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
        }
        return java.util.List.of();
    }

    public static void mirrorPose(ModelPart root) {
        for (ModelPart part : root.getAllParts().toList()) {
            part.x = -part.x;
            part.yRot = -part.yRot;
            part.zRot = -part.zRot;
        }
    }

    private static void mirrorCube(Object cube) {
        try {
            if (cubePolygonsField == null) {
                for (Field f : cube.getClass().getDeclaredFields()) {
                    if (f.getType().isArray()
                            && f.getType().getComponentType().getSimpleName().equals("Polygon")) {
                        f.setAccessible(true);
                        cubePolygonsField = f;
                        break;
                    }
                }
            }
            if (cubePolygonsField == null) {
                return;
            }
            Object[] polygons = (Object[]) cubePolygonsField.get(cube);
            if (polygons == null) {
                return;
            }
            for (Object polygon : polygons) {
                mirrorPolygon(polygon);
            }
        } catch (ReflectiveOperationException ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
        }
    }

    private static void mirrorPolygon(Object polygon) throws ReflectiveOperationException {
        Class<?> type = polygon.getClass();
        if (polygonVerticesField == null) {
            for (Field f : type.getDeclaredFields()) {
                if (f.getType().isArray()
                        && f.getType().getComponentType().getSimpleName().equals("Vertex")) {
                    f.setAccessible(true);
                    polygonVerticesField = f;
                } else if (f.getType() == Vector3f.class) {
                    f.setAccessible(true);
                    polygonNormalField = f;
                }
            }
        }
        if (polygonNormalField != null) {
            Vector3f normal = (Vector3f) polygonNormalField.get(polygon);
            if (normal != null) {
                normal.x = -normal.x;
            }
        }
        if (polygonVerticesField == null) {
            return;
        }
        Object[] vertices = (Object[]) polygonVerticesField.get(polygon);
        if (vertices == null) {
            return;
        }
        for (Object vertex : vertices) {
            if (vertex == null) {
                continue;
            }
            if (vertexPositionField == null) {
                for (Field f : vertex.getClass().getDeclaredFields()) {
                    if (f.getType() == Vector3f.class) {
                        f.setAccessible(true);
                        vertexPositionField = f;
                        break;
                    }
                }
            }
            if (vertexPositionField == null) {
                continue;
            }
            Vector3f pos = (Vector3f) vertexPositionField.get(vertex);
            if (pos != null) {
                pos.x = -pos.x;
            }
        }
        for (int i = 0, j = vertices.length - 1; i < j; i++, j--) {
            Object tmp = vertices[i];
            vertices[i] = vertices[j];
            vertices[j] = tmp;
        }
    }

}
