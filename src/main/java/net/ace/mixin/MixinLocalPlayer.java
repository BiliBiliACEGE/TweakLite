package net.ace.mixin;

import net.ace.TweakLite;
import net.ace.config.ClientConfigs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
@Environment(EnvType.CLIENT)
public class MixinLocalPlayer {

    @Unique
    private static final ResourceLocation ATTACK_RANGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(TweakLite.MOD_ID, "attack_range");
    @Unique
    private static final ResourceLocation BLOCK_RANGE_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(TweakLite.MOD_ID, "block_range");

    @Inject(method = "tick", at = @At("HEAD"))
    @SuppressWarnings("UnreachableCode")
    private void onTick(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        applyRangeModifiers(player);
    }

    @Unique
    private void applyRangeModifiers(LocalPlayer player) {
        updateAttribute(
                player,
                Attributes.ENTITY_INTERACTION_RANGE,
                ATTACK_RANGE_MODIFIER_ID,
                ClientConfigs.PLAYER_ATTACK_RANGE.getDoubleValue() - 3.0
        );

        updateAttribute(
                player,
                Attributes.BLOCK_INTERACTION_RANGE,
                BLOCK_RANGE_MODIFIER_ID,
                ClientConfigs.PLAYER_BLOCK_RANGE.getDoubleValue() - 4.5
        );
    }

    @Unique
    private void updateAttribute(LocalPlayer player, Holder<net.minecraft.world.entity.ai.attributes.Attribute> attributeHolder,
                                 ResourceLocation id, double value) {
        AttributeInstance instance = player.getAttributes().getInstance(attributeHolder);
        if (instance == null) return;

        AttributeModifier existingModifier = instance.getModifier(id);

        if (existingModifier != null && existingModifier.amount() == value) {
            return;
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
