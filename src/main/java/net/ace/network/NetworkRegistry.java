package net.ace.network;

import net.ace.config.ServerConfigs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class NetworkRegistry {
    public static void initServer() {
        // 注册实体缩放包
        PayloadTypeRegistry.playC2S().register(ScaleUpdatePacket.TYPE, ScaleUpdatePacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(
                ScaleUpdatePacket.TYPE,
                (packet, context) -> {
                    context.server().execute(() -> {
                        ServerPlayer player = context.player();
                        Entity targetEntity = player.level().getEntity(packet.targetUuid());
                        if (!(targetEntity instanceof LivingEntity target)) return;

                        if (target instanceof ServerPlayer targetPlayer && targetPlayer != player) return;
                        if (!(target instanceof ServerPlayer) && player.distanceTo(target) > 50) return;

                        AttributeInstance scaleAttr = target.getAttributes().getInstance(Attributes.SCALE);
                        if (scaleAttr != null) {
                            scaleAttr.setBaseValue(packet.scaleValue());
                        }
                    });
                }
        );

        PayloadTypeRegistry.playC2S().register(ConfigSyncPacket.TYPE, ConfigSyncPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(
                ConfigSyncPacket.TYPE,
                (packet, context) -> {
                    context.server().execute(() -> {
                        ServerConfigs.setAttackRange(packet.attackRange());
                        ServerConfigs.setBlockRange(packet.blockRange());

                        ServerPlayer player = context.player();
                        AttributeInstance attackAttr = player.getAttributes().getInstance(Attributes.ENTITY_INTERACTION_RANGE);
                        if (attackAttr != null) {
                            attackAttr.setBaseValue(packet.attackRange());
                        }

                        AttributeInstance blockAttr = player.getAttributes().getInstance(Attributes.BLOCK_INTERACTION_RANGE);
                        if (blockAttr != null) {
                            blockAttr.setBaseValue(packet.blockRange());
                        }
                    });
                }
        );
    }
}