package net.ace.mixin;

import net.ace.config.ClientConfigs;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Goat.class)
public class GoatEntityMixin {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    @SuppressWarnings("UnreachableCode")
    private void onMobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        // 配置未启用则直接返回
        if (!ClientConfigs.SHEARED_GOAT_HORN.getBooleanValue()) {
            return;
        }

        Goat goat = (Goat)(Object)this;
        ItemStack stack = player.getItemInHand(hand);
        Level level = goat.level();

        // 仅在服务端逻辑执行
        if (level.isClientSide()) {
            return;
        }

        if (stack.is(Items.SHEARS) && !goat.isBaby()) {
            boolean droppedHorn = goat.dropHorn();

            if (droppedHorn) {
                // 减少剪刀耐久
                stack.hurtAndBreak(1, player, hand);

                // 播放使用动画
                player.swing(hand);

                // 播放剪角音效
                level.playSound(
                        null,
                        goat.blockPosition(),
                        SoundEvents.GOAT_HORN_BREAK,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );

                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}