package net.ace.client;

import net.ace.TweakLite;
import net.ace.config.ClientConfigs;
import net.ace.network.ScaleUpdatePacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ScaleKeyHandler {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final String INFO_KEY = TweakLite.MOD_ID;

    public static void onPlayerScaleKeyPressed(boolean isPlayerScale) {
        LocalPlayer player = mc.player;
        if (player == null) return;

        double scaleValue = isPlayerScale
                ? ClientConfigs.PLAYER_SCALE_SIZE.getDoubleValue()
                : ClientConfigs.ENTITY_SCALE_SIZE.getDoubleValue();

        Entity target = isPlayerScale ? player : getTargetEntity();

        if (target != null && (isPlayerScale || !(target instanceof Player))) {
            ClientPlayNetworking.send(new ScaleUpdatePacket(
                    target.getUUID(),
                    scaleValue
            ));

            String entityKey = isPlayerScale
                    ? player.getPlainTextName()
                    : target.getPlainTextName();

            Component message = Component.translatable(
                    INFO_KEY + ".message.scale_applied",
                    Component.literal(entityKey).withStyle(ChatFormatting.YELLOW),
                    Component.literal(String.format("%.1f", scaleValue)).withStyle(ChatFormatting.AQUA)
            );

            player.displayClientMessage(message, true);
        }
    }

    private static Entity getTargetEntity() {
        if (mc.hitResult instanceof EntityHitResult entityHit) {
            return entityHit.getEntity();
        }
        return null;
    }
}