package net.ace.mixin;

import net.ace.TweakLite;
import net.ace.config.ServerConfigs;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {
    @Unique
    private static final ResourceLocation ATTACK_RANGE_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(TweakLite.MOD_ID, "attack_range");
    @Unique
    private static final ResourceLocation BLOCK_RANGE_MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath(TweakLite.MOD_ID, "block_range");

    @Inject(method = "tick", at = @At("HEAD"))
    @SuppressWarnings("UnreachableCode")
    private void onTick(CallbackInfo ci) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        applyRangeModifiers(player);
    }

    @Unique
    private void applyRangeModifiers(ServerPlayer player) {
        updateAttribute(
                player,
                Attributes.ENTITY_INTERACTION_RANGE,
                ATTACK_RANGE_MODIFIER_ID,
                ServerConfigs.getAttackRange() - 3.0
        );

        updateAttribute(
                player,
                Attributes.BLOCK_INTERACTION_RANGE,
                BLOCK_RANGE_MODIFIER_ID,
                ServerConfigs.getBlockRange() - 4.5
        );
    }

    @Unique
    private void updateAttribute(ServerPlayer player, Holder<net.minecraft.world.entity.ai.attributes.Attribute> attributeHolder,
                                 ResourceLocation id, double value) {
        AttributeInstance instance = player.getAttributes().getInstance(attributeHolder);
        if (instance == null) return;

        AttributeModifier existingModifier = instance.getModifier(id);
        if (existingModifier != null && existingModifier.amount() == value) {
            return; // 值未变化，跳过
        }

        if (existingModifier != null) {
            instance.removeModifier(id);
        }

        if (value != 0.0) {
            instance.addTransientModifier(
                    new AttributeModifier(id, value, AttributeModifier.Operation.ADD_VALUE)
            );
        }
    }
}