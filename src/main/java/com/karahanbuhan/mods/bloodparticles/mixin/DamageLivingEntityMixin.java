package com.karahanbuhan.mods.bloodparticles.mixin;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.common.MixinVariables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;

/**
 * Mixin class for injecting {@link DamageLivingEntityCallback} into LivingEntity on damage()
 */
@Mixin(LivingEntity.class)
public abstract class DamageLivingEntityMixin {
    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"))
    private void onDamageLivingEntity(DamageSource source, float amount, final CallbackInfoReturnable<Boolean> info) {
        DamageLivingEntityCallback.EVENT.invoker().interact((LivingEntity) (Object) this, source, amount);
    }

    @Environment(EnvType.CLIENT)
    @Inject(method = "handleStatus(B)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private void onAnimateDamageLivingEntity(byte status, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        LivingEntity entity = (LivingEntity) (Object) this;

        if (MinecraftClient.getInstance().isInSingleplayer())
            return;

        MixinVariables.AttackData data = MixinVariables.lastAttackData;
        if (data != null && !data.handled && !data.isOutdated()) {
            DamageLivingEntityCallback.EVENT.invoker().interact(entity, DamageSource.player(data.player), data.amount);
            return;
        }

        DamageSource source = DamageSource.GENERIC;
        float amount = 5.0f;
        switch (status) {
            case 33: // If the damage source is thorns enchantment
                amount = 3.0f; // TODO Get the actual amount
                source = DamageSource.thorns(null);
                break;
            case 36: // If the damage source is drowning
                source = DamageSource.DROWN;
                amount = 2.0f;
                break;
            case 37: // If the damage source is fire
                if (doesEntityContactBlock(entity, Blocks.MAGMA_BLOCK)) {
                    amount = 1.0f;
                    source = DamageSource.HOT_FLOOR;
                } else if (doesEntityContactBlock(entity, Blocks.LAVA)) {
                    amount = 4.0f;
                    source = DamageSource.LAVA;
                } else {
                    float fireProtection = 0;
                    for (ItemStack itemStack : entity.getArmorItems())
                        fireProtection += 8 * EnchantmentHelper.getLevel(Enchantments.FIRE_PROTECTION, itemStack);
                    fireProtection = Math.min(fireProtection, 80);

                    amount = 2.0f * (100 - fireProtection) / 100;
                    source = DamageSource.ON_FIRE;
                }
                break;
            case 44: // If the damage source is berry bush
                amount = 1.0f;
                source = DamageSource.SWEET_BERRY_BUSH;
                break;
            case 2: // If the damage source is generic, we will need to do client-side calculations
                if (entity.getPos().getY() <= -64) {
                    amount = 4.0f;
                    source = DamageSource.OUT_OF_WORLD;
                    break;
                }

                if (entity.isInsideWall()) {
                    amount = 1.0f;
                    source = DamageSource.IN_WALL;
                    break;
                }

                if (doesEntityContactBlock(entity, Blocks.CACTUS)) {
                    amount = 1.0f;
                    source = DamageSource.CACTUS;
                    break;
                }

                if (entity.world.getOtherEntities(entity, entity.getBoundingBox().expand(1))
                        .stream()
                        .anyMatch(lightning -> lightning instanceof LightningEntity)) {
                    amount = 5.0f;
                    source = DamageSource.LIGHTNING_BOLT;
                    break;
                }

                if (entity.world.getOtherEntities(entity, entity.getBoundingBox().expand(1))
                        .stream()
                        .anyMatch(fallingBlock -> fallingBlock.getType().equals(EntityType.FALLING_BLOCK) &&
                                ((FallingBlockEntity) fallingBlock).getBlockState().getBlock() instanceof AnvilBlock)) {
                    amount = 18.0f; // TODO Get the actual amount
                    source = DamageSource.ANVIL;
                    break;
                }

                if (entity.world.getOtherEntities(entity, entity.getBoundingBox().expand(1), Entity::isAlive).size() >= 24) {
                    amount = 6.0f;
                    source = DamageSource.CRAMMING;
                    break;
                }

                if (entity instanceof PlayerEntity && ((PlayerEntity) entity).getHungerManager().getFoodLevel() == 0) {
                    amount = 1.0f;
                    source = DamageSource.STARVE;
                }

                break;
        }

        DamageLivingEntityCallback.EVENT.invoker().interact(entity, source, amount);
    }

    private boolean doesEntityContactBlock(LivingEntity entity, Block block) {
        Box box = entity.getBoundingBox();
        BlockPos blockPos = new BlockPos(box.minX + 0.002D, box.minY + 0.002D, box.minZ + 0.002D);
        BlockPos blockPos2 = new BlockPos(box.maxX - 0.002D, box.maxY - 0.002D, box.maxZ - 0.002D);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = blockPos.getX(); i <= blockPos2.getX(); ++i)
            for (int j = blockPos.getY(); j <= blockPos2.getY(); ++j)
                for (int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, j, k);
                    if (entity.world.getBlockState(mutable).getBlock().equals(block))
                        return true;
                }
        return false;
    }
}