package net.ace.mixin;

import net.ace.config.ClientConfigs;
import net.ace.util.AgeLockHelper;
import net.ace.util.AgeableDataAccess;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AgeableMob.class)
public class AgeableMobMixin {

    @Unique
    private static final EntityDataAccessor<Boolean> DATA_BABY_LOCK =
            AgeableDataAccess.BABY_LOCK;

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void onDefineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(DATA_BABY_LOCK, false);
    }

    /* 拦截所有 setAge（最稳） */
    @Inject(method = "setAge", at = @At("HEAD"), cancellable = true)
    @SuppressWarnings("UnreachableCode")
    private void onSetAge(int age, CallbackInfo ci) {
        AgeableMob self = (AgeableMob) (Object) this;
        if (ClientConfigs.BABIES_FOREVER.getBooleanValue()
                && AgeLockHelper.isBabyLock(self)
                && age >= 0) {
            ci.cancel();
        }
    }

    /* 拦截喂食 ageUp */
    @Inject(method = "ageUp(I)V", at = @At("HEAD"), cancellable = true)
    @SuppressWarnings("UnreachableCode")
    private void onAgeUp(int amount, CallbackInfo ci) {
        AgeableMob self = (AgeableMob) (Object) this;
        if (ClientConfigs.BABIES_FOREVER.getBooleanValue()
                && AgeLockHelper.isBabyLock(self)) {
            ci.cancel();
        }
    }
}