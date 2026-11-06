package cn.blockforge.generated.goetysupreme.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class NamelessWitherConfig {

    public static ForgeConfigSpec.DoubleValue MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue REGEN_PER_SECOND;
    public static ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.DoubleValue DAMAGE_CAP_FRACTION;
    public static ForgeConfigSpec.DoubleValue ATTACK_DAMAGE;
    public static ForgeConfigSpec.IntValue INVULNERABLE_TICKS;
    public static ForgeConfigSpec.DoubleValue FLYING_SPEED;
    public static ForgeConfigSpec.IntValue STATION_EXTRA_TICKS;
    public static ForgeConfigSpec.DoubleValue FLY_HEIGHT_MIN;
    public static ForgeConfigSpec.DoubleValue FLY_HEIGHT_MAX;
    public static ForgeConfigSpec.DoubleValue UNDEAD_DAMAGE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue NIGHT_DAMAGE_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue SKULL_SCALE;
    public static ForgeConfigSpec.DoubleValue SKULL_EXPLOSION_RADIUS;
    public static ForgeConfigSpec.DoubleValue SKULL_SPEED_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SKULL_FIRE_INTERVAL_MULTIPLIER;
    public static ForgeConfigSpec.IntValue HEAD_MIN_SPACING_TICKS;
    public static ForgeConfigSpec.IntValue SLAM_CHARGE_TICKS;
    public static ForgeConfigSpec.IntValue BEAM_EVERY_SKULLS;
    public static ForgeConfigSpec.IntValue BEAM_DELAY_TICKS;
    public static ForgeConfigSpec.DoubleValue BEAM_DAMAGE;
    public static ForgeConfigSpec.DoubleValue BEAM_DIRECT_DAMAGE;
    public static ForgeConfigSpec.DoubleValue BEAM_RANGE;
    public static ForgeConfigSpec.DoubleValue BEAM_LIFESTEAL;
    public static ForgeConfigSpec.DoubleValue BEAM_LIGHTNING_DAMAGE;

    public static ForgeConfigSpec.DoubleValue LIFESTEAL_RADIUS;
    public static ForgeConfigSpec.DoubleValue LIFESTEAL_PER_ENEMY;
    public static ForgeConfigSpec.DoubleValue LIFESTEAL_CAP;
    public static ForgeConfigSpec.DoubleValue KILL_HEAL;

    public static ForgeConfigSpec.DoubleValue ARMOR_THRESHOLD;
    public static ForgeConfigSpec.IntValue ARMOR_RESISTANCE_REFRESH;
    public static ForgeConfigSpec.IntValue ARMOR_RESISTANCE_DURATION;
    public static ForgeConfigSpec.IntValue ARMOR_RESISTANCE_AMPLIFIER;

    public static ForgeConfigSpec.DoubleValue DEATH_EXPLOSION_DAMAGE;
    public static ForgeConfigSpec.DoubleValue DEATH_EXPLOSION_RADIUS;
    public static ForgeConfigSpec.DoubleValue DEATH_SHOCKWAVE_DAMAGE;

    public static ForgeConfigSpec.DoubleValue AMBITION_SMALL;
    public static ForgeConfigSpec.DoubleValue AMBITION_MEDIUM;
    public static ForgeConfigSpec.DoubleValue AMBITION_LARGE;
    public static ForgeConfigSpec.DoubleValue AMBITION_MINIBOSS;
    public static ForgeConfigSpec.DoubleValue AMBITION_BOSS;
    public static ForgeConfigSpec.DoubleValue AMBITION_MAX_HEALTH;
    public static ForgeConfigSpec.DoubleValue AMBITION_SIZE_PER_50;
    public static ForgeConfigSpec.DoubleValue AMBITION_MAX_SIZE_BONUS;

    public static ForgeConfigSpec.DoubleValue APPLE_BEAM_DAMAGE_BONUS;
    public static ForgeConfigSpec.DoubleValue APPLE_WITHER_SKELETON_CHANCE;
    public static ForgeConfigSpec.DoubleValue SCYTHE_RADIUS;
    public static ForgeConfigSpec.IntValue SCYTHE_INTERVAL_TICKS;
    public static ForgeConfigSpec.DoubleValue SCYTHE_DRAIN;
    public static ForgeConfigSpec.DoubleValue SCYTHE_HEAL_CAP;
    public static ForgeConfigSpec.IntValue SCYTHE_ALLY_COUNT;
    public static ForgeConfigSpec.DoubleValue COFFIN_SOUL_PER_SECOND;
    public static ForgeConfigSpec.DoubleValue COFFIN_KILL_SOUL_FRACTION;
    public static ForgeConfigSpec.DoubleValue BLOOD_ARMOR;
    public static ForgeConfigSpec.DoubleValue BLOOD_TOUGHNESS;
    public static ForgeConfigSpec.DoubleValue BLOOD_KNOCKBACK_RESISTANCE;
    public static ForgeConfigSpec.IntValue BLOOD_PIGLIN_COUNT;
    public static ForgeConfigSpec.IntValue BLOOD_WITHER_SKELETON_COUNT;
    public static ForgeConfigSpec.IntValue BLOOD_COOLDOWN_TICKS;
    public static ForgeConfigSpec.DoubleValue STAR_SKULL_DAMAGE_BONUS;

    public static void buildInto(ForgeConfigSpec.Builder builder) {
        builder.push("nameless_wither");

        MAX_HEALTH = builder.defineInRange("max_health", 300.0D, 1.0D, 500.0D);
        REGEN_PER_SECOND = builder.defineInRange("regen_per_second", 2.0D, 0.0D, 100.0D);
        KNOCKBACK_RESISTANCE = builder.defineInRange("knockback_resistance", 0.5D, 0.0D, 1.0D);
        DAMAGE_CAP_FRACTION = builder.defineInRange("damage_cap_fraction", 0.08D, 0.0D, 1.0D);
        ATTACK_DAMAGE = builder.defineInRange("attack_damage", 10.0D, 0.0D, 1000.0D);
        INVULNERABLE_TICKS = builder.defineInRange("invulnerable_ticks", 15, 0, 200);
        FLYING_SPEED = builder.defineInRange("flying_speed", 0.5D, 0.0D, 10.0D);
        STATION_EXTRA_TICKS = builder.defineInRange("station_extra_ticks", 20, 0, 200);
        FLY_HEIGHT_MIN = builder.defineInRange("fly_height_min", 3.0D, 0.0D, 64.0D);
        FLY_HEIGHT_MAX = builder.defineInRange("fly_height_max", 5.0D, 0.0D, 64.0D);
        UNDEAD_DAMAGE_MULTIPLIER = builder.defineInRange("undead_damage_multiplier", 0.75D, 0.0D, 1.0D);
        NIGHT_DAMAGE_MULTIPLIER = builder.defineInRange("night_damage_multiplier", 0.75D, 0.0D, 1.0D);

        SKULL_SCALE = builder.defineInRange("skull_scale", 1.2D, 0.1D, 10.0D);
        SKULL_EXPLOSION_RADIUS = builder.defineInRange("skull_explosion_radius", 3.0D, 0.0D, 64.0D);
        SKULL_SPEED_MULTIPLIER = builder.defineInRange("skull_speed_multiplier", 1.2D, 0.1D, 10.0D);
        SKULL_FIRE_INTERVAL_MULTIPLIER = builder.defineInRange("skull_fire_interval_multiplier", 1.333333D, 1.0D, 10.0D);
        HEAD_MIN_SPACING_TICKS = builder.defineInRange("head_min_spacing_ticks", 10, 0, 200);
        SLAM_CHARGE_TICKS = builder.defineInRange("slam_charge_ticks", 27, 1, 200);
        BEAM_EVERY_SKULLS = builder.defineInRange("beam_every_skulls", 3, 1, 100);
        BEAM_DELAY_TICKS = builder.defineInRange("beam_delay_ticks", 10, 0, 200);
        BEAM_DAMAGE = builder.defineInRange("beam_damage", 8.0D, 0.0D, 1000.0D);
        BEAM_DIRECT_DAMAGE = builder.defineInRange("beam_direct_damage", 12.0D, 0.0D, 1000.0D);
        BEAM_RANGE = builder.defineInRange("beam_range", 40.0D, 1.0D, 256.0D);
        BEAM_LIFESTEAL = builder.defineInRange("beam_lifesteal", 3.0D, 0.0D, 1000.0D);
        BEAM_LIGHTNING_DAMAGE = builder.defineInRange("beam_lightning_damage", 8.0D, 0.0D, 1000.0D);

        LIFESTEAL_RADIUS = builder.defineInRange("lifesteal_radius", 16.0D, 0.0D, 128.0D);
        LIFESTEAL_PER_ENEMY = builder.defineInRange("lifesteal_per_enemy", 0.5D, 0.0D, 100.0D);
        LIFESTEAL_CAP = builder.defineInRange("lifesteal_cap", 3.0D, 0.0D, 100.0D);
        KILL_HEAL = builder.defineInRange("kill_heal", 5.0D, 0.0D, 1000.0D);

        ARMOR_THRESHOLD = builder.defineInRange("armor_threshold", 0.5D, 0.0D, 1.0D);
        ARMOR_RESISTANCE_REFRESH = builder.defineInRange("armor_resistance_refresh", 20, 1, 200);
        ARMOR_RESISTANCE_DURATION = builder.defineInRange("armor_resistance_duration", 300, 1, 20000);
        ARMOR_RESISTANCE_AMPLIFIER = builder.defineInRange("armor_resistance_amplifier", 0, 0, 10);

        DEATH_EXPLOSION_DAMAGE = builder.defineInRange("death_explosion_damage", 100.0D, 0.0D, 10000.0D);
        DEATH_EXPLOSION_RADIUS = builder.defineInRange("death_explosion_radius", 5.0D, 0.0D, 128.0D);
        DEATH_SHOCKWAVE_DAMAGE = builder.defineInRange("death_shockwave_damage", 20.0D, 0.0D, 10000.0D);

        AMBITION_SMALL = builder.defineInRange("ambition_small", 1.0D, 0.0D, 1000.0D);
        AMBITION_MEDIUM = builder.defineInRange("ambition_medium", 2.5D, 0.0D, 1000.0D);
        AMBITION_LARGE = builder.defineInRange("ambition_large", 5.0D, 0.0D, 1000.0D);
        AMBITION_MINIBOSS = builder.defineInRange("ambition_miniboss", 10.0D, 0.0D, 1000.0D);
        AMBITION_BOSS = builder.defineInRange("ambition_boss", 25.0D, 0.0D, 1000.0D);
        AMBITION_MAX_HEALTH = builder.defineInRange("ambition_max_health", 500.0D, 1.0D, 10000.0D);
        AMBITION_SIZE_PER_50 = builder.defineInRange("ambition_size_per_50", 0.05D, 0.0D, 1.0D);
        AMBITION_MAX_SIZE_BONUS = builder.defineInRange("ambition_max_size_bonus", 0.2D, 0.0D, 10.0D);

        APPLE_BEAM_DAMAGE_BONUS = builder.defineInRange("apple_beam_damage_bonus", 3.0D, 0.0D, 1000.0D);
        APPLE_WITHER_SKELETON_CHANCE = builder.defineInRange("apple_wither_skeleton_chance", 0.25D, 0.0D, 1.0D);
        SCYTHE_RADIUS = builder.defineInRange("scythe_radius", 16.0D, 0.0D, 128.0D);
        SCYTHE_INTERVAL_TICKS = builder.defineInRange("scythe_interval_ticks", 80, 1, 20000);
        SCYTHE_DRAIN = builder.defineInRange("scythe_drain", 2.0D, 0.0D, 1000.0D);
        SCYTHE_HEAL_CAP = builder.defineInRange("scythe_heal_cap", 10.0D, 0.0D, 1000.0D);
        SCYTHE_ALLY_COUNT = builder.defineInRange("scythe_ally_count", 3, 0, 100);
        COFFIN_SOUL_PER_SECOND = builder.defineInRange("coffin_soul_per_second", 4.0D, 0.0D, 1000.0D);
        COFFIN_KILL_SOUL_FRACTION = builder.defineInRange("coffin_kill_soul_fraction", 0.5D, 0.0D, 1.0D);
        BLOOD_ARMOR = builder.defineInRange("blood_armor", 12.0D, 0.0D, 1000.0D);
        BLOOD_TOUGHNESS = builder.defineInRange("blood_toughness", 6.0D, 0.0D, 1000.0D);
        BLOOD_KNOCKBACK_RESISTANCE = builder.defineInRange("blood_knockback_resistance", 0.5D, 0.0D, 1.0D);
        BLOOD_PIGLIN_COUNT = builder.defineInRange("blood_piglin_count", 4, 0, 100);
        BLOOD_WITHER_SKELETON_COUNT = builder.defineInRange("blood_wither_skeleton_count", 2, 0, 100);
        BLOOD_COOLDOWN_TICKS = builder.defineInRange("blood_cooldown_ticks", 1200, 1, 200000);
        STAR_SKULL_DAMAGE_BONUS = builder.defineInRange("star_skull_damage_bonus", 3.0D, 0.0D, 1000.0D);

        builder.pop();
    }

    private NamelessWitherConfig() {
    }
}
