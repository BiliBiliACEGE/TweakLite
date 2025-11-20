package net.ace.mixin;

import net.ace.config.ClientConfigs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
@Environment(EnvType.CLIENT)
public abstract class PumpkinBlurMixin {

    /* 1.21.10 映射：Gui#renderTextureOverlay */
    @Inject(method = "renderTextureOverlay", at = @At("HEAD"), cancellable = true)
    private void ace$cancelPumpkinBlur(CallbackInfo ci) {
        if (ClientConfigs.NO_PUMPKIN_BLUR.getBooleanValue()) {
            ci.cancel();
        }
    }
}