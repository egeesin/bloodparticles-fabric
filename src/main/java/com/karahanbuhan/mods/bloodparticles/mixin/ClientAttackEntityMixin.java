package com.karahanbuhan.mods.bloodparticles.mixin;

import com.karahanbuhan.mods.bloodparticles.api.event.DamageLivingEntityCallback;
import com.karahanbuhan.mods.bloodparticles.common.MixinVariables;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin class for injecting {@link DamageLivingEntityCallback} into ClientPlayerInteractionManager on entityAttack()
 */
@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientAttackEntityMixin {
    @Inject(method = "attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V"))
    @Environment(EnvType.CLIENT)
    private void onAttackEntity(PlayerEntity player, Entity entity, CallbackInfo ci) {
        if (!(entity instanceof LivingEntity) || MinecraftClient.getInstance().isInSingleplayer())
            return;

        float amount = 1.0f;
        ItemStack hand = player.getMainHandStack();
        if (!hand.isEmpty()) {
            Item item = hand.getItem();
            if (item instanceof ToolItem)
                amount = ((ToolItem) item).getMaterial().getAttackDamage();
            if (item instanceof SwordItem)
                amount = ((SwordItem) item).getAttackDamage();
        }

        DamageLivingEntityCallback.EVENT.invoker().interact((LivingEntity) entity, DamageSource.player(player), amount);
        MixinVariables.clientAttacked = true;
    }

}
