package net.ace.feature;

import net.ace.client.ScaleKeyHandler;
import net.ace.config.ClientConfigs;
import net.ace.config.ConfigManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import fi.dy.masa.malilib.hotkeys.IKeybind;


@Environment(EnvType.CLIENT)
public class FeatureHandler {

    public static void init() {
        // 客户端 tick 注册
        ClientTickEvents.END_CLIENT_TICK.register(client -> onTick());
    }

    public static void onTick() {

        // 热键触发 GUI
        IKeybind openGui = ClientConfigs.OPEN_GUI.getKeybind();
        if (openGui != null) {
            openGui.updateIsPressed();        // 必须刷新状态
            if (openGui.isPressed()) {        // 检查按下
                Minecraft.getInstance().setScreen(ConfigManager.createGui());
            }
        }
        IKeybind apply_player_scale_key = ClientConfigs.APPLY_PLAYER_SCALE_KEY.getKeybind();
        IKeybind apply_entity_scale_key = ClientConfigs.APPLY_ENTITY_SCALE_KEY.getKeybind();
        if(apply_player_scale_key != null){
            apply_player_scale_key.updateIsPressed();
            if (apply_player_scale_key.isPressed()){

                ScaleKeyHandler.onPlayerScaleKeyPressed(true);

            }
        }
        if(apply_entity_scale_key != null){
            apply_entity_scale_key.updateIsPressed();
            if (apply_entity_scale_key.isPressed()){

                ScaleKeyHandler.onPlayerScaleKeyPressed(false);

            }
        }
    }
}
