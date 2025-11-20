package net.ace.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.ace.TweakLite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ace.config.ClientConfigs;
import net.ace.util.NbtValueOutput;

import java.util.ArrayList;
import java.util.List;

@Mixin(Minecraft.class)
@Environment(EnvType.CLIENT)
public class MixinCopyEntityNBT {
    @Shadow
    public HitResult hitResult;

    @Shadow
    public LocalPlayer player;

    @Inject(method = "pickBlock", at = @At("HEAD"), cancellable = true)
    private void onPickBlock(CallbackInfo ci) {
        if (!ClientConfigs.CREATIVE_COPY_SPAWN_EGG_NBT.getBooleanValue()) {
            return;
        }

        if (!(this.hitResult instanceof EntityHitResult entityHitResult)) {
            return;
        }

        if (this.player == null || !this.player.isCreative()) {
            return;
        }

        Entity entity = entityHitResult.getEntity();
        ItemStack pickResult = entity.getPickResult();

        if (pickResult == null) {
            return;
        }

        Item item = pickResult.getItem();
        if (!(item instanceof SpawnEggItem)) {
            return;
        }

        Window window = Minecraft.getInstance().getWindow();
        if (!InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL)) {
            return;
        }

        // ✅ 保存实体NBT
        CompoundTag entityTag = new CompoundTag();
        NbtValueOutput output = new NbtValueOutput(entityTag);

        if (!entity.saveAsPassenger(output)) {
            return;
        }

        entityTag.remove("Pos");
        entityTag.remove("Motion");
        entityTag.remove("Rotation");
        entityTag.remove("UUID");
        entityTag.remove("Dimension");

        // ✅ 使用Codec创建TypedEntityData
        Codec<TypedEntityData<EntityType<?>>> codec = TypedEntityData.codec(EntityType.CODEC);
        DataResult<TypedEntityData<EntityType<?>>> result = codec.parse(net.minecraft.nbt.NbtOps.INSTANCE, entityTag);

        result.result().ifPresent(typedData -> {
            ItemStack enhancedEgg = pickResult.copy();
            enhancedEgg.set(DataComponents.ENTITY_DATA, typedData);

            // ✅ 添加自定义描述（移除动画，改为文字提示）
            List<Component> loreLines = new ArrayList<>();
            ItemLore originalLore = enhancedEgg.get(DataComponents.LORE);
            if (originalLore != null) {
                loreLines.addAll(originalLore.lines());
            }
            // 金色斜体提示
            loreLines.add(Component.translatable(TweakLite.MOD_ID + ".item.text.info"));
            enhancedEgg.set(DataComponents.LORE, new ItemLore(loreLines));

            if (this.player.getInventory().add(enhancedEgg)) {
                ci.cancel();
            }
        });
    }
}