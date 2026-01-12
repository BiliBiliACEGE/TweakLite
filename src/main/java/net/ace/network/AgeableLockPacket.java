package net.ace.network;

import net.ace.TweakLite;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AgeableLockPacket(int entityId, boolean lock) implements CustomPacketPayload {
    public static final Type<AgeableLockPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(TweakLite.MOD_ID, "ageable_lock"));
    public static final StreamCodec<FriendlyByteBuf, AgeableLockPacket> CODEC =
            StreamCodec.ofMember(AgeableLockPacket::write, AgeableLockPacket::new);

    public AgeableLockPacket(FriendlyByteBuf buf) {
        this(buf.readVarInt(), buf.readBoolean());
    }
    private void write(FriendlyByteBuf buf) {
        buf.writeVarInt(entityId);
        buf.writeBoolean(lock);
    }
    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
}