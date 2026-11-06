package cn.blockforge.generated.goetysupreme.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.entity.PrismarineSoldier;
import cn.blockforge.generated.goetysupreme.model.PrismarineSoldierModel;
import cn.blockforge.generated.goetysupreme.renderer.PrismarineSoldierRenderer;

public final class PrismarineSoldierModel extends EntityModel<PrismarineSoldier> implements ArmedModel {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(GeneratedMod.MOD_ID, "prismarine_soldier"), "main");

    public static final float PALM_DROP = 21.0F / 16.0F;

    private static final int ROOT = 0, UPPER = 1, HEAD = 2, BODY = 3, RARM = 4, LARM = 5, RLEG = 6, LLEG = 7;

    public static final int ARMOR_BONE_HEAD = HEAD;
    public static final int ARMOR_BONE_BODY = BODY;
    public static final int ARMOR_BONE_RIGHT_ARM = RARM;
    public static final int ARMOR_BONE_LEFT_ARM = LARM;
    public static final int ARMOR_BONE_RIGHT_LEG = RLEG;
    public static final int ARMOR_BONE_LEFT_LEG = LLEG;

    private static final float[][] BONE_BASE = {
        {0.0F, 0.0F, 0.0F}, {0.0F, 1.0F, 0.0F},
        {-2F, -24.0F, 1F}, {0F, -2.0F, 0F},
        {-10F, -23.0F, 0F}, {10F, -23.0F, 0F},
        {-4.0F, 1.0F, 0.0F}, {4.0F, 1.0F, 0.0F},
    };
    private static final int[] BONE_PARENT = {-1, 0, 1, 1, 1, 1, 0, 0};

    private static final float[][] QUAD_0 = {
    };
    private static final float[][] QUAD_1 = {
    };
    private static final float[][] QUAD_2 = {
    {10F, -16.0F, -7F, -6F, -16.0F, -7F, -6F, 0.0F, -7F, 10F, 0.0F, -7F, 0F, 0F, -1F, 0.25F, 0.125F, 0.125F, 0.125F, 0.125F, 0.25F, 0.25F, 0.25F, 0.8F},
    {10F, -16.0F, 9F, 10F, -16.0F, -7F, 10F, 0.0F, -7F, 10F, 0.0F, 9F, 1F, 0F, 0F, 0.375F, 0.125F, 0.25F, 0.125F, 0.25F, 0.25F, 0.375F, 0.25F, 0.6F},
    {-6F, -16.0F, 9F, 10F, -16.0F, 9F, 10F, 0.0F, 9F, -6F, 0.0F, 9F, 0F, 0F, 1F, 0.5F, 0.125F, 0.375F, 0.125F, 0.375F, 0.25F, 0.5F, 0.25F, 0.8F},
    {-6F, -16.0F, -7F, -6F, -16.0F, 9F, -6F, 0.0F, 9F, -6F, 0.0F, -7F, -1F, 0F, 0F, 0.125F, 0.125F, 0.0F, 0.125F, 0.0F, 0.25F, 0.125F, 0.25F, 0.6F},
    {-6F, -16.0F, -7F, 10F, -16.0F, -7F, 10F, -16.0F, 9F, -6F, -16.0F, 9F, 0F, 1F, 0F, 0.125F, 0.125F, 0.25F, 0.125F, 0.25F, 0.0F, 0.125F, 0.0F, 1.0F},
    {-6F, 0.0F, 9F, 10F, 0.0F, 9F, 10F, 0.0F, -7F, -6F, 0.0F, -7F, 0F, -1F, 0F, 0.25F, 0.0F, 0.375F, 0.0F, 0.375F, 0.125F, 0.25F, 0.125F, 0.5F},
    {10.5F, -12.5F, -8.0F, -6.0F, -12.5F, -8.0F, -6.0F, 4.0F, -8.0F, 10.5F, 4.0F, -8.0F, 0F, 0F, -1F, 0.75F, 0.125F, 0.625F, 0.125F, 0.625F, 0.25F, 0.75F, 0.25F, 0.8F},
    {10.5F, -12.5F, 8.5F, 10.5F, -12.5F, -8.0F, 10.5F, 4.0F, -8.0F, 10.5F, 4.0F, 8.5F, 1F, 0F, 0F, 0.875F, 0.125F, 0.75F, 0.125F, 0.75F, 0.25F, 0.875F, 0.25F, 0.6F},
    {-6.0F, -12.5F, 8.5F, 10.5F, -12.5F, 8.5F, 10.5F, 4.0F, 8.5F, -6.0F, 4.0F, 8.5F, 0F, 0F, 1F, 1.0F, 0.125F, 0.875F, 0.125F, 0.875F, 0.25F, 1.0F, 0.25F, 0.8F},
    {-6.0F, -12.5F, -8.0F, -6.0F, -12.5F, 8.5F, -6.0F, 4.0F, 8.5F, -6.0F, 4.0F, -8.0F, -1F, 0F, 0F, 0.625F, 0.125F, 0.5F, 0.125F, 0.5F, 0.25F, 0.625F, 0.25F, 0.6F},
    {-6.0F, -12.5F, -8.0F, 10.5F, -12.5F, -8.0F, 10.5F, -12.5F, 8.5F, -6.0F, -12.5F, 8.5F, 0F, 1F, 0F, 0.625F, 0.125F, 0.75F, 0.125F, 0.75F, 0.0F, 0.625F, 0.0F, 1.0F},
    {-6.0F, 4.0F, 8.5F, 10.5F, 4.0F, 8.5F, 10.5F, 4.0F, -8.0F, -6.0F, 4.0F, -8.0F, 0F, -1F, 0F, 0.75F, 0.0F, 0.875F, 0.0F, 0.875F, 0.125F, 0.75F, 0.125F, 0.5F},
    };
    private static final float[][] QUAD_3 = {
    {8F, -23.0F, -3F, -8F, -23.0F, -3F, -8F, 1.0F, -3F, 8F, 1.0F, -3F, 0F, 0F, -1F, 0.4375F, 0.3125F, 0.3125F, 0.3125F, 0.3125F, 0.5F, 0.4375F, 0.5F, 0.8F},
    {8F, -23.0F, 5F, 8F, -23.0F, -3F, 8F, 1.0F, -3F, 8F, 1.0F, 5F, 1F, 0F, 0F, 0.5F, 0.3125F, 0.4375F, 0.3125F, 0.4375F, 0.5F, 0.5F, 0.5F, 0.6F},
    {-8F, -23.0F, 5F, 8F, -23.0F, 5F, 8F, 1.0F, 5F, -8F, 1.0F, 5F, 0F, 0F, 1F, 0.625F, 0.3125F, 0.5F, 0.3125F, 0.5F, 0.5F, 0.625F, 0.5F, 0.8F},
    {-8F, -23.0F, -3F, -8F, -23.0F, 5F, -8F, 1.0F, 5F, -8F, 1.0F, -3F, -1F, 0F, 0F, 0.3125F, 0.3125F, 0.25F, 0.3125F, 0.25F, 0.5F, 0.3125F, 0.5F, 0.6F},
    {-8F, -23.0F, -3F, 8F, -23.0F, -3F, 8F, -23.0F, 5F, -8F, -23.0F, 5F, 0F, 1F, 0F, 0.3125F, 0.3125F, 0.4375F, 0.3125F, 0.4375F, 0.25F, 0.3125F, 0.25F, 1.0F},
    {-8F, 1.0F, 5F, 8F, 1.0F, 5F, 8F, 1.0F, -3F, -8F, 1.0F, -3F, 0F, -1F, 0F, 0.4375F, 0.25F, 0.5625F, 0.25F, 0.5625F, 0.3125F, 0.4375F, 0.3125F, 0.5F},
    {4.25F, -17.25F, -3.25F, -8.25F, -17.25F, -3.25F, -8.25F, 1.25F, -3.25F, 4.25F, 1.25F, -3.25F, 0F, 0F, -1F, 0.42188F, 0.5625F, 0.32812F, 0.5625F, 0.32812F, 0.70312F, 0.42188F, 0.70312F, 0.8F},
    {4.25F, -17.25F, 3.25F, 4.25F, -17.25F, -3.25F, 4.25F, 1.25F, -3.25F, 4.25F, 1.25F, 3.25F, 1F, 0F, 0F, 0.46875F, 0.5625F, 0.42188F, 0.5625F, 0.42188F, 0.70312F, 0.46875F, 0.70312F, 0.6F},
    {-8.25F, -17.25F, 3.25F, 4.25F, -17.25F, 3.25F, 4.25F, 1.25F, 3.25F, -8.25F, 1.25F, 3.25F, 0F, 0F, 1F, 0.5625F, 0.5625F, 0.46875F, 0.5625F, 0.46875F, 0.70312F, 0.5625F, 0.70312F, 0.8F},
    {-8.25F, -17.25F, -3.25F, -8.25F, -17.25F, 3.25F, -8.25F, 1.25F, 3.25F, -8.25F, 1.25F, -3.25F, -1F, 0F, 0F, 0.32812F, 0.5625F, 0.28125F, 0.5625F, 0.28125F, 0.70312F, 0.32812F, 0.70312F, 0.6F},
    {-8.25F, -17.25F, -3.25F, 4.25F, -17.25F, -3.25F, 4.25F, -17.25F, 3.25F, -8.25F, -17.25F, 3.25F, 0F, 1F, 0F, 0.32812F, 0.5625F, 0.42188F, 0.5625F, 0.42188F, 0.51562F, 0.32812F, 0.51562F, 1.0F},
    {-8.25F, 1.25F, 3.25F, 4.25F, 1.25F, 3.25F, 4.25F, 1.25F, -3.25F, -8.25F, 1.25F, -3.25F, 0F, -1F, 0F, 0.42188F, 0.51562F, 0.51562F, 0.51562F, 0.51562F, 0.5625F, 0.42188F, 0.5625F, 0.5F},
    };
    private static final float[][] QUAD_4 = {
    {2F, -2.0F, -3F, -6F, -2.0F, -3F, -6F, 22.0F, -3F, 2F, 22.0F, -3F, 0F, 0F, -1F, 0.75F, 0.3125F, 0.6875F, 0.3125F, 0.6875F, 0.5F, 0.75F, 0.5F, 0.8F},
    {2F, -2.0F, 5F, 2F, -2.0F, -3F, 2F, 22.0F, -3F, 2F, 22.0F, 5F, 1F, 0F, 0F, 0.8125F, 0.3125F, 0.75F, 0.3125F, 0.75F, 0.5F, 0.8125F, 0.5F, 0.6F},
    {-6F, -2.0F, 5F, 2F, -2.0F, 5F, 2F, 22.0F, 5F, -6F, 22.0F, 5F, 0F, 0F, 1F, 0.875F, 0.3125F, 0.8125F, 0.3125F, 0.8125F, 0.5F, 0.875F, 0.5F, 0.8F},
    {-6F, -2.0F, -3F, -6F, -2.0F, 5F, -6F, 22.0F, 5F, -6F, 22.0F, -3F, -1F, 0F, 0F, 0.6875F, 0.3125F, 0.625F, 0.3125F, 0.625F, 0.5F, 0.6875F, 0.5F, 0.6F},
    {-6F, -2.0F, -3F, 2F, -2.0F, -3F, 2F, -2.0F, 5F, -6F, -2.0F, 5F, 0F, 1F, 0F, 0.6875F, 0.3125F, 0.75F, 0.3125F, 0.75F, 0.25F, 0.6875F, 0.25F, 1.0F},
    {-6F, 22.0F, 5F, 2F, 22.0F, 5F, 2F, 22.0F, -3F, -6F, 22.0F, -3F, 0F, -1F, 0F, 0.75F, 0.25F, 0.8125F, 0.25F, 0.8125F, 0.3125F, 0.75F, 0.3125F, 0.5F},
    {2.25F, -3.25F, -4.25F, -8.25F, -3.25F, -4.25F, -8.25F, 21.25F, -4.25F, 2.25F, 21.25F, -4.25F, 0F, 0F, -1F, 0.75F, 0.57031F, 0.67188F, 0.57031F, 0.67188F, 0.75781F, 0.75F, 0.75781F, 0.8F},
    {2.25F, -3.25F, 6.25F, 2.25F, -3.25F, -4.25F, 2.25F, 21.25F, -4.25F, 2.25F, 21.25F, 6.25F, 1F, 0F, 0F, 0.82812F, 0.57031F, 0.75F, 0.57031F, 0.75F, 0.75781F, 0.82812F, 0.75781F, 0.6F},
    {-8.25F, -3.25F, 6.25F, 2.25F, -3.25F, 6.25F, 2.25F, 21.25F, 6.25F, -8.25F, 21.25F, 6.25F, 0F, 0F, 1F, 0.90625F, 0.57031F, 0.82812F, 0.57031F, 0.82812F, 0.75781F, 0.90625F, 0.75781F, 0.8F},
    {-8.25F, -3.25F, -4.25F, -8.25F, -3.25F, 6.25F, -8.25F, 21.25F, 6.25F, -8.25F, 21.25F, -4.25F, -1F, 0F, 0F, 0.67188F, 0.57031F, 0.59375F, 0.57031F, 0.59375F, 0.75781F, 0.67188F, 0.75781F, 0.6F},
    {-8.25F, -3.25F, -4.25F, 2.25F, -3.25F, -4.25F, 2.25F, -3.25F, 6.25F, -8.25F, -3.25F, 6.25F, 0F, 1F, 0F, 0.67188F, 0.57031F, 0.75F, 0.57031F, 0.75F, 0.49219F, 0.67188F, 0.49219F, 1.0F},
    {-8.25F, 21.25F, 6.25F, 2.25F, 21.25F, 6.25F, 2.25F, 21.25F, -4.25F, -8.25F, 21.25F, -4.25F, 0F, -1F, 0F, 0.75F, 0.49219F, 0.82812F, 0.49219F, 0.82812F, 0.57031F, 0.75F, 0.57031F, 0.5F},
    };
    private static final float[][] QUAD_5 = {
    {6F, -2.0F, -3F, -2F, -2.0F, -3F, -2F, 22.0F, -3F, 6F, 22.0F, -3F, 0F, 0F, -1F, 0.625F, 0.8125F, 0.5625F, 0.8125F, 0.5625F, 1.0F, 0.625F, 1.0F, 0.8F},
    {6F, -2.0F, 5F, 6F, -2.0F, -3F, 6F, 22.0F, -3F, 6F, 22.0F, 5F, 1F, 0F, 0F, 0.6875F, 0.8125F, 0.625F, 0.8125F, 0.625F, 1.0F, 0.6875F, 1.0F, 0.6F},
    {-2F, -2.0F, 5F, 6F, -2.0F, 5F, 6F, 22.0F, 5F, -2F, 22.0F, 5F, 0F, 0F, 1F, 0.75F, 0.8125F, 0.6875F, 0.8125F, 0.6875F, 1.0F, 0.75F, 1.0F, 0.8F},
    {-2F, -2.0F, -3F, -2F, -2.0F, 5F, -2F, 22.0F, 5F, -2F, 22.0F, -3F, -1F, 0F, 0F, 0.5625F, 0.8125F, 0.5F, 0.8125F, 0.5F, 1.0F, 0.5625F, 1.0F, 0.6F},
    {-2F, -2.0F, -3F, 6F, -2.0F, -3F, 6F, -2.0F, 5F, -2F, -2.0F, 5F, 0F, 1F, 0F, 0.5625F, 0.8125F, 0.625F, 0.8125F, 0.625F, 0.75F, 0.5625F, 0.75F, 1.0F},
    {-2F, 22.0F, 5F, 6F, 22.0F, 5F, 6F, 22.0F, -3F, -2F, 22.0F, -3F, 0F, -1F, 0F, 0.625F, 0.75F, 0.6875F, 0.75F, 0.6875F, 0.8125F, 0.625F, 0.8125F, 0.5F},
    };
    private static final float[][] QUAD_6 = {
    {4.15F, -1.0F, -3F, -3.85F, -1.0F, -3F, -3.85F, 23.0F, -3F, 4.15F, 23.0F, -3F, 0F, 0F, -1F, 0.375F, 0.8125F, 0.3125F, 0.8125F, 0.3125F, 1.0F, 0.375F, 1.0F, 0.8F},
    {4.15F, -1.0F, 5F, 4.15F, -1.0F, -3F, 4.15F, 23.0F, -3F, 4.15F, 23.0F, 5F, 1F, 0F, 0F, 0.4375F, 0.8125F, 0.375F, 0.8125F, 0.375F, 1.0F, 0.4375F, 1.0F, 0.6F},
    {-3.85F, -1.0F, 5F, 4.15F, -1.0F, 5F, 4.15F, 23.0F, 5F, -3.85F, 23.0F, 5F, 0F, 0F, 1F, 0.5F, 0.8125F, 0.4375F, 0.8125F, 0.4375F, 1.0F, 0.5F, 1.0F, 0.8F},
    {-3.85F, -1.0F, -3F, -3.85F, -1.0F, 5F, -3.85F, 23.0F, 5F, -3.85F, 23.0F, -3F, -1F, 0F, 0F, 0.3125F, 0.8125F, 0.25F, 0.8125F, 0.25F, 1.0F, 0.3125F, 1.0F, 0.6F},
    {-3.85F, -1.0F, -3F, 4.15F, -1.0F, -3F, 4.15F, -1.0F, 5F, -3.85F, -1.0F, 5F, 0F, 1F, 0F, 0.3125F, 0.8125F, 0.375F, 0.8125F, 0.375F, 0.75F, 0.3125F, 0.75F, 1.0F},
    {-3.85F, 23.0F, 5F, 4.15F, 23.0F, 5F, 4.15F, 23.0F, -3F, -3.85F, 23.0F, -3F, 0F, -1F, 0F, 0.375F, 0.75F, 0.4375F, 0.75F, 0.4375F, 0.8125F, 0.375F, 0.8125F, 0.5F},
    {4.4F, -1.25F, -3.25F, -4.1F, -1.25F, -3.25F, -4.1F, 23.25F, -3.25F, 4.4F, 23.25F, -3.25F, 0F, 0F, -1F, 0.125F, 0.8125F, 0.0625F, 0.8125F, 0.0625F, 1.0F, 0.125F, 1.0F, 0.8F},
    {4.4F, -1.25F, 5.25F, 4.4F, -1.25F, -3.25F, 4.4F, 23.25F, -3.25F, 4.4F, 23.25F, 5.25F, 1F, 0F, 0F, 0.1875F, 0.8125F, 0.125F, 0.8125F, 0.125F, 1.0F, 0.1875F, 1.0F, 0.6F},
    {-4.1F, -1.25F, 5.25F, 4.4F, -1.25F, 5.25F, 4.4F, 23.25F, 5.25F, -4.1F, 23.25F, 5.25F, 0F, 0F, 1F, 0.25F, 0.8125F, 0.1875F, 0.8125F, 0.1875F, 1.0F, 0.25F, 1.0F, 0.8F},
    {-4.1F, -1.25F, -3.25F, -4.1F, -1.25F, 5.25F, -4.1F, 23.25F, 5.25F, -4.1F, 23.25F, -3.25F, -1F, 0F, 0F, 0.0625F, 0.8125F, 0.0F, 0.8125F, 0.0F, 1.0F, 0.0625F, 1.0F, 0.6F},
    {-4.1F, -1.25F, -3.25F, 4.4F, -1.25F, -3.25F, 4.4F, -1.25F, 5.25F, -4.1F, -1.25F, 5.25F, 0F, 1F, 0F, 0.0625F, 0.8125F, 0.125F, 0.8125F, 0.125F, 0.75F, 0.0625F, 0.75F, 1.0F},
    {-4.1F, 23.25F, 5.25F, 4.4F, 23.25F, 5.25F, 4.4F, 23.25F, -3.25F, -4.1F, 23.25F, -3.25F, 0F, -1F, 0F, 0.125F, 0.75F, 0.1875F, 0.75F, 0.1875F, 0.8125F, 0.125F, 0.8125F, 0.5F},
    };
    private static final float[][] QUAD_7 = {
    {3.85F, -1.0F, -3F, -4.15F, -1.0F, -3F, -4.15F, 23.0F, -3F, 3.85F, 23.0F, -3F, 0F, 0F, -1F, 0.125F, 0.8125F, 0.0625F, 0.8125F, 0.0625F, 1.0F, 0.125F, 1.0F, 0.8F},
    {3.85F, -1.0F, 5F, 3.85F, -1.0F, -3F, 3.85F, 23.0F, -3F, 3.85F, 23.0F, 5F, 1F, 0F, 0F, 0.1875F, 0.8125F, 0.125F, 0.8125F, 0.125F, 1.0F, 0.1875F, 1.0F, 0.6F},
    {-4.15F, -1.0F, 5F, 3.85F, -1.0F, 5F, 3.85F, 23.0F, 5F, -4.15F, 23.0F, 5F, 0F, 0F, 1F, 0.25F, 0.8125F, 0.1875F, 0.8125F, 0.1875F, 1.0F, 0.25F, 1.0F, 0.8F},
    {-4.15F, -1.0F, -3F, -4.15F, -1.0F, 5F, -4.15F, 23.0F, 5F, -4.15F, 23.0F, -3F, -1F, 0F, 0F, 0.0625F, 0.8125F, 0.0F, 0.8125F, 0.0F, 1.0F, 0.0625F, 1.0F, 0.6F},
    {-4.15F, -1.0F, -3F, 3.85F, -1.0F, -3F, 3.85F, -1.0F, 5F, -4.15F, -1.0F, 5F, 0F, 1F, 0F, 0.0625F, 0.8125F, 0.125F, 0.8125F, 0.125F, 0.75F, 0.0625F, 0.75F, 1.0F},
    {-4.15F, 23.0F, 5F, 3.85F, 23.0F, 5F, 3.85F, 23.0F, -3F, -4.15F, 23.0F, -3F, 0F, -1F, 0F, 0.125F, 0.75F, 0.1875F, 0.75F, 0.1875F, 0.8125F, 0.125F, 0.8125F, 0.5F},
    {4.1F, -1.25F, -3.25F, -4.4F, -1.25F, -3.25F, -4.4F, 23.25F, -3.25F, 4.1F, 23.25F, -3.25F, 0F, 0F, -1F, 0.125F, 0.3125F, 0.0625F, 0.3125F, 0.0625F, 0.5F, 0.125F, 0.5F, 0.8F},
    {4.1F, -1.25F, 5.25F, 4.1F, -1.25F, -3.25F, 4.1F, 23.25F, -3.25F, 4.1F, 23.25F, 5.25F, 1F, 0F, 0F, 0.1875F, 0.3125F, 0.125F, 0.3125F, 0.125F, 0.5F, 0.1875F, 0.5F, 0.6F},
    {-4.4F, -1.25F, 5.25F, 4.1F, -1.25F, 5.25F, 4.1F, 23.25F, 5.25F, -4.4F, 23.25F, 5.25F, 0F, 0F, 1F, 0.25F, 0.3125F, 0.1875F, 0.3125F, 0.1875F, 0.5F, 0.25F, 0.5F, 0.8F},
    {-4.4F, -1.25F, -3.25F, -4.4F, -1.25F, 5.25F, -4.4F, 23.25F, 5.25F, -4.4F, 23.25F, -3.25F, -1F, 0F, 0F, 0.0625F, 0.3125F, 0.0F, 0.3125F, 0.0F, 0.5F, 0.0625F, 0.5F, 0.6F},
    {-4.4F, -1.25F, -3.25F, 4.1F, -1.25F, -3.25F, 4.1F, -1.25F, 5.25F, -4.4F, -1.25F, 5.25F, 0F, 1F, 0F, 0.0625F, 0.3125F, 0.125F, 0.3125F, 0.125F, 0.25F, 0.0625F, 0.25F, 1.0F},
    {-4.4F, 23.25F, 5.25F, 4.1F, 23.25F, 5.25F, 4.1F, 23.25F, -3.25F, -4.4F, 23.25F, -3.25F, 0F, -1F, 0F, 0.125F, 0.25F, 0.1875F, 0.25F, 0.1875F, 0.3125F, 0.125F, 0.3125F, 0.5F},
    };
    private static final float[][][] QUADS = {QUAD_0, QUAD_1, QUAD_2, QUAD_3, QUAD_4, QUAD_5, QUAD_6, QUAD_7};

    private record Track(int bone, boolean rotation, float[] keys) {}

    private static final float[] CLIP_LENGTH = {3F, 2F, 1.75F, 2.5F, 2F, 2F, 3.5F, 1.5F, 1.75F, 3F};
    private static final boolean[] CLIP_LOOP = {true, true, false, false, true, false, false, false, false, true};

    public static final float BOW_DRAW_PHASE = 1.0F;

    private static final float BOW_RELEASE_PHASE = 1.25F;

    private static final float BLOCK_HOLD_PHASE = 0.6F;
    private static final Track[][] CLIPS = {
        {new Track(0, false, new float[]{0.0F, 0.0F, 0.2956F, 0.0F, 0.75F, 0.0F, 0.051F, 0.0F, 1.5F, 0.0F, -0.2956F, 0.0F, 2.25F, 0.0F, -0.051F, 0.0F, 3.0F, 0.0F, 0.2956F, 0.0F}),
         new Track(2, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.75F, -0.0F, -4.5F, -0.0F, 1.5F, -0.0F, 0.0F, -0.0F, 2.25F, -0.0F, 4.5F, -0.0F, 3.0F, -0.0F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, -1.2422F, 0.0F, -0.0F, 0.75F, -1.8157F, 0.0F, -0.0F, 1.5F, 1.2422F, 0.0F, -0.0F, 2.25F, 1.8157F, 0.0F, -0.0F, 3.0F, -1.2422F, 0.0F, -0.0F}),
         new Track(5, true, new float[]{0.0F, -1.3551F, 0.0F, -0.0F, 0.75F, -1.9808F, 0.0F, -0.0F, 1.5F, 1.3551F, 0.0F, -0.0F, 2.25F, 1.9808F, 0.0F, -0.0F, 3.0F, -1.3551F, 0.0F, -0.0F})},
        {new Track(1, false, new float[]{0.0F, 0.0F, 2.0F, 0.0F, 0.25F, 0.0F, -0.0F, 0.0F, 0.70833F, 0.0F, -0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, 1.375F, 0.0F, -0.0F, 0.0F, 1.66667F, 0.0F, -0.0F, 0.0F, 2.0F, 0.0F, 2.0F, 0.0F}),
         new Track(4, true, new float[]{0.0F, 13.1828F, 6.2049F, 6.5402F, 0.25F, 12.6044F, 7.3212F, 1.6321F, 0.70833F, 12.6044F, 7.3212F, 1.6321F, 1.0F, 13.1828F, 6.2049F, 6.5402F, 1.375F, 12.6044F, 7.3212F, 1.6321F, 1.66667F, 12.6044F, 7.3212F, 1.6321F, 2.0F, 13.1828F, 6.2049F, 6.5402F}),
         new Track(5, true, new float[]{0.0F, 13.1828F, -6.2049F, -6.5402F, 0.25F, 12.6044F, -7.3212F, -1.6321F, 0.70833F, 12.6044F, -7.3212F, -1.6321F, 1.0F, 13.1828F, -6.2049F, -6.5402F, 1.375F, 12.6044F, -7.3212F, -1.6321F, 1.66667F, 12.6044F, -7.3212F, -1.6321F, 2.0F, 13.1828F, -6.2049F, -6.5402F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 4.0F, 0.5F, 0.0F, -3.0F, -2.0F, 1.0F, 0.0F, -0.0F, -4.0F, 1.95833F, 0.0F, -0.0F, 4.0F}),
         new Track(7, false, new float[]{0.0F, 0.0F, -0.0F, -4.0F, 1.0F, 0.0F, -0.0F, 4.0F, 1.5F, 0.0F, -3.0F, -2.0F, 2.0F, 0.0F, -0.0F, -4.0F})},
        {new Track(1, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.20833F, 20.5138F, 24.8985F, 2.3273F, 0.5F, -1.9862F, 24.8985F, 2.3273F, 0.625F, 30.1509F, 1.2775F, -6.1088F, 0.75F, 20.1005F, -22.3435F, -14.545F, 1.0F, 20.1005F, -22.3435F, -14.545F, 1.125F, 28.8588F, -9.3169F, -5.6977F, 1.25F, 6.55F, 6.064F, 3.0582F, 1.5F, -1.9862F, 24.8985F, 2.3273F, 1.75F, -0.0F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, 13.1828F, 6.2049F, 6.5402F, 0.20833F, 13.1828F, 6.2049F, 6.5402F, 0.5F, 30.301F, 6.2049F, 6.5402F, 0.75F, -70.6797F, -17.9912F, 38.8166F, 1.0F, -76.2904F, -26.7057F, 41.2776F, 1.125F, -19.2405F, -29.3073F, 43.6025F, 1.25F, -11.2904F, -26.7057F, 41.2776F, 1.5F, 30.301F, 6.2049F, 6.5402F, 1.75F, 13.1828F, 6.2049F, 6.5402F}),
         new Track(5, true, new float[]{0.0F, 13.1828F, -6.2049F, -6.5402F, 0.20833F, 23.1828F, -6.2049F, -6.5402F, 0.5F, -95.9512F, 48.212F, -59.6691F, 0.625F, -46.4964F, 46.2773F, -65.7085F, 0.75F, -20.1171F, 28.6076F, -60.399F, 1.0F, 41.9899F, -3.0288F, -19.9894F, 1.25F, 24.4899F, -3.0288F, -19.9894F, 1.75F, 13.1828F, -6.2049F, -6.5402F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.25F, 0.0F, -3.0F, -3.0F, 0.5F, 0.0F, -0.0F, -6.0F, 1.5F, 0.0F, -0.0F, -6.0F, 1.625F, 0.0F, -3.0F, -3.0F, 1.75F, 0.0F, -0.0F, 0.0F})},
        {new Track(1, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.125F, -22.5F, 0.0F, -0.0F, 0.25F, -22.5F, 0.0F, -0.0F, 0.375F, 32.5F, 0.0F, -0.0F, 0.5F, 32.5F, 0.0F, -0.0F, 2.25F, 32.5F, 0.0F, -0.0F, 2.5F, -0.0F, 0.0F, -0.0F}),
         new Track(1, false, new float[]{0.5F, 0.0F, -0.0F, 0.0F, 0.54167F, 1.0F, -0.0F, 0.0F, 0.625F, -1.0F, -0.0F, 0.0F, 0.70833F, 1.0F, -0.0F, 0.0F, 0.79167F, -1.0F, -0.0F, 0.0F, 0.875F, 1.0F, -0.0F, 0.0F, 0.95833F, -1.0F, -0.0F, 0.0F, 1.04167F, 1.0F, -0.0F, 0.0F, 1.125F, -1.0F, -0.0F, 0.0F, 1.20833F, 1.0F, -0.0F, 0.0F, 1.29167F, -1.0F, -0.0F, 0.0F, 1.375F, 1.0F, -0.0F, 0.0F, 1.45833F, -1.0F, -0.0F, 0.0F, 1.54167F, 1.0F, -0.0F, 0.0F, 1.625F, -1.0F, -0.0F, 0.0F}),
         new Track(2, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.25F, -27.5F, 0.0F, -0.0F, 1.5F, -27.5F, 0.0F, -0.0F, 1.83333F, 17.5F, 0.0F, -0.0F, 2.25F, -0.0F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, 13.1828F, 6.2049F, 6.5402F, 0.125F, -109.8102F, -31.9308F, 19.1981F, 0.25F, -109.8102F, -31.9308F, 19.1981F, 0.375F, -74.4269F, 1.1875F, 58.0906F, 0.5F, -74.4269F, 1.1875F, 58.0906F, 1.625F, -74.4269F, 1.1875F, 58.0906F, 1.875F, 75.6828F, 6.2049F, 6.5402F, 2.5F, 13.1828F, 6.2049F, 6.5402F}),
         new Track(5, true, new float[]{0.0F, 13.1828F, -6.2049F, -6.5402F, 0.125F, -109.8102F, 31.9308F, -19.1981F, 0.25F, -109.8102F, 31.9308F, -19.1981F, 0.375F, -66.4996F, 9.7885F, -76.8215F, 0.5F, -66.4996F, 9.7885F, -76.8215F, 1.625F, -66.4996F, 9.7885F, -76.8215F, 1.875F, 75.6828F, -6.2049F, -6.5402F, 2.5F, 13.1828F, -6.2049F, -6.5402F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.125F, 0.0F, -6.0F, -3.0F, 0.29167F, 0.0F, -6.0F, -3.0F, 0.41667F, 0.0F, -0.0F, -9.0F, 1.91667F, 0.0F, -0.0F, -9.0F, 2.5F, 0.0F, -0.0F, 0.0F})},
        {new Track(1, true, new float[]{0.0F, 15.0F, 0.0F, -0.0F}),
         new Track(1, false, new float[]{0.0F, 0.0F, 2.0F, 0.0F, 0.25F, 0.0F, -0.0F, 0.0F, 0.70833F, 0.0F, -0.0F, 0.0F, 1.0F, 0.0F, 2.0F, 0.0F, 1.375F, 0.0F, -0.0F, 0.0F, 1.66667F, 0.0F, -0.0F, 0.0F, 2.0F, 0.0F, 2.0F, 0.0F}),
         new Track(2, true, new float[]{0.0F, -12.5F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, -47.7815F, -56.0841F, -26.1373F, 0.5F, -52.7815F, -56.0841F, -26.1373F, 1.0F, -47.7815F, -56.0841F, -26.1373F, 1.5F, -52.7815F, -56.0841F, -26.1373F, 2.0F, -47.7815F, -56.0841F, -26.1373F}),
         new Track(5, true, new float[]{0.0F, -74.8102F, 52.5604F, -73.2279F, 0.5F, -76.3049F, 47.7178F, -75.1729F, 1.0F, -74.8102F, 52.5604F, -73.2279F, 1.5F, -76.3049F, 47.7178F, -75.1729F, 2.0F, -74.8102F, 52.5604F, -73.2279F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 4.0F, 0.5F, 0.0F, -3.0F, -2.0F, 1.0F, 0.0F, -0.0F, -4.0F, 2.0F, 0.0F, -0.0F, 4.0F}),
         new Track(7, false, new float[]{0.0F, 0.0F, -0.0F, -4.0F, 1.0F, 0.0F, -0.0F, 4.0F, 1.5F, 0.0F, -3.0F, -2.0F, 2.0F, 0.0F, -0.0F, -4.0F})},
        {new Track(1, true, new float[]{0.0F, 15.0F, 0.0F, -0.0F, 1.0F, -0.0F, 0.0F, -0.0F, 1.25F, -0.0F, 0.0F, -0.0F, 1.75F, 15.0F, 0.0F, -0.0F}),
         new Track(1, false, new float[]{0.0F, 0.0F, 2.0F, 0.0F, 1.25F, 0.0F, 2.0F, 0.0F, 1.33333F, 0.0F, 2.0F, 6.0F, 1.41667F, 0.0F, 2.0F, 0.0F, 1.75F, 0.0F, 2.0F, 0.0F}),
         new Track(2, true, new float[]{0.0F, -15.0F, 0.0F, -0.0F, 1.0F, -0.0F, 0.0F, 17.5F, 1.16667F, -0.0F, 0.0F, 17.5F, 1.75F, -15.0F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, -47.7815F, -56.0841F, -26.1373F, 1.0F, -51.9328F, -76.7076F, -51.1538F, 1.25F, -51.9328F, -76.7076F, -51.1538F, 1.33333F, 2.0335F, 25.1556F, 19.0481F, 1.75F, -47.7815F, -56.0841F, -26.1373F}),
         new Track(5, true, new float[]{0.0F, -74.8103F, 52.5602F, -73.2283F, 1.0F, -80.8331F, 1.0259F, -85.2275F, 1.25F, -80.8331F, 1.0259F, -85.2275F, 1.75F, -74.8103F, 52.5602F, -73.2283F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.25F, 0.0F, -4.0F, 2.0F, 0.5F, 0.0F, -0.0F, 4.0F, 1.75F, 0.0F, -0.0F, 4.0F, 1.875F, 0.0F, -2.0F, 2.81F, 2.0F, 0.0F, -0.0F, 0.0F}),
         new Track(7, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.5F, 0.0F, -0.0F, 0.0F, 0.75F, 0.0F, -2.0F, -2.0F, 1.0F, 0.0F, -0.0F, -4.0F, 1.25F, 0.0F, -0.0F, -4.0F, 1.5F, 0.0F, -0.0F, -4.0F, 1.625F, 0.0F, -2.0F, -2.0F, 1.75F, 0.0F, -0.0F, 0.0F})},
        {new Track(0, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 1.25F, -0.0F, 0.0F, -0.0F, 1.5F, -0.0F, 90.0F, -0.0F, 2.0F, -0.0F, 90.0F, -0.0F, 2.45833F, -0.0F, 90.0F, -0.0F, 3.0F, -0.0F, 0.0F, -0.0F}),
         new Track(1, true, new float[]{0.0F, 15.0F, 0.0F, -0.0F, 0.25F, 36.41F, 0.0F, -0.0F, 0.5F, 36.41F, 0.0F, -0.0F, 0.75F, -10.58F, 0.0F, -0.0F, 1.5F, -7.5F, 0.0F, -0.0F, 2.0F, -7.5F, 0.0F, -0.0F, 2.5F, -7.5F, 0.0F, -0.0F, 3.0F, 15.0F, 0.0F, -0.0F, 3.41667F, 15.0F, 0.0F, -0.0F}),
         new Track(1, false, new float[]{0.0F, 0.0F, 2.0F, 0.0F, 0.25F, 0.0F, 5.0F, 0.0F, 0.5F, 0.0F, 5.0F, 0.0F, 0.75F, 0.0F, 1.0F, 0.0F, 3.0F, 0.0F, 7.0F, 0.0F, 3.41667F, 0.0F, 2.0F, 0.0F}),
         new Track(2, true, new float[]{0.0F, -15.0F, 0.0F, -0.0F, 0.5F, -15.0F, 0.0F, -0.0F, 0.75F, 13.9083F, 5.0896F, -4.8919F, 1.25F, 13.9083F, 5.0896F, -4.8919F, 1.5F, -71.9827F, -74.2067F, 71.3236F, 2.0F, -71.9827F, -74.2067F, 71.3236F, 2.33333F, -71.9827F, -74.2067F, 71.3236F, 3.0F, -15.0F, 0.0F, -0.0F, 3.41667F, -15.0F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, -47.7815F, -56.0841F, -26.1373F, 0.5F, -47.7815F, -56.0841F, -26.1373F, 0.75F, -173.3288F, -18.1894F, 55.8876F, 1.25F, -173.3288F, -18.1894F, 55.8876F, 1.5F, -3.6324F, -67.9328F, -75.2405F, 1.625F, -3.6324F, -67.9328F, -75.2405F, 2.0F, 42.781F, 15.909F, 35.3257F, 2.5F, 42.781F, 15.909F, 35.3257F, 3.0F, -47.7815F, -56.0841F, -26.1373F, 3.41667F, -47.7815F, -56.0841F, -26.1373F}),
         new Track(4, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.75F, 0.0F, -14.0F, 0.0F, 1.25F, 0.0F, -14.0F, 0.0F, 1.5F, 0.0F, -4.0F, 0.0F, 1.625F, -9.0F, -4.0F, 0.0F, 2.0F, 0.0F, -4.0F, 0.0F, 2.5F, 0.0F, -4.0F, 0.0F, 3.0F, 0.0F, -0.0F, 0.0F, 3.41667F, 0.0F, -0.0F, 0.0F}),
         new Track(5, true, new float[]{0.0F, -74.8103F, 52.5602F, -73.2283F, 0.5F, -74.8103F, 52.5602F, -73.2283F, 0.75F, -73.2442F, -67.0383F, -103.5518F, 1.25F, -73.2442F, -67.0383F, -103.5518F, 1.5F, -51.4903F, -76.3833F, -31.7397F, 2.0F, -51.4903F, -76.3833F, -31.7397F, 2.5F, -51.4903F, -76.3833F, -31.7397F, 3.0F, -74.8103F, 52.5602F, -73.2283F, 3.41667F, -74.8103F, 52.5602F, -73.2283F}),
         new Track(5, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.75F, 0.0F, -14.0F, 0.0F, 1.25F, 0.0F, -14.0F, 0.0F, 1.5F, 0.0F, -2.0F, 0.0F, 2.0F, 0.0F, -2.0F, 0.0F, 2.5F, 0.0F, -2.0F, 0.0F, 3.0F, 0.0F, -0.0F, 0.0F, 3.41667F, 0.0F, -0.0F, 0.0F}),
         new Track(6, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.5F, -0.0F, 0.0F, -0.0F, 1.25F, -0.0F, 0.0F, -0.0F, 1.5F, -0.0F, 0.0F, -25.0F, 2.0F, -0.0F, 0.0F, -25.0F, 2.5F, -0.0F, 0.0F, -25.0F, 3.0F, -0.0F, 0.0F, -0.0F, 3.41667F, -0.0F, 0.0F, -0.0F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.125F, 0.0F, -4.0F, -2.81F, 0.25F, 0.0F, -0.0F, -5.0F, 0.375F, 0.0F, -0.0F, -5.0F, 0.5F, 0.0F, -0.0F, -5.0F, 1.25F, 0.0F, -6.0F, -5.0F, 1.5F, 0.0F, -0.0F, 0.0F, 2.0F, 0.0F, -0.0F, 0.0F, 2.5F, 0.0F, -0.0F, 0.0F, 3.0F, 0.0F, -0.0F, 0.0F, 3.41667F, 0.0F, -0.0F, 0.0F}),
         new Track(7, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.375F, -0.0F, 0.0F, -0.0F, 0.5F, -0.0F, 0.0F, -0.0F, 1.25F, -0.0F, 0.0F, -0.0F, 1.5F, -0.0F, 0.0F, -50.0F, 2.0F, -0.0F, 0.0F, -50.0F, 2.5F, -0.0F, 0.0F, -50.0F, 3.0F, -0.0F, 0.0F, -0.0F, 3.41667F, -0.0F, 0.0F, -0.0F}),
         new Track(7, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.375F, 0.0F, -0.0F, 0.0F, 0.5F, 0.0F, -0.0F, 0.0F, 1.25F, 0.0F, -6.0F, 0.0F, 2.0F, 0.0F, -6.0F, 0.0F, 2.5F, 0.0F, -6.0F, 0.0F, 3.0F, 0.0F, -0.0F, 0.0F, 3.41667F, 0.0F, -0.0F, 0.0F})},
        {new Track(1, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.20833F, 20.5138F, 24.8985F, 2.3273F, 0.5F, -17.6998F, 41.7293F, -3.4575F, 0.625F, 30.1509F, 1.2775F, -6.1088F, 0.75F, 20.1005F, -22.3435F, -14.545F, 1.0F, -0.0F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, 13.1828F, 6.2049F, 6.5402F, 0.20833F, 13.1828F, 6.2049F, 6.5402F, 0.5F, 30.301F, 6.2049F, 6.5402F, 0.75F, 9.3203F, -17.9912F, 38.8166F, 1.0F, 13.1828F, 6.2049F, 6.5402F}),
         new Track(5, true, new float[]{0.0F, 13.1828F, -6.2049F, -6.5402F, 0.20833F, 23.1828F, -6.2049F, -6.5402F, 0.5F, -95.9512F, 48.212F, -59.6691F, 0.625F, -46.4964F, 46.2773F, -65.7085F, 1.0F, -20.1171F, 28.6076F, -60.399F, 1.5F, 13.1828F, -6.2049F, -6.5402F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.125F, 0.0F, -3.0F, -3.0F, 0.25F, 0.0F, -0.0F, -6.0F, 1.0F, 0.0F, -0.0F, -6.0F, 1.25F, 0.0F, -3.0F, -3.0F, 1.5F, 0.0F, -0.0F, 0.0F})},
        {new Track(1, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.25F, -27.5F, 0.0F, -0.0F, 0.5F, -27.5F, 0.0F, -0.0F, 0.66667F, 97.5F, 0.0F, -0.0F, 1.0F, 97.5F, 0.0F, -0.0F, 1.75F, -0.0F, 0.0F, -0.0F}),
         new Track(2, true, new float[]{0.0F, -0.0F, 0.0F, -0.0F, 0.75F, -45.0F, 0.0F, -0.0F, 1.0F, -45.0F, 0.0F, -0.0F, 1.75F, -0.0F, 0.0F, -0.0F}),
         new Track(4, true, new float[]{0.0F, 13.1828F, 6.2049F, 6.5402F, 0.25F, -179.8596F, 6.4629F, 24.1465F, 0.5F, -179.8596F, 6.4629F, 24.1465F, 0.66667F, -140.4278F, -8.243F, 18.3711F, 1.0F, -140.4278F, -8.243F, 18.3711F, 1.75F, 13.1828F, 6.2049F, 6.5402F}),
         new Track(5, true, new float[]{0.0F, 13.1828F, -6.2049F, -6.5402F, 0.25F, -175.0973F, -4.9614F, -24.0801F, 0.5F, -175.0973F, -4.9614F, -24.0801F, 0.66667F, -143.0495F, 8.0055F, -20.5059F, 1.0F, -143.0495F, 8.0055F, -20.5059F, 1.75F, -0.0F, 0.0F, -0.0F}),
         new Track(6, false, new float[]{0.0F, 0.0F, -0.0F, 0.0F, 0.25F, 0.0F, -8.0F, 0.0F, 0.5F, 0.0F, -8.0F, 0.0F, 0.66667F, 0.0F, -0.0F, -10.0F, 1.0F, 0.0F, -0.0F, -10.0F, 1.75F, 0.0F, -0.0F, 0.0F})},
        {new Track(1, true, new float[]{0.0F, 15.0F, 0.0F, -0.0F}),
         new Track(1, false, new float[]{0.0F, 0.0F, 0.6F, 0.0F, 1.5F, 0.0F, 1.4F, 0.0F, 3.0F, 0.0F, 0.6F, 0.0F}),
         new Track(4, true, new float[]{0.0F, -47.7815F, -56.0841F, -26.1373F, 1.5F, -50.2815F, -56.0841F, -26.1373F, 3.0F, -47.7815F, -56.0841F, -26.1373F}),
         new Track(5, true, new float[]{0.0F, -74.8102F, 52.5604F, -73.2279F, 1.5F, -72.3102F, 52.5604F, -73.2279F, 3.0F, -74.8102F, 52.5604F, -73.2279F})},
    };

    private static final boolean[] CLIP_MIRROR = {false, false, true, false, true, true, false, true, false, true};

    private static final Track[][] CLIPS_MIRRORED = mirrorClips(CLIPS);

    private static Track[][] mirrorClips(Track[][] src) {
        Track[][] out = new Track[src.length][];
        for (int c = 0; c < src.length; c++) {
            if (!CLIP_MIRROR[c]) {
                out[c] = src[c];
                continue;
            }
            Track[] dst = new Track[src[c].length];
            for (int i = 0; i < src[c].length; i++) {
                Track tr = src[c][i];
                int bone = tr.bone();
                if (tr.rotation()) {
                    int swapped = bone == RARM ? LARM : (bone == LARM ? RARM : bone);
                    dst[i] = new Track(swapped, true, flipYZ(tr.keys()));
                } else {
                    int swapped = bone == RLEG ? LLEG : (bone == LLEG ? RLEG : bone);
                    dst[i] = new Track(swapped, false, flipX(tr.keys()));
                }
            }
            out[c] = dst;
        }
        return out;
    }

    private static float[] flipYZ(float[] keys) {
        float[] out = keys.clone();
        for (int i = 0; i + 3 < out.length; i += 4) {
            out[i + 2] = -out[i + 2];
            out[i + 3] = -out[i + 3];
        }
        return out;
    }

    private static float[] flipX(float[] keys) {
        float[] out = keys.clone();
        for (int i = 0; i + 3 < out.length; i += 4) {
            out[i + 1] = -out[i + 1];
        }
        return out;
    }

    private final ModelPart[] parts = new ModelPart[8];

    public PrismarineSoldierModel(ModelPart root) {
        super();
        parts[ROOT] = root;
        parts[UPPER] = root.getChild("upper_body");
        parts[HEAD] = parts[UPPER].getChild("head");
        parts[BODY] = parts[UPPER].getChild("body");
        parts[RARM] = parts[UPPER].getChild("right_arm");
        parts[LARM] = parts[UPPER].getChild("left_arm");
        parts[RLEG] = root.getChild("right_leg");
        parts[LLEG] = root.getChild("left_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition upper = root.addOrReplaceChild("upper_body", CubeListBuilder.create(),
                PartPose.offset(BONE_BASE[UPPER][0], BONE_BASE[UPPER][1], BONE_BASE[UPPER][2]));
        addBone(upper, "head", HEAD);
        addBone(upper, "body", BODY);
        addBone(upper, "right_arm", RARM);
        addBone(upper, "left_arm", LARM);
        addBone(root, "right_leg", RLEG);
        addBone(root, "left_leg", LLEG);
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static void addBone(PartDefinition parent, String name, int idx) {
        parent.addOrReplaceChild(name, CubeListBuilder.create(),
                PartPose.offset(BONE_BASE[idx][0], BONE_BASE[idx][1], BONE_BASE[idx][2]));
    }

    public ModelPart getArmPart(boolean right) {
        return right ? parts[RARM] : parts[LARM];
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        parts[ROOT].translateAndRotate(poseStack);
        parts[UPPER].translateAndRotate(poseStack);
        getArmPart(arm == HumanoidArm.RIGHT).translateAndRotate(poseStack);
        poseStack.translate(0.0F, PALM_DROP, 0.0F);
    }

    @Override
    public void renderToBuffer(PoseStack pose, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a) {
        if (this.young) {
            pose.pushPose();
            pose.scale(0.5F, 0.5F, 0.5F);
            pose.translate(0.0F, 24.0F / 16.0F, 0.0F);
        }
        renderPart(parts[ROOT], pose, consumer, light, overlay, r, g, b, a);
        if (this.young) pose.popPose();
    }

    private void renderPart(ModelPart part, PoseStack pose, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a) {
        if (!part.visible) return;
        pose.pushPose();
        part.translateAndRotate(pose);
        int idx = indexOf(part);
        if (idx >= 0) drawQuads(idx, pose, consumer, light, overlay, r, g, b, a);
        for (int child : childrenOf(idx)) {
            renderPart(parts[child], pose, consumer, light, overlay, r, g, b, a);
        }
        pose.popPose();
    }

    private int indexOf(ModelPart part) {
        for (int i = 0; i < parts.length; i++) if (parts[i] == part) return i;
        return -1;
    }

    private static int[] childrenOf(int idx) {
        return switch (idx) {
            case ROOT -> new int[]{UPPER, RLEG, LLEG};
            case UPPER -> new int[]{HEAD, BODY, RARM, LARM};
            default -> new int[0];
        };
    }

    private void drawQuads(int idx, PoseStack pose, VertexConsumer consumer, int light, int overlay, float r, float g, float b, float a) {
        if (idx >= QUADS.length) return;
        Matrix4f m = pose.last().pose();
        Matrix3f n = pose.last().normal();
        for (float[] q : QUADS[idx]) {
            float shade = q[23];
            float sr = r * shade, sg = g * shade, sb = b * shade;
            float nx = q[12], ny = q[13], nz = q[14];
            emit(consumer, m, n, q, 0, 0, nx, ny, nz, light, overlay, sr, sg, sb, a);
            emit(consumer, m, n, q, 1, 1, nx, ny, nz, light, overlay, sr, sg, sb, a);
            emit(consumer, m, n, q, 2, 2, nx, ny, nz, light, overlay, sr, sg, sb, a);
            emit(consumer, m, n, q, 3, 3, nx, ny, nz, light, overlay, sr, sg, sb, a);
        }
    }

    private static final float INV16 = 1.0F / 16.0F;

    private static void emit(VertexConsumer consumer, Matrix4f m, Matrix3f n, float[] q, int vi, int ui, float nx, float ny, float nz, int light, int overlay, float r, float g, float b, float a) {
        consumer.vertex(m, q[vi * 3] * INV16, q[vi * 3 + 1] * INV16, q[vi * 3 + 2] * INV16)
                .color(r, g, b, a)
                .uv(q[15 + ui * 2], q[15 + ui * 2 + 1])
                .overlayCoords(overlay)
                .uv2(light)
                .normal(n, nx, ny, nz)
                .endVertex();
    }

    @Override
    public void setupAnim(PrismarineSoldier entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        resetToRestPose();
        if (entity.isInWater() && !entity.isAttackAnimating()) {
            applyClip(1, (limbSwing % 13.0F) / 13.0F * 2.0F); // 游泳用近战走路
        } else if (entity.holdsBow() && entity.isUsingItem() && !entity.isAttackAnimating()) {
            float charge = Mth.clamp(entity.getTicksUsingItem() / (float) PrismarineSoldier.BOW_DRAW_TICKS, 0.0F, 1.0F);
            applyClip(5, charge * BOW_DRAW_PHASE);
        } else {
            int clip = pickClip(entity, limbSwingAmount);
            if (clip >= 0) applyClip(clip, phaseFor(clip, entity, limbSwing, limbSwingAmount, ageInTicks));
            if (entity.isAttackAnimating() && limbSwingAmount > 0.01F) {
                applyWalkLegs(entity.holdsBow() ? 4 : 1, limbSwing);
            }
        }
        this.parts[HEAD].yRot = (float) Math.toRadians(netHeadYaw * 0.5D);
        this.parts[HEAD].xRot = (float) Math.toRadians(headPitch * 0.5D);
    }

    private static int pickClip(PrismarineSoldier e, float moving) {
        if (e.isAttackAnimating()) {
            return switch (e.getAttackAnimation()) {
                case PrismarineSoldier.ATTACK_BOW -> 5;
                case PrismarineSoldier.ATTACK_THUNDER -> 6;
                case PrismarineSoldier.ATTACK_SINGLE -> 7;
                case PrismarineSoldier.ATTACK_HEAVY -> 8;
                case PrismarineSoldier.ATTACK_BLOCK -> 3;
                default -> 2;
            };
        }
        boolean bow = e.holdsBow();
        if (moving > 0.01F) return bow ? 4 : 1;
        return bow ? 9 : 0;
    }

    private static float phaseFor(int clip, PrismarineSoldier e, float swing, float amount, float age) {
        float length = CLIP_LENGTH[clip];
        boolean loop = CLIP_LOOP[clip];
        if (!loop && clip >= 2 && clip <= 8) {
            int tick = e.getAttackAnimationTicks() - e.getAttackAnimationTick();
            float sec = tick / 20.0F;
            if (clip == 5) {
                return Mth.clamp(BOW_RELEASE_PHASE + sec, 0.0F, length);
            }
            if (clip == 3) {
                float total = Math.max(1, e.getAttackAnimationTicks()) / 20.0F;
                float holdEnd = total - (length - BLOCK_HOLD_PHASE);
                if (sec <= BLOCK_HOLD_PHASE) {
                    return sec;
                }
                if (sec < holdEnd) {
                    return BLOCK_HOLD_PHASE;
                }
                return Mth.clamp(BLOCK_HOLD_PHASE + (sec - holdEnd), BLOCK_HOLD_PHASE, length);
            }
            return Mth.clamp(sec, 0.0F, length);
        }
        if (clip == 1 || clip == 4) {
            float stride = 13.0F;
            float phase = swing % stride;
            if (phase < 0.0F) phase += stride;
            return phase * length / stride;
        }
        return (age % (length * 20.0F)) / (length * 20.0F) * length;
    }

    private void applyClip(int clip, float t) {
        for (Track tr : CLIPS_MIRRORED[clip]) applyTrack(tr, t, CLIP_LOOP[clip]);
    }

    private void applyWalkLegs(int walkClip, float swing) {
        float length = CLIP_LENGTH[walkClip];
        float stride = 13.0F;
        float phase = swing % stride;
        if (phase < 0.0F) {
            phase += stride;
        }
        float t = phase * length / stride;
        for (Track tr : CLIPS_MIRRORED[walkClip]) {
            if (!tr.rotation() && (tr.bone() == RLEG || tr.bone() == LLEG)) {
                applyTrack(tr, t, CLIP_LOOP[walkClip]);
            }
        }
    }

    private void applyTrack(Track tr, float t, boolean loop) {
        int idx = tr.bone();
        float[] v = sampleTrack(tr, t, loop);
        ModelPart part = parts[idx];
        if (tr.rotation()) {
            part.xRot = (float) Math.toRadians(v[0]);
            part.yRot = (float) Math.toRadians(v[1]);
            part.zRot = (float) Math.toRadians(v[2]);
        } else {
            part.x = BONE_BASE[idx][0] + v[0];
            part.y = BONE_BASE[idx][1] + v[1];
            part.z = BONE_BASE[idx][2] + v[2];
        }
    }

    private void resetToRestPose() {
        for (int i = 0; i < parts.length; i++) {
            parts[i].x = BONE_BASE[i][0];
            parts[i].y = BONE_BASE[i][1];
            parts[i].z = BONE_BASE[i][2];
            parts[i].xRot = parts[i].yRot = parts[i].zRot = 0.0F;
        }
    }

    private static float[] sampleTrack(Track tr, float t, boolean loop) {
        float[] k = tr.keys();
        int n = k.length / 4;
        if (n == 0 || t <= k[0]) return vec(k, 0);
        if (t >= k[(n - 1) * 4]) return vec(k, n - 1);
        int i = 0;
        while (i < n - 2 && k[(i + 1) * 4] <= t) i++;
        float t0 = k[i * 4], t1 = k[(i + 1) * 4];
        float u = Mth.clamp((t - t0) / (t1 - t0), 0.0F, 1.0F);
        float u2 = u * u, u3 = u2 * u;
        float[] p0 = vec(k, i > 0 ? i - 1 : i);
        float[] p1 = vec(k, i);
        float[] p2 = vec(k, i + 1);
        float[] p3 = vec(k, i + 2 < n ? i + 2 : (loop ? 1 : i + 1));
        float[] out = new float[3];
        for (int a = 0; a < 3; a++) {
            float v0 = p0[a], v1 = p1[a], v2 = p2[a], v3 = p3[a];
            out[a] = 0.5F * ((2.0F * v1) + (-v0 + v2) * u
                    + (2.0F * v0 - 5.0F * v1 + 4.0F * v2 - v3) * u2
                    + (-v0 + 3.0F * v1 - 3.0F * v2 + v3) * u3);
        }
        return out;
    }

    private static float[] vec(float[] k, int i) {
        return new float[]{k[i * 4 + 1], k[i * 4 + 2], k[i * 4 + 3]};
    }

    public ModelPart part(int bone) { return parts[bone]; }

    public void applyPoseToArmor(HumanoidModel<?> armor) {
        this.copyBoneRotation(armor.head, HEAD);
        this.copyBoneRotation(armor.body, BODY);
        this.copyBoneRotation(armor.rightArm, RARM);
        this.copyBoneRotation(armor.leftArm, LARM);
        this.copyBoneRotation(armor.rightLeg, RLEG);
        this.copyBoneRotation(armor.leftLeg, LLEG);
    }

    public ModelPart upperBone() {
        return this.parts[UPPER];
    }

    public void pushBoneTransform(int bone, PoseStack pose) {
        this.parts[ROOT].translateAndRotate(pose);
        if (bone != RLEG && bone != LLEG) {
            this.parts[UPPER].translateAndRotate(pose);
        }
        this.parts[bone].translateAndRotate(pose);
    }

    public float rootYRot() {
        return this.parts[ROOT].yRot;
    }

    private void copyBoneRotation(ModelPart target, int bone) {
        ModelPart part = this.parts[bone];
        target.xRot = part.xRot;
        target.yRot = part.yRot;
        target.zRot = part.zRot;
    }
}
