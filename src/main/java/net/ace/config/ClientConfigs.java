package net.ace.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import net.ace.TweakLite;
import net.minecraft.locale.Language;

import java.util.ArrayList;
import java.util.List;

public class ClientConfigs {

    // 按分页分类（仅在客户端使用）
    public static final List<IConfigBase> GENERIC_CONFIGS = new ArrayList<>();
    public static final List<IConfigBase> SURVIVAL_CONFIGS = new ArrayList<>();
    public static final List<IConfigBase> CREATIVE_CONFIGS = new ArrayList<>();

    private static final String CONFIG_KEY = TweakLite.MOD_ID;
    private static final double MAX_ATTACK_RANGE = 128.0;
    private static final double MAX_BLOCK_RANGE = 64.0;

    // ---------------- 自动多语言基类 ----------------
    private static abstract class LocalizedConfigBoolean extends ConfigBoolean {
        public LocalizedConfigBoolean(String name, boolean defaultValue, String comment) {
            super(name, defaultValue, comment);
        }

        @Override
        public String getPrettyName() {
            return Language.getInstance().getOrDefault(this.translatedName);
        }
    }

    private static abstract class LocalizedConfigHotkey extends ConfigHotkey {
        public LocalizedConfigHotkey(String name, String defaultKey, KeybindSettings settings, String comment) {
            super(name, defaultKey, settings, comment, "", name);
        }

        @Override
        public String getPrettyName() {
            return Language.getInstance().getOrDefault(this.translatedName);
        }
    }

    // ---------------- 热键 ----------------
    public static final ConfigHotkey OPEN_GUI = new LocalizedConfigHotkey(
            CONFIG_KEY + ".config.hotkey.openConfigGui",
            "X,K",
            KeybindSettings.DEFAULT,
            CONFIG_KEY + ".config.hotkey.openConfigGui.Tooltip"
    ) {};

    // ---------------- 生存页配置 ----------------
    public static final ConfigDouble PLAYER_ATTACK_RANGE = new ConfigDouble(
            CONFIG_KEY + ".config.general.player_attack_range",
            3, 0, MAX_ATTACK_RANGE,
            CONFIG_KEY + ".config.general.player_attack_range.Tooltip"
    );

    public static final ConfigDouble PLAYER_BLOCK_RANGE = new ConfigDouble(
            CONFIG_KEY + ".config.general.player_block_range",
            5, 0, MAX_BLOCK_RANGE,
            CONFIG_KEY + ".config.general.player_block_range.Tooltip"
    );

    public static final ConfigBoolean NO_PUMPKIN_BLUR = new LocalizedConfigBoolean(
            CONFIG_KEY + ".config.render.noPumpkinBlur",
            false,
            CONFIG_KEY + ".config.render.noPumpkinBlur.Tooltip"
    ) {};

    public static final ConfigBoolean SHEARED_GOAT_HORN = new LocalizedConfigBoolean(
            CONFIG_KEY + ".config.render.sheared_goat_horn",
            false,
            CONFIG_KEY + ".config.render.sheared_goat_horn.Tooltip"
    ) {};

    // ---------------- 实体缩放配置 ----------------
    public static final ConfigDouble PLAYER_SCALE_SIZE = new ConfigDouble(
            CONFIG_KEY + ".config.general.player_scale_size",
            1.0, 0, 50,
            CONFIG_KEY + ".config.general.player_scale_size.Tooltip"
    );

    public static final ConfigDouble ENTITY_SCALE_SIZE = new ConfigDouble(
            CONFIG_KEY + ".config.general.entity_scale_size",
            1.0, 0, 50,
            CONFIG_KEY + ".config.general.entity_scale_size.Tooltip"
    );

    public static final ConfigHotkey APPLY_PLAYER_SCALE_KEY = new LocalizedConfigHotkey(
            CONFIG_KEY + ".config.hotkey.apply_player_scale_key",
            "C", KeybindSettings.DEFAULT,
            CONFIG_KEY + ".config.hotkey.apply_player_scale_key.Tooltip"
    ) {};

    public static final ConfigHotkey APPLY_ENTITY_SCALE_KEY = new LocalizedConfigHotkey(
            CONFIG_KEY + ".config.hotkey.apply_entity_scale_key",
            "V", KeybindSettings.DEFAULT,
            CONFIG_KEY + ".config.hotkey.apply_entity_scale_key.Tooltip"
    ) {};

    // ---------------- 创造页配置（仅客户端） ----------------

    public static final ConfigBoolean CREATIVE_COPY_SPAWN_EGG_NBT = new LocalizedConfigBoolean(
            CONFIG_KEY + ".config.creative.copy_spawn_egg_nbt",
            false,
            CONFIG_KEY + ".config.creative.copy_spawn_egg_nbt.Tooltip"
    ) {};



    // ---------------- 初始化分类列表 ----------------
    static {
        // 通用配置
        GENERIC_CONFIGS.add(OPEN_GUI);
        GENERIC_CONFIGS.add(APPLY_PLAYER_SCALE_KEY);
        GENERIC_CONFIGS.add(APPLY_ENTITY_SCALE_KEY);

        // 生存页配置（服务端需要同步的）
        SURVIVAL_CONFIGS.add(PLAYER_ATTACK_RANGE);
        SURVIVAL_CONFIGS.add(PLAYER_BLOCK_RANGE);
        SURVIVAL_CONFIGS.add(PLAYER_SCALE_SIZE);
        SURVIVAL_CONFIGS.add(ENTITY_SCALE_SIZE);
        SURVIVAL_CONFIGS.add(SHEARED_GOAT_HORN);
        SURVIVAL_CONFIGS.add(NO_PUMPKIN_BLUR);

        // 创造页配置（纯客户端功能）
        CREATIVE_CONFIGS.add(CREATIVE_COPY_SPAWN_EGG_NBT);
    }
}