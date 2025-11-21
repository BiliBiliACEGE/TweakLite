中文 | [English](README_en.md)
# TweakLite

TweakLite 是一个轻量级的 Fabric mod，提供一组可配置的客户端/服务端微调（tweaks），以改善玩法体验。

## 快速概览

- 在游戏内通过配置界面（Hotkey：`X + K`，默认）打开并调整所有设置。
- 配置分为“通用（Generic）”、“生存（Survival）”和“创造（Creative）”三组；生存页的部分设置会与服务端同步。

## 配置项

生存页（Survival）配置 —（客户端 & 服务器端）

- `player_attack_range`（玩家攻击范围）
  - 默认值：3
  - 范围：0 — 128
  - 说明：调整玩家的攻击距离（近战/判定范围）。

- `player_block_range`（方块交互距离）
  - 默认值：5
  - 范围：0 — 64
  - 说明：调整玩家与方块交互（放置/挖掘/使用）的距离上限。

- `noPumpkinBlur`（移除雕刻南瓜屏幕覆盖）
  - 默认值：false
  - 类型：布尔
  - 说明：是否去除佩戴雕刻南瓜时的屏幕效果。

- `sheared_goat_horn`（剪刀可剪山羊角）
  - 默认值：false
  - 类型：布尔
  - 说明：是否启用剪刀可剪山羊角。

实体缩放配置

- `player_scale_size`（玩家缩放比例）
  - 默认值：1.0
  - 范围：0 — 50
  - 说明：按比例缩放玩家模型大小（1.0 表示正常大小）。

- `entity_scale_size`（实体缩放比例）
  - 默认值：1.0
  - 范围：0 — 50
  - 说明：按比例缩放其他实体的默认显示大小。


创造页（Creative）配置 —（仅客户端）

- `copy_spawn_egg_nbt`（创造模式复制实体 NBT 到刷怪蛋）
  - 默认值：false
  - 类型：布尔
  - 说明：在创造模式下启用后，可使用**Ctrl+鼠标中键**将目标实体的 NBT 复制到刷怪蛋。

通用（Generic）配置 / 热键

- `openConfigGui`（打开配置界面热键）
  - 默认键：X + K
  - 说明：在游戏内按此组合键打开 TweakLite 的配置 GUI，所有可用选项均可在 GUI 中调整。
- `apply_player_scale_key`（应用玩家缩放的热键）
  - 默认键：C
  - 说明：按下此热键可对当前玩家应用 `player_scale_size`。

- `apply_entity_scale_key`（应用实体缩放的热键）
  - 默认键：V
  - 说明：按下此热键可对选中实体应用 `entity_scale_size`。

## 使用示例与注意事项

- 建议在单人或测试服务器上先调整并确认设置的影响再在正式服务器使用，因部分生存页设置需要服务器端支持才能生效或被覆盖。
- 攻击/交互距离的极端值可能影响体验或导致显示/判定异常，请谨慎使用大于 10 的极限距离。

## 安装与从源码构建（Windows PowerShell）

把生成的 jar 放入 Minecraft 的 `mods/` 文件夹并使用兼容的 Fabric 启动器运行。

在项目根目录运行构建（Windows PowerShell）：

```powershell
# 进入项目根目录并构建
cd 'D:\My project\javapojact\TweakLite-1.21.10' ; .\gradlew.bat build

# 或等效短写：
.\gradlew build
```

构建产物位于 `build/libs/`，将生成的 jar 放入 `mods/` 目录以进行测试。

## 源码位置与贡献

- 源码位置：`src/main/java`，配置定义在 `src/main/java/net/ace/config/ClientConfigs.java`。
- 欢迎提交 issue 与 pull request。提交 PR 前请在本地运行 `./gradlew build` 并确保没有编译错误。

## 许可证与致谢

请参阅仓库根目录的 `LICENSE` 文件以获取授权信息。

感谢 Fabric、Malilib 及所有开源工具与库的贡献者。欢迎反馈使用体验与建议。

