package net.ace.event;

import net.ace.config.ClientConfigs;
import net.ace.network.AgeableLockPacket;
import net.ace.util.AgeLockHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ScissorInteract {
    public static void register() {
        UseEntityCallback.EVENT.register((Player player,
                                          Level level,
                                          InteractionHand hand,
                                          net.minecraft.world.entity.Entity entity,
                                          EntityHitResult hitResult) -> {

            if (!ClientConfigs.BABIES_FOREVER.getBooleanValue()) return InteractionResult.PASS;
            if (!(entity instanceof AgeableMob mob) || !mob.isBaby()) return InteractionResult.PASS;
            if (!player.getItemInHand(hand).is(Items.SHEARS)) return InteractionResult.PASS;

            /* 客户端只做 swing */
            if (level.isClientSide()) {
                // 构造记录类对象 → 一步发完
                AgeableLockPacket pkt = new AgeableLockPacket(mob.getId(),
                        !AgeLockHelper.isBabyLock(mob));
                ClientPlayNetworking.send(pkt);
                player.swing(hand);
                return InteractionResult.SUCCESS;
            }

            /* 服务端逻辑 */
            boolean locked = AgeLockHelper.isBabyLock(mob);
            AgeLockHelper.setBabyLock(mob, !locked);
            player.getItemInHand(hand).hurtAndBreak(1, player, hand);
            level.playSound(null, mob.blockPosition(), SoundEvents.GOAT_HORN_BREAK,
                    SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        });
    }
}