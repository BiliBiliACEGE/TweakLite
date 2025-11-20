package net.ace;

import net.ace.config.ClientConfigs;
import net.ace.config.ConfigManager;
import net.ace.config.ServerConfigs;
import net.ace.feature.FeatureHandler;
import net.ace.network.ConfigSyncPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class TweakLiteClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // ✅ 初始化配置（静态列表已准备好）
        ConfigManager.init();

        // 注册 Tick 事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> FeatureHandler.onTick());
        FeatureHandler.init();

        // 设置配置同步（仅生存页配置需要同步）
        setupConfigSync();

        // 连接服务器时同步配置
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.getConnection() != null) {
                syncConfigNow();
            }
        });
    }

    /**
     * 监听配置变更，实时同步到服务端
     */
    private void setupConfigSync() {
        // ✅ 仅同步生存页配置（服务端需要的）
        ClientConfigs.PLAYER_ATTACK_RANGE.setValueChangeCallback(config -> {
            ServerConfigs.setAttackRange(config.getDoubleValue());
            syncConfigNow();
        });

        ClientConfigs.PLAYER_BLOCK_RANGE.setValueChangeCallback(config -> {
            ServerConfigs.setBlockRange(config.getDoubleValue());
            syncConfigNow();
        });
        // ✅ 创造配置无需同步（纯客户端功能）
    }

    /**
     * 立即发送当前配置到服务端
     */
    private void syncConfigNow() {
        ClientPlayNetworking.send(new ConfigSyncPacket(
                ClientConfigs.PLAYER_ATTACK_RANGE.getDoubleValue(),
                ClientConfigs.PLAYER_BLOCK_RANGE.getDoubleValue()
        ));
    }
}