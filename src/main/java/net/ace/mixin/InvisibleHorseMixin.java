package net.ace.mixin;

import net.ace.config.ClientConfigs;
import net.minecraft.client.model.AbstractEquineModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AbstractEquineModel.class)
public class InvisibleHorseMixin<T extends EquineRenderState> {

    @Shadow
    protected ModelPart headParts;

    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void onSetupAnim(T state, CallbackInfo ci) {
        // 需要判断是否是 Horse 的 RenderState，避免影响驴、骡等
        if (!(state instanceof EquineRenderState equineState)) {
            return;
        }

        boolean shouldHide = ClientConfigs.INVISIBLE_HOUSE.getBooleanValue()
                && equineState.isRidden;

        if (this.headParts != null) {
            this.headParts.visible = !shouldHide;
        }
    }
}