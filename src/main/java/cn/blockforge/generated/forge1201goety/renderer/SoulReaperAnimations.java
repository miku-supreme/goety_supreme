package cn.blockforge.generated.goetysupreme.renderer;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class SoulReaperAnimations {
	public static final AnimationDefinition REST_NO_COFFIN = AnimationDefinition.Builder.withLength(2F).looping()
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(20F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-15.326F, 9.6621F, -4.7898F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(15F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-17.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition REST_COFFIN = AnimationDefinition.Builder.withLength(2F).looping()
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-15.326F, 9.6621F, -4.7898F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(15F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-17.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition WALK_NO_COFFIN = AnimationDefinition.Builder.withLength(2F).looping()
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-51.1547F, -64.7126F, 73.76F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(17.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(7.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-7.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition WALK_COFFIN = AnimationDefinition.Builder.withLength(2F).looping()
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-51.1547F, -64.7126F, 73.76F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(7.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-7.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM))).build();

	public static final AnimationDefinition COFFIN_ENTER = AnimationDefinition.Builder.withLength(1.7917F)
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.6476F, 18.4303F, 0.5838F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-8.9426F, -14.9685F, -7.1139F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-8.9426F, -14.9685F, -7.1139F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-10F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(119.102F, 31.5316F, -52.0491F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(119.102F, 31.5316F, -52.0491F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-105F, 0F, 85F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-105F, 0F, 85F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-52.4007F, 26.7134F, 35.0241F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-52.4007F, 26.7134F, 35.0241F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(7.5F, 90F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(7.5F, 90F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(89.0454F, -17.9392F, -31.1693F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(89.0454F, -17.9392F, -31.1693F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.5F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-12.5F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(10F, 4.21F, 45F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(10F, 4.21F, 45F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(90F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(70F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(77.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(45F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(40F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("left_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(52.7476F, 15.7627F, -12.8426F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(180F, 80F, 115F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(180F, 80F, 115F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(30F, 15F, -70F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(30F, 15F, -70F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("left_pauldron", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.posVec(0F, 0F, -2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-52.9339F, 26.9464F, 18.8959F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-6.9776F, 5.2483F, 11.3608F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-20.0995F, 4.2954F, 3.8956F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-6.9776F, 5.2483F, 11.3608F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-27.5995F, 4.2954F, 3.8956F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-6.9776F, 5.2483F, 11.3608F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.75F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8333F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM))).build();

	public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(1F)
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(136.0719F, -63.747F, -131.4022F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(136.0719F, -63.747F, -131.4022F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-132.2879F, -59.4478F, 184.906F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-132.2879F, -59.4478F, 184.906F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-45.5497F, -84.8016F, 123.7044F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.posVec(1.2826F, -5.0471F, -0.2905F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-0F, -34.53F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-0F, -34.53F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 55.36F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(19.56F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-27.58F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-10F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(98.61F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.degreeVec(-22.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(35F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-22.4738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-22.4738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-26.3474F, 28.9516F, 118.6781F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(8.75F, 0F, -16.71F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.5F, 0F, -33.42F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(-7.5F, 0F, -33.42F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(67.6282F, -37.8094F, 71.6614F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.posVec(4.6386F, -2.9832F, -1.4964F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.375F, KeyframeAnimations.posVec(3.8768F, -1.8289F, -4.3402F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(17.5F, -12.5F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(17.5F, -12.5F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-10F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-34.17F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition REVIVE = AnimationDefinition.Builder.withLength(1.7917F)
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.6476F, 18.4303F, 0.5838F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-8.9426F, -14.9685F, -7.1139F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-8.9426F, -14.9685F, -7.1139F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-10F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-10F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(119.102F, 31.5316F, -52.0491F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(119.102F, 31.5316F, -52.0491F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-105F, 0F, 85F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-105F, 0F, 85F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-52.4007F, 26.7134F, 35.0241F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-52.4007F, 26.7134F, 35.0241F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(7.5F, 90F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(7.5F, 90F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(7.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(7.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(89.0454F, -17.9392F, -31.1693F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(89.0454F, -17.9392F, -31.1693F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-12.5F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-12.5F, 15F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(10F, 4.21F, 45F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(10F, 4.21F, 45F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(90F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(70F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(77.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(45F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(40F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("left_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(52.7476F, 15.7627F, -12.8426F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(180F, 80F, 115F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(180F, 80F, 115F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(30F, 15F, -70F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(30F, 15F, -70F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("left_pauldron", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.posVec(0F, 0F, -2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-52.9339F, 26.9464F, 18.8959F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-6.9776F, 5.2483F, 11.3608F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-20.0995F, 4.2954F, 3.8956F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-6.9776F, 5.2483F, 11.3608F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2083F, KeyframeAnimations.degreeVec(-27.5995F, 4.2954F, 3.8956F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-6.9776F, 5.2483F, 11.3608F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.7917F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition ATTACK_ORDER = AnimationDefinition.Builder.withLength(1.5F)
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0F, 2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4583F, KeyframeAnimations.degreeVec(9.8162F, -23.6602F, 8.7009F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2083F, KeyframeAnimations.degreeVec(9.8162F, -23.6602F, 8.7009F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-10F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-92.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(-93.5336F, 44.9454F, -2.4978F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-92.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(-92.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(-12.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-27.5F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition BURST = AnimationDefinition.Builder.withLength(2.25F)
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 3F, 4F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-20F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(27.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(27.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-43.5255F, -79.9236F, 61.3261F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(-0.3198F, 1.3646F, 10.5494F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-0.3198F, 1.3646F, 10.5494F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-43.5255F, -79.9236F, 61.3261F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.25F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(18.7507F, 1.1105F, -6.719F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(57.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(57.5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-41.3041F, 0.9313F, -48.3676F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(-19.3521F, -4.9922F, 0.9068F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-19.3521F, -4.9922F, 0.9068F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-104.743F, 20.6237F, -10.4399F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(-89.9999F, 5.0004F, -5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-92.5094F, 4.9956F, -5.2187F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.posVec(0.359F, -4.1036F, 5.7473F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.posVec(0.359F, -4.1036F, 5.7473F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-0F, -20F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(-19.9299F, 1.7082F, 4.6999F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.875F, KeyframeAnimations.degreeVec(25.0907F, -2.8208F, 2.5809F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.25F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition DEATH = AnimationDefinition.Builder.withLength(1.5833F)
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("上半身", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, 7F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-35F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(-35F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("头", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.posVec(0F, -31F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2917F, KeyframeAnimations.posVec(0F, -28F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.375F, KeyframeAnimations.posVec(0F, -31F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4583F, KeyframeAnimations.posVec(0F, -30F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5417F, KeyframeAnimations.posVec(0F, -31F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-7.4338F, -2.7855F, 25.9184F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-43.5255F, -79.9236F, 61.3261F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-0.873F, -20.9171F, 13.7447F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("right_pauldron", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(25F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("身", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-57.4738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-90F, 30F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3333F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4167F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6667F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition TAKEOFF_COFFIN = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition LANDING_COFFIN = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.5F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition TAKEOFF_NO_COFFIN = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();

	public static final AnimationDefinition LANDING_NO_COFFIN = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("skeleton", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-66.5958F, -66.3228F, 85.2405F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(26.0916F, -83.3326F, -8.8436F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("镰刀", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("左肩", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-82.4717F, 4.9575F, -4.3458F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("披风", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(6F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("右手2", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-14.9738F, -2.4068F, -1.5102F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("左手", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("除了头", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(30F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-0F, 0F, -0F), AnimationChannel.Interpolations.LINEAR)))
		.addAnimation("肋骨", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(5F, 0F, -0F), AnimationChannel.Interpolations.CATMULLROM)))
		.addAnimation("棺材", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.5F, KeyframeAnimations.scaleVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR))).build();
}
