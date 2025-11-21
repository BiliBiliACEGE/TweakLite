English | [中文](readme.md)
# TweakLite

TweakLite is a lightweight Fabric mod that provides a set of configurable client/server-side tweaks to improve gameplay experience and facilitate debugging and development. The following documentation is based on the configuration options in the `ClientConfigs` source code, listing available features, default values, and hotkeys.

## Quick Overview

- Use the in-game configuration interface (Hotkey: `X + K`, by default) to open and adjust all settings.
- Configuration is divided into three groups: "Generic", "Survival", and "Creative"; some settings on the Survival tab will be synchronized with the server.

## Configuration Items

### Survival Tab Configuration (Client & Server)

- `player_attack_range` (Player Attack Range)
    - Default: 3
    - Range: 0 — 128
    - Description: Adjusts the player's attack distance (melee/hit range).

- `player_block_range` (Block Interaction Range)
    - Default: 5
    - Range: 0 — 64
    - Description: Adjusts the maximum distance for player block interactions (placing/mining/using).

- `noPumpkinBlur` (Remove Carved Pumpkin Overlay)
    - Default: false
    - Type: Boolean
    - Description: Whether to remove the screen overlay effect when wearing a carved pumpkin.

- `sheared_goat_horn` (Shear Goat Horns)
    - Default: false
    - Type: Boolean
    - Description: Whether to enable shearing goat horns with shears.

### Entity Scaling Configuration

- `player_scale_size` (Player Scale Size)
    - Default: 1.0
    - Range: 0 — 50
    - Description: Scales the player model size proportionally (1.0 = normal size).

- `entity_scale_size` (Entity Scale Size)
    - Default: 1.0
    - Range: 0 — 50
    - Description: Scales the default display size of other entities proportionally.

### Creative Tab Configuration (Client-only)

- `copy_spawn_egg_nbt` (Copy Entity NBT to Spawn Egg)
    - Default: false
    - Type: Boolean
    - Description: When enabled in Creative mode, use **Ctrl + Middle Click** to copy target entity NBT to a spawn egg.

### Generic Configuration / Hotkeys

- `openConfigGui` (Open Configuration GUI Hotkey)
    - Default Key: X + K
    - Description: Press this key combination in-game to open the TweakLite configuration GUI, where all available options can be adjusted.

- `apply_player_scale_key` (Apply Player Scale Hotkey)
    - Default Key: C
    - Description: Press this hotkey to apply `player_scale_size` to the current player.

- `apply_entity_scale_key` (Apply Entity Scale Hotkey)
    - Default Key: V
    - Description: Press this hotkey to apply `entity_scale_size` to the selected entity.

## Usage Examples and Notes

- It is recommended to test and verify settings in a single-player or test server before using them on a production server, as some Survival tab settings require server-side support to take effect or may be overridden.
- Extreme values for attack/interaction ranges may affect gameplay experience or cause detection/rendering issues; please use caution with values greater than 10.

## Installation and Building from Source (Windows PowerShell)

Place the generated jar file into Minecraft's `mods/` folder and run with a compatible Fabric launcher.

Build from the project root directory (Windows PowerShell):

```powershell
# Navigate to project root and build
cd 'D:\My project\javapojact\TweakLite-1.21.10' ; .\gradlew.bat build

# Or shorthand equivalent:
.\gradlew build