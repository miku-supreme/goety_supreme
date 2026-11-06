package cn.blockforge.generated.goetysupreme.renderer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import cn.blockforge.generated.goetysupreme.util.Ease;
import cn.blockforge.generated.goetysupreme.compat.JerotesItems;
import cn.blockforge.generated.goetysupreme.renderer.JerotesSpearAnimation;

public final class JerotesSpearAnimation {

    public static void specialAnim(LivingEntity living, HumanoidModel<?> model, ModelPart head,
            ModelPart rightArm, ModelPart leftArm, ModelPart body) {
        ItemStack main = living.getMainHandItem();

        if (shouldPoseRightArm(living)) {
            thirdPersonHandUse(rightArm, head, true, living.getUseItem(), living, model);
        }
        if (shouldPoseLeftArm(living)) {
            thirdPersonHandUse(leftArm, head, false, living.getUseItem(), living, model);
        }

        if (JerotesItems.isSpear(main) && !otherAnimSpear(main) && model.attackTime > 0.0F) {
            twistBodyForSpear(living, model, rightArm, leftArm, body);
            thirdPersonAttackHand(model, living);
        }

        if (JerotesItems.isPike(main)) {
            if (living.getMainArm() == HumanoidArm.RIGHT) {
                pikeAnimate(rightArm, leftArm, false);
                thirdPersonHandUse(rightArm, head, true, living.getUseItem(), living, model);
                if (living.isUsingItem() && living.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                    rightArm.yRot += pikeUseDelta(living.getTicksUsingItem());
                }
            } else {
                pikeAnimate(rightArm, leftArm, true);
                thirdPersonHandUse(leftArm, head, false, living.getUseItem(), living, model);
                if (living.isUsingItem() && living.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                    leftArm.yRot -= pikeUseDelta(living.getTicksUsingItem());
                }
            }
            if (model.attackTime > 0.0F) {
                twistBodyForSpear(living, model, rightArm, leftArm, body);
                pikeAnimateAttack(model, living);
            }
        }
    }

    public static float progress(float value, float min, float max) {
        if (max == min) {
            return value >= max ? 1.0F : 0.0F;
        }
        return Mth.clamp(Mth.inverseLerp(value, min, max), 0.0F, 1.0F);
    }

    public static void thirdPersonHandUse(ModelPart arm, ModelPart head, boolean rightHand, ItemStack itemStack,
            LivingEntity living, HumanoidModel<?> model) {
        int side = rightHand ? 1 : -1;

        float headYawDeg = (float) (57.295776F * head.yRot) % 360.0F;
        if (headYawDeg > 180.0F) {
            headYawDeg -= 360.0F;
        } else if (headYawDeg < -180.0F) {
            headYawDeg += 360.0F;
        }
        float headYawRad = headYawDeg * ((float) Math.PI / 180.0F);

        arm.yRot = -0.1F * (float) side + headYawRad;
        arm.xRot = -1.5707964F + head.xRot + 0.8F;
        if (living.isFallFlying() || model.swimAmount > 0.0F) {
            arm.xRot -= 0.9599311F;
        }
        arm.yRot = (float) Math.PI / 180.0F * Mth.clamp((float) (57.295776F * arm.yRot), -60.0F, 60.0F);
        arm.xRot = (float) Math.PI / 180.0F * Mth.clamp((float) (57.295776F * arm.xRot), -120.0F, 30.0F);

        if (living.getTicksUsingItem() <= 0
                || living.isUsingItem() && living.getUsedItemHand()
                        != (rightHand ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND)) {
            return;
        }
        if (!JerotesItems.isSpear(itemStack)) {
            return;
        }
        SpearUseParams params = SpearUseParams.from(itemStack, living.getTicksUsingItem());
        arm.yRot += (float) (-side) * params.swayScaleFast() * ((float) Math.PI / 180.0F) * params.swayIntensity();
        arm.zRot += (float) (-side) * params.swayScaleSlow() * ((float) Math.PI / 180.0F) * params.swayIntensity() * 0.5F;
        arm.xRot += (float) Math.PI / 180.0F * (-40.0F * params.raiseProgressStart()
                + 30.0F * params.raiseProgressMiddle() - 20.0F * params.raiseProgressEnd()
                + 20.0F * params.lowerProgress() + 10.0F * params.raiseBackProgress()
                + 0.6F * params.swayScaleSlow() * params.swayIntensity());
    }

    public static void thirdPersonAttackHand(HumanoidModel<?> model, LivingEntity living) {
        float attack = living.getAttackAnim(Minecraft.getInstance().getPartialTick());
        HumanoidArm mainArm = living.getMainArm();
        HumanoidArm swinging = living.swingingArm == InteractionHand.MAIN_HAND ? mainArm : mainArm.getOpposite();

        model.rightArm.yRot -= model.body.yRot;
        model.leftArm.yRot -= model.body.yRot;
        model.leftArm.xRot -= model.body.yRot;

        float f2 = Ease.inOutSine(progress(attack, 0.0F, 0.05F));
        float f3 = Ease.inQuad(progress(attack, 0.05F, 0.2F));
        float f4 = Ease.inOutExpo(progress(attack, 0.4F, 1.0F));
        getArm(model, swinging).xRot += (90.0F * f2 - 120.0F * f3 + 30.0F * f4) * ((float) Math.PI / 180.0F);
    }

    public static void pikeAnimate(ModelPart firstArm, ModelPart secondArm, boolean flag) {
        ModelPart primary = flag ? firstArm : secondArm;
        ModelPart secondary = flag ? secondArm : firstArm;
        primary.yRot = flag ? -0.8F : 0.8F;
        primary.xRot = -0.97079635F;
        secondary.xRot = primary.xRot;
        float lerp = 0.5F;
        secondary.yRot = Mth.lerp(lerp, 0.4F, 0.85F) * (float) (flag ? 1 : -1);
        secondary.xRot = Mth.lerp(lerp, secondary.xRot, -1.5707964F);
    }

    public static void pikeAnimateAttack(HumanoidModel<?> model, LivingEntity living) {
        float attack = living.getAttackAnim(Minecraft.getInstance().getPartialTick());
        float f2 = Ease.inOutSine(progress(attack, 0.0F, 0.05F));
        float f3 = Ease.inQuad(progress(attack, 0.05F, 0.2F));
        float f4 = Ease.inOutExpo(progress(attack, 0.4F, 1.0F));

        HumanoidArm mainArm = living.getMainArm();
        HumanoidArm swinging = living.swingingArm == InteractionHand.MAIN_HAND ? mainArm : mainArm.getOpposite();
        model.rightArm.yRot -= model.body.yRot;
        model.leftArm.yRot -= model.body.yRot;
        getArm(model, swinging).xRot += (90.0F * f2 - 120.0F * f3 + 30.0F * f4) * ((float) Math.PI / 180.0F);

        HumanoidArm oppositeBase = mainArm.getOpposite();
        HumanoidArm opposite = living.swingingArm == InteractionHand.MAIN_HAND
                ? oppositeBase : oppositeBase.getOpposite();
        model.rightArm.yRot -= model.body.yRot;
        model.leftArm.yRot -= model.body.yRot;
        getArm(model, opposite).xRot += (90.0F * f2 - 120.0F * f3 + 30.0F * f4)
                * ((float) Math.PI / 180.0F) * 0.2F;
    }

    private static void twistBodyForSpear(LivingEntity living, HumanoidModel<?> model,
            ModelPart rightArm, ModelPart leftArm, ModelPart body) {
        HumanoidArm mainArm = living.getMainArm();
        float attacked = model.attackTime;
        body.yRot = Mth.sin(Mth.sqrt(attacked) * ((float) Math.PI * 2.0F)) * 0.2F;
        if (mainArm == HumanoidArm.LEFT) {
            body.yRot *= -1.0F;
        }
        rightArm.z = Mth.sin(body.yRot) * 5.0F;
        rightArm.x = -Mth.cos(body.yRot) * 5.0F;
        leftArm.z = -Mth.sin(body.yRot) * 5.0F;
        leftArm.x = Mth.cos(body.yRot) * 5.0F;
        rightArm.yRot += body.yRot;
        leftArm.yRot += body.yRot;
        leftArm.zRot += body.yRot;
    }

    private static float pikeUseDelta(int ticksUsingItem) {
        float f = 1.0F - (float) Math.min(25, ticksUsingItem) / 25.0F;
        if (f > 0.0F && f <= 0.4F) {
            return f / 0.4F * 0.35F;
        }
        if (f > 0.4F && f <= 0.5F) {
            return 0.35F + (f - 0.4F) / 0.1F * -0.55F;
        }
        if (f > 0.5F) {
            return (1.0F - (f - 0.5F) / 0.5F) * -0.2F;
        }
        return 0.0F;
    }

    private static boolean shouldPoseRightArm(LivingEntity entity) {
        if (entity.getMainArm() == HumanoidArm.RIGHT) {
            if (JerotesItems.isSpear(entity.getUseItem()) && entity.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                return true;
            }
            if (JerotesItems.isSpear(entity.getMainHandItem()) && JerotesItems.isSpear(entity.getOffhandItem())) {
                return false;
            }
            return !JerotesItems.isSpear(entity.getOffhandItem()) && JerotesItems.isSpear(entity.getMainHandItem());
        }
        if (JerotesItems.isSpear(entity.getUseItem()) && entity.getUsedItemHand() != InteractionHand.MAIN_HAND) {
            return true;
        }
        if (JerotesItems.isSpear(entity.getMainHandItem()) && JerotesItems.isSpear(entity.getOffhandItem())) {
            return true;
        }
        return !JerotesItems.isSpear(entity.getMainHandItem()) && JerotesItems.isSpear(entity.getOffhandItem());
    }

    private static boolean shouldPoseLeftArm(LivingEntity entity) {
        if (entity.getMainArm() == HumanoidArm.LEFT) {
            if (JerotesItems.isSpear(entity.getUseItem()) && entity.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                return true;
            }
            if (JerotesItems.isSpear(entity.getMainHandItem()) && JerotesItems.isSpear(entity.getOffhandItem())) {
                return false;
            }
            return !JerotesItems.isSpear(entity.getOffhandItem()) && JerotesItems.isSpear(entity.getMainHandItem());
        }
        if (JerotesItems.isSpear(entity.getUseItem()) && entity.getUsedItemHand() != InteractionHand.MAIN_HAND) {
            return true;
        }
        if (JerotesItems.isSpear(entity.getMainHandItem()) && JerotesItems.isSpear(entity.getOffhandItem())) {
            return true;
        }
        return !JerotesItems.isSpear(entity.getMainHandItem()) && JerotesItems.isSpear(entity.getOffhandItem());
    }

    private static ModelPart getArm(HumanoidModel<?> model, HumanoidArm arm) {
        return arm == HumanoidArm.LEFT ? model.leftArm : model.rightArm;
    }

    private static boolean otherAnimSpear(ItemStack stack) {
        try {
            Class<?> type = stack.getItem().getClass();
            Method method = OTHER_ANIM_CACHE.computeIfAbsent(type, t -> {
                try {
                    Method m = t.getMethod("otherAnimSpear");
                    m.setAccessible(true);
                    return m;
                } catch (ReflectiveOperationException e) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", e.toString());
                    return MISSING;
                }
            });
            if (method == MISSING) {
                return false;
            }
            Object result = method.invoke(stack.getItem());
            return result instanceof Boolean value && value;
        } catch (Throwable ignored) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", ignored.toString());
            return false;
        }
    }

    private static final Method MISSING;

    static {
        try {
            MISSING = JerotesSpearAnimation.class.getDeclaredMethod("missing");
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static void missing() {
    }

    private static final Map<Class<?>, Method> OTHER_ANIM_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, Field> FIELD_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Method> CONDITION_MAX_CACHE = new ConcurrentHashMap<>();

    private record SpearUseParams(float raiseProgress, float raiseProgressStart, float raiseProgressMiddle,
            float raiseProgressEnd, float swayProgress, float lowerProgress, float raiseBackProgress,
            float swayIntensity, float swayScaleSlow, float swayScaleFast) {

        public static final SpearUseParams ZERO = new SpearUseParams(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

        static SpearUseParams from(ItemStack stack, float ticks) {
            try {
                Object item = stack.getItem();
                int delay = readInt(item, "delayTicks");
                if (delay <= 0) {
                    return ZERO;
                }
                int dismount = conditionMax(item, "dismountConditions");
                int knockback = conditionMax(item, "knockbackConditions");
                int damage = conditionMax(item, "damageConditions");

                int dismountEnd = dismount + delay;
                int dismountStart = dismountEnd - 20;
                int knockbackEnd = knockback + delay;
                int knockbackStart = knockbackEnd - 40;
                int damageEnd = damage + delay;

                float raise = progress(ticks, 0.0F, delay);
                float raiseStart = progress(raise, 0.0F, 0.5F);
                float raiseMiddle = progress(raise, 0.5F, 0.8F);
                float raiseEnd = progress(raise, 0.8F, 1.0F);
                float sway = progress(ticks, dismountStart, knockbackStart);
                float lower = Ease.outCubic(Ease.inOutElastic(
                        progress(ticks - 20.0F, knockbackStart, knockbackEnd)));
                float raiseBack = progress(ticks, damageEnd - 5.0F, damageEnd);
                float intensity = 2.0F * Ease.outCirc(sway) - 2.0F * Ease.inCirc(raiseBack);
                float slow = Mth.sin(ticks * 19.0F * ((float) Math.PI / 180.0F)) * intensity;
                float fast = Mth.sin(ticks * 30.0F * ((float) Math.PI / 180.0F)) * intensity;
                return new SpearUseParams(raise, raiseStart, raiseMiddle, raiseEnd, sway, lower,
                        raiseBack, intensity, slow, fast);
            } catch (Throwable ignored) {
                return ZERO;
            }
        }

        private static int readInt(Object target, String name) throws ReflectiveOperationException {
            return ((Number) field(target.getClass(), name).get(target)).intValue();
        }

        private static int conditionMax(Object target, String name) throws ReflectiveOperationException {
            Object value = field(target.getClass(), name).get(target);
            if (value instanceof Optional<?> optional && optional.isPresent()) {
                Object condition = optional.get();
                Method method = CONDITION_MAX_CACHE.computeIfAbsent(condition.getClass(), t -> {
                    try {
                        Method m = t.getMethod("maxDurationTicks");
                        m.setAccessible(true);
                        return m;
                    } catch (ReflectiveOperationException e) {
                com.mojang.logging.LogUtils.getLogger().warn("[goety_supreme] 反射读取失败（可能目标结构变了）: {}", e.toString());
                        return MISSING;
                    }
                });
                if (method != MISSING) {
                    return ((Number) method.invoke(condition)).intValue();
                }
            }
            return 0;
        }

        private static Field field(Class<?> type, String name) throws NoSuchFieldException {
            String key = type.getName() + "#" + name;
            Field cached = FIELD_CACHE.get(key);
            if (cached != null) {
                return cached;
            }
            Field found = type.getField(name);
            found.setAccessible(true);
            FIELD_CACHE.put(key, found);
            return found;
        }
    }
}
