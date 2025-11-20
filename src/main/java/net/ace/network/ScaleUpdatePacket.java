package net.ace.network;

import net.ace.TweakLite;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public final class ScaleUpdatePacket implements CustomPacketPayload {
    private final UUID targetUuid;
    private final double scaleValue;


    public static final Type<ScaleUpdatePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TweakLite.MOD_ID, "scale_update"));


    public static final StreamCodec<FriendlyByteBuf, ScaleUpdatePacket> CODEC = StreamCodec.ofMember(
            ScaleUpdatePacket::write, ScaleUpdatePacket::new
    );

    public ScaleUpdatePacket(UUID targetUuid, double scaleValue) {
        this.targetUuid = targetUuid;
        this.scaleValue = scaleValue;
    }


    public ScaleUpdatePacket(FriendlyByteBuf buf) {
        this(buf.readUUID(), buf.readDouble());
    }


    public UUID targetUuid() {
        return targetUuid;
    }

    public double scaleValue() {
        return scaleValue;
    }


    private void write(FriendlyByteBuf buf) {
        buf.writeUUID(targetUuid);
        buf.writeDouble(scaleValue);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}