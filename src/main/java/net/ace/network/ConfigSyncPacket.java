package net.ace.network;

import net.ace.TweakLite;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * 客户端配置变更时实时同步到服务端
 * 确保专用服务器上每个玩家的配置独立生效
 */
public final class ConfigSyncPacket implements CustomPacketPayload {
    private final double attackRange;
    private final double blockRange;

    public static final Type<ConfigSyncPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(TweakLite.MOD_ID, "config_sync")
    );

    public static final StreamCodec<FriendlyByteBuf, ConfigSyncPacket> CODEC = StreamCodec.ofMember(
            ConfigSyncPacket::write, ConfigSyncPacket::new
    );

    public ConfigSyncPacket(double attackRange, double blockRange) {
        this.attackRange = attackRange;
        this.blockRange = blockRange;
    }

    public ConfigSyncPacket(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble());
    }

    public double attackRange() { return attackRange; }
    public double blockRange() { return blockRange; }

    private void write(FriendlyByteBuf buf) {
        buf.writeDouble(attackRange);
        buf.writeDouble(blockRange);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}