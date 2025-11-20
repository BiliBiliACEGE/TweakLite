package net.ace.config;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Path;
import java.nio.file.Files;


public class ServerConfigs {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("TweakLite-Server.json");
    private static JsonObject configData = new JsonObject();

    private static final double DEFAULT_ATTACK_RANGE = 3.0;
    private static final double DEFAULT_BLOCK_RANGE = 5.0;

    // 缓存值，避免重复读取文件
    private static double cachedAttackRange = DEFAULT_ATTACK_RANGE;
    private static double cachedBlockRange = DEFAULT_BLOCK_RANGE;

    static {
        load();
    }

    /**
     * 从文件加载配置（服务端启动时调用）
     */
    public static void load() {
        try {
            if (Files.exists(CONFIG_PATH)) {
                String content = Files.readString(CONFIG_PATH);
                configData = com.google.gson.JsonParser.parseString(content).getAsJsonObject();
            } else {
                configData = new JsonObject();
                configData.addProperty("player_attack_range", DEFAULT_ATTACK_RANGE);
                configData.addProperty("player_block_range", DEFAULT_BLOCK_RANGE);
                save();
            }

            // 更新缓存
            cachedAttackRange = configData.has("player_attack_range")
                    ? configData.get("player_attack_range").getAsDouble()
                    : DEFAULT_ATTACK_RANGE;
            cachedBlockRange = configData.has("player_block_range")
                    ? configData.get("player_block_range").getAsDouble()
                    : DEFAULT_BLOCK_RANGE;

        } catch (Exception e) {
            e.printStackTrace();
            // 出错时使用默认值
            configData = new JsonObject();
            configData.addProperty("player_attack_range", DEFAULT_ATTACK_RANGE);
            configData.addProperty("player_block_range", DEFAULT_BLOCK_RANGE);
        }
    }

    /**
     * 保存配置到文件
     */
    public static void save() {
        try {
            Files.writeString(CONFIG_PATH, configData.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存的攻击范围（服务端 Mixin 调用）
     */
    public static double getAttackRange() {
        return cachedAttackRange;
    }

    /**
     * 获取缓存的方块交互范围（服务端 Mixin 调用）
     */
    public static double getBlockRange() {
        return cachedBlockRange;
    }

    /**
     * 设置攻击范围（从网络包调用）
     * 更新文件 + 缓存
     */
    public static void setAttackRange(double value) {
        configData.addProperty("player_attack_range", value);
        cachedAttackRange = value;
        save();
    }

    /**
     * 设置方块交互范围（从网络包调用）
     * 更新文件 + 缓存
     */
    public static void setBlockRange(double value) {
        configData.addProperty("player_block_range", value);
        cachedBlockRange = value;
        save();
    }
}