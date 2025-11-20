package net.ace;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.ace.config.ConfigManager;

public class ModMenuImpl implements ModMenuApi {
    /* 告诉 ModMenu：点“配置”按钮时打开哪个屏幕 */
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigManager.Gui();
    }
}