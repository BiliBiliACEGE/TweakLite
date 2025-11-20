package net.ace.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.ace.TweakLite;
import net.minecraft.client.Minecraft;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.util.List;

public class ConfigManager {
    private static final File CONFIG_FILE =
            FabricLoader.getInstance().getConfigDir().resolve("TweakLite.json").toFile();

    public static void init() {
        if (!CONFIG_FILE.exists()) {
            save();
        }
        JsonElement root = JsonUtils.parseJsonFile(CONFIG_FILE);
        JsonObject obj = (root != null && root.isJsonObject())
                ? root.getAsJsonObject() : new JsonObject();

        ConfigUtils.readConfigBase(obj, "", ClientConfigs.GENERIC_CONFIGS);
        ConfigUtils.readConfigBase(obj, "", ClientConfigs.SURVIVAL_CONFIGS);
        ConfigUtils.readConfigBase(obj, "", ClientConfigs.CREATIVE_CONFIGS);
    }

    public static void save() {
        JsonObject obj = new JsonObject();
        ConfigUtils.writeConfigBase(obj, "", ClientConfigs.GENERIC_CONFIGS);
        ConfigUtils.writeConfigBase(obj, "", ClientConfigs.SURVIVAL_CONFIGS);
        ConfigUtils.writeConfigBase(obj, "", ClientConfigs.CREATIVE_CONFIGS);
        JsonUtils.writeJsonToFile(obj, CONFIG_FILE);
    }

    public static Gui createGui() {
        return new Gui();
    }

    public static class Gui extends GuiConfigsBase {
        private static Tab selectedTab = Tab.GENERIC;

        public enum Tab {
            GENERIC("Generic",  TweakLite.MOD_ID + ".tab.generic", ClientConfigs.GENERIC_CONFIGS),
            SURVIVAL("Survival", TweakLite.MOD_ID + ".tab.survival", ClientConfigs.SURVIVAL_CONFIGS),
            CREATIVE("Creative", TweakLite.MOD_ID + ".tab.creative", ClientConfigs.CREATIVE_CONFIGS);

            private final String translationKey;
            private final List<fi.dy.masa.malilib.config.IConfigBase> configs;

            Tab(String name, String translationKey, List<fi.dy.masa.malilib.config.IConfigBase> configs) {
                this.translationKey = translationKey;
                this.configs = configs;
            }

            public String getDisplayName() {
                return StringUtils.translate(translationKey);
            }

            public List<fi.dy.masa.malilib.config.IConfigBase> getConfigs() {
                return configs;
            }
        }

        public Gui() {
            super(10, 50, TweakLite.MOD_ID, null, TweakLite.MOD_ID + ".config.title");
        }

        @Override
        public void initGui() {
            super.initGui();
            this.clearOptions();

            int x = 10;
            int y = 26;
            for (Tab tab : Tab.values()) {
                int width = Minecraft.getInstance().font.width(tab.getDisplayName()) + 10;
                ButtonGeneric button = new ButtonGeneric(x, y, width, 18, tab.getDisplayName());
                button.setEnabled(tab != selectedTab);


                this.addButton(button, (buttonBase, mouseButton) -> {
                    selectedTab = tab;
                    Gui.this.initGui();
                });

                x += width + 2;
            }
        }

        @Override
        public List<ConfigOptionWrapper> getConfigs() {
            return ConfigOptionWrapper.createFor(selectedTab.getConfigs());
        }
    }
}