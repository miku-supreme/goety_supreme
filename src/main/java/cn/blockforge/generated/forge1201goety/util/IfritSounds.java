package cn.blockforge.generated.goetysupreme.util;

import cn.blockforge.generated.goetysupreme.GeneratedMod;

final class IfritSounds {

    private IfritSounds() {
    }

    static void playBasicAttackSound(Ifrit mob) {
        mob.playSound(GeneratedMod.IFRIT_BASIC_ATTACK.get(), 1.35F,
                0.95F + mob.getRandom().nextFloat() * 0.1F);
    }

    static void playSwordSaberSound(Ifrit mob) {
        mob.playSound(GeneratedMod.IFRIT_SWORD_SABER.get(), 1.4F,
                0.95F + mob.getRandom().nextFloat() * 0.1F);
    }

    static void playImpactSound(Ifrit mob) {
        if (mob.tickCount == mob.impactSoundTick) {
            return;
        }
        mob.impactSoundTick = mob.tickCount;
        mob.playSound(GeneratedMod.IFRIT_POWERFUL_BLOW.get(), 1.4F,
                0.95F + mob.getRandom().nextFloat() * 0.1F);
    }

    static void playDashSound(Ifrit mob) {
        mob.playSound(GeneratedMod.IFRIT_DASH.get(), 1.3F,
                0.95F + mob.getRandom().nextFloat() * 0.1F);
    }
}
