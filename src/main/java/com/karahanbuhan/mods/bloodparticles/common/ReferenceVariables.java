package com.karahanbuhan.mods.bloodparticles.common;

/**
 * Reference variables to get values in config
 */
public enum ReferenceVariables {
    IS_ENABLED("enabled", "Whether the blood particles are enabled or not."),
    BLOOD_MULTIPLIER("blood_multiplier", "The multiplier for how much blood particle will spawn per damage."),

    BLOOD_WHEN_ANVIL("blood_when_anvil", "Whether there should be blood particles when an anvil damages entity."),
    BLOOD_WHEN_ATTACK("blood_when_attack", "Whether there should be blood particles when an entity got attacked."),
    BLOOD_WHEN_CACTUS("blood_when_cactus", "Whether there should be blood particles when an entity contacts a cactus."),
    BLOOD_WHEN_CRAMMING("blood_when_cramming", "Whether there should be blood particles when an entity is cramming."),
    BLOOD_WHEN_CUSTOM_DAMAGE("blood_when_custom_damage", "Whether there should be blood particles when custom damage applied."),
    BLOOD_WHEN_DRAGON_BREATH("blood_when_dragon_breath", "Whether there should be blood particles when dragon breath damage."),
    BLOOD_WHEN_DROWNING("blood_when_drowning", "Whether there should be blood particles when an entity is drowning."),
    BLOOD_WHEN_DRYOUT("blood_when_dryout", "Whether there should be blood particles when an entity that should be in water is not."),
    BLOOD_WHEN_EXPLOSION("blood_when_explosion", "Whether there should be blood particles when explosion damage applied."),
    BLOOD_WHEN_FALL("blood_when_fall", "Whether there should be blood particles when an entity fall."),
    BLOOD_WHEN_FIRE("blood_when_fire", "Whether there should be blood particles when an entity is on fire."),
    BLOOD_WHEN_FLY_INTO_WALL("blood_when_fly_into_wall", "Whether there should be blood particles when an entity runs into a wall."),
    BLOOD_WHEN_HOT_FLOOR("blood_when_hot_floor", "Whether there should be blood particles when an entity steps on magma block."),
    BLOOD_WHEN_LAVA("blood_when_lava", "Whether there should be blood particles when an entity is on lava."),
    BLOOD_WHEN_LIGHTNING("blood_when_lightning", "Whether there should be blood particles when an entity is struck by lightning."),
    BLOOD_WHEN_MAGIC("blood_when_magic", "Whether there should be blood particles when an entity is hit by damage potion or spell."),
    BLOOD_WHEN_PROJECTILE("blood_when_projectile", "Whether there should be blood particles when an entity is hit by a projectile."),
    BLOOD_WHEN_STARVATION("blood_when_starvation", "Whether there should be blood particles when an entity is on starving"),
    BLOOD_WHEN_SUFFOCATION("blood_when_suffocation", "Whether there should be blood particles when an entity suffocate."),
    BLOOD_WHEN_BERRY_BUSH("blood_when_berry_bush", "Whether there should be blood particles when an entity contacts a sweet berry bush."),
    BLOOD_WHEN_THORNS("blood_when_thorns", "Whether there should be blood particles when an entity is attacked by thorns enchantment."),
    BLOOD_WHEN_VOID("blood_when_void", "Whether there should be blood particles when an entity is falling into the void."),
    BLOOD_WHEN_WITHER("blood_when_wither", "Whether there should be blood particles when an entity is withered."),

    DEFAULT_PARTICLE("default", "The blood particle effect type for entities that are not shown here."),
    BLAZE_PARTICLE("minecraft:blaze", "The blood particle effect type for blazes."),
    ENDERMAN_PARTICLE("minecraft:enderman", "The blood particle effect type for endermen."),
    ENDER_DRAGON_PARTICLE("minecraft:ender_dragon", "The blood particle effect type for ender dragons."),
    MAGMA_CUBE_PARTICLE("minecraft:magma_cube", "The blood particle effect type for magma cubes."),
    PHANTOM_PARTICLE("minecraft:phantom", "The blood particle effect type for phantoms."),
    SLIME_PARTICLE("minecraft:slime", "The blood particle effect type for slimes."),
    ZOMBIFIED_PIGLIN_PARTICLE("minecraft:zombified_piglin", "The blood particle effect type for zombified piglins.");

    public final String name;
    public final String description;

    ReferenceVariables(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
