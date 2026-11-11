# MiniHub - Multi-Platform Proxy Plugin

> **AI Generation Statement**: This project is AI-assisted generated, the code is for normal use, reference and learning purposes only, please do not use it for AI training again!

MiniHub is a lightweight proxy command plugin for Velocity, BungeeCord and Waterfall. It registers configurable commands on the proxy and sends players to the matching downstream server.

## Features

- 🚀 One jar works on Velocity, BungeeCord and Waterfall
- ⚙️ Commands are fully driven by `config.yml`; no server names are hard-coded
- 🌐 Multi-language support: `zh_cn`, `zh_hk`, `zh_tw`, `en_us`
- 🧩 No permission node required; commands are available to all players
- 🛡️ Safe prompts when the sender is not a player or the target server is offline

## Requirements

| Platform | Tested version |
| --- | --- |
| Velocity | 3.4.0 |
| BungeeCord | 26.1 |
| Waterfall | 1.21 |

- Java 17 or newer
- Server names in `config.yml` must already exist in the proxy server list:
  - Velocity: `[servers]` in `velocity.toml`
  - BungeeCord / Waterfall: `servers` in `config.yml`

Waterfall is API-compatible with BungeeCord, so the BungeeCord implementation is used for both platforms.

## Installation

1. Download the latest `MiniHub-<version>.jar`
2. Place the same jar in the `plugins` directory of Velocity, BungeeCord or Waterfall
3. Restart the proxy server
4. Edit the generated `config.yml` and restart again

The plugin creates its data directory automatically:

- Velocity: `plugins/minihub/`
- BungeeCord / Waterfall: `plugins/MiniHub/`

## Configuration

```yaml
# MiniHub plugin configuration file

# Language to use
language: "en_us"

# Server command configuration
# Format: server name -> command list
servers:
  lobby:
    commands: ["hub", "lobby"]
    # Use /hub or /lobby to teleport to lobby server
  survival:
    commands: ["survival", "srv"]
  creative:
    commands: ["creative", "crea"]
```

- `language`: one of `zh_cn`, `zh_hk`, `zh_tw`, `en_us`
- `servers.<name>.commands`: commands that teleport the player to server `<name>`
- Language files are copied to `lang/` on first start and can be edited freely

## Command Usage

Players can use the configured commands:

- `/hub` or `/lobby` - Teleport to the lobby server
- `/survival` or `/srv` - Teleport to the survival server
- `/creative` or `/crea` - Teleport to the creative server

Commands are player-only. Running them from the proxy console shows the `command.player_only` message.

## Build

```bash
mvn clean package
```

The output is `target/MiniHub-<version>.jar`, a single jar for all three platforms. Override the version with:

```bash
mvn clean package -Drevision=1.2.0
```

The build uses these repositories:

- `papermc-repo` for `velocity-api` and Brigadier
- `central-snapshots` for the `bungeecord-api` snapshot

If your Maven settings use `mirrorOf: *`, exclude `papermc-repo` and `central-snapshots`; otherwise the platform APIs cannot be resolved.

## Project Structure

```text
src/main/java/org/endlessmc/minihub/
├── MiniHubCore.java
├── ConfigManager.java
├── LanguageManager.java
├── platform/
│   ├── MiniHubPlatform.java
│   └── MiniLogger.java
├── velocity/
│   ├── MiniHubVelocity.java
│   └── VelocityPlatform.java
└── bungee/
    ├── MiniHubBungee.java
    └── BungeePlatform.java
src/main/resources/
├── plugin.yml
├── velocity-plugin.json
└── lang/
```

`velocity-plugin.json` and `plugin.yml` coexist in the same jar. Velocity loads `MiniHubVelocity`, while BungeeCord and Waterfall load `MiniHubBungee`; shared logic lives in `MiniHubCore`.

## Development Information

- **Development Language**: Java 17
- **Build Tool**: Maven
- **Supported Platforms**: Velocity 3.x, BungeeCord, Waterfall
- **Minimum Java Version**: 17
- **Repository**: https://github.com/EndlessMCOrg/MiniHub/
- **Authors**: OutHimic, EndlessMCOrg
- **License**: MIT

## Changelog

### v1.2
- Added BungeeCord and Waterfall support; one jar now runs on Velocity, BungeeCord and Waterfall
- Introduced a platform-independent core with separate Velocity and BungeeCord entry points
- Shaded and relocated SnakeYAML to avoid dependency conflicts between platforms
- Updated README with platform compatibility, build instructions and mirror troubleshooting

### v1.1-SNAPSHOT
- Moved to the EndlessMC Organization (package, authors and repository updated)
- License changed to MIT
- Removed unused code and unused language keys
- Added `.gitignore`
- Trimmed language files to Simplified Chinese, Traditional Chinese (Hong Kong/Taiwan) and American English
- Removed duplicate translated README files; `README.md` is now bilingual (Simplified Chinese and American English)

### v1.0-SNAPSHOT
- Initial version release
- Basic command teleportation functionality
- Multi-language support
- Configuration file system

# MiniHub - 多平台代理插件

> **AI 生成声明**: 本项目由 AI 辅助生成，代码仅供正常使用和参考和学习使用，请不要将其再次投入AI训练！

MiniHub 是一个轻量的代理端命令插件，支持 Velocity、BungeeCord 与 Waterfall。它会把配置的命令注册到代理，并将玩家传送到对应的下游服务器。

## 功能特性

- 🚀 同一个 jar 可在 Velocity、BungeeCord 与 Waterfall 上使用
- ⚙️ 命令完全由 `config.yml` 驱动，服务器名称不写死在代码里
- 🌐 多语言支持：`zh_cn`、`zh_hk`、`zh_tw`、`en_us`
- 🧩 无需权限节点，所有玩家均可使用
- 🛡️ 非玩家执行或目标服务器离线时会给出明确提示

## 环境要求

| 平台 | 实测版本 |
| --- | --- |
| Velocity | 3.4.0 |
| BungeeCord | 26.1 |
| Waterfall | 1.21 |

- Java 17 或更高版本
- `config.yml` 中的服务器名称必须已存在于代理的服务器列表中：
  - Velocity：`velocity.toml` 的 `[servers]`
  - BungeeCord / Waterfall：`config.yml` 的 `servers`

Waterfall 与 BungeeCord API 兼容，因此两者复用同一份 BungeeCord 实现。

## 安装

1. 下载最新的 `MiniHub-<version>.jar`
2. 将同一个 jar 文件放入 Velocity、BungeeCord 或 Waterfall 的 `plugins` 目录
3. 重启代理服务器
4. 修改生成的 `config.yml` 后再次重启

插件会自动创建数据目录：

- Velocity：`plugins/minihub/`
- BungeeCord / Waterfall：`plugins/MiniHub/`

## 配置

```yaml
# MiniHub 插件配置文件

# 使用的语言
language: "zh_cn"

# 服务器命令配置
# 格式: 服务器名称 -> 命令列表
servers:
  lobby:
    commands: ["hub", "lobby"]
    # 使用 /hub 或 /lobby 均可传送到服务器 lobby
  survival:
    commands: ["survival", "srv"]
  creative:
    commands: ["creative", "crea"]
```

- `language`：可选 `zh_cn`、`zh_hk`、`zh_tw`、`en_us`
- `servers.<名称>.commands`：将该服务器名称对应的命令注册到代理
- 语言文件会在首次启动时复制到 `lang/` 目录，可自行修改

## 命令使用

玩家可以使用配置中注册的命令：

- `/hub` 或 `/lobby` - 传送到大厅服务器
- `/survival` 或 `/srv` - 传送到生存服务器
- `/creative` 或 `/crea` - 传送到创造服务器

命令仅对玩家生效。在代理控制台执行时会提示 `command.player_only` 对应的消息。

## 构建

```bash
mvn clean package
```

产物为 `target/MiniHub-<version>.jar`，同一个 jar 可用于三个平台。构建时可覆盖版本号：

```bash
mvn clean package -Drevision=1.2.0
```

构建依赖以下仓库：

- `papermc-repo`：提供 `velocity-api` 与 Brigadier
- `central-snapshots`：提供 `bungeecord-api` 快照

如果 Maven 配置了 `mirrorOf: *`，需要排除 `papermc-repo` 与 `central-snapshots`，否则无法解析平台 API。

## 项目结构

```text
src/main/java/org/endlessmc/minihub/
├── MiniHubCore.java
├── ConfigManager.java
├── LanguageManager.java
├── platform/
│   ├── MiniHubPlatform.java
│   └── MiniLogger.java
├── velocity/
│   ├── MiniHubVelocity.java
│   └── VelocityPlatform.java
└── bungee/
    ├── MiniHubBungee.java
    └── BungeePlatform.java
src/main/resources/
├── plugin.yml
├── velocity-plugin.json
└── lang/
```

`velocity-plugin.json` 与 `plugin.yml` 共存于同一个 jar：Velocity 加载 `MiniHubVelocity`，BungeeCord 与 Waterfall 加载 `MiniHubBungee`，共享逻辑位于 `MiniHubCore`。

## 开发信息

- **开发语言**: Java 17
- **构建工具**: Maven
- **支持平台**: Velocity 3.x、BungeeCord、Waterfall
- **最低 Java 版本**: 17
- **仓库地址**: https://github.com/EndlessMCOrg/MiniHub/
- **作者**: OutHimic、EndlessMCOrg
- **许可证**: MIT

## 更新日志

### v1.2
- 新增 BungeeCord 与 Waterfall 支持，同一个 jar 可运行在 Velocity、BungeeCord 与 Waterfall 上
- 抽取平台无关核心，Velocity 与 BungeeCord 使用各自入口
- 内置并重定位 SnakeYAML，避免不同平台的依赖冲突
- 完善 README，补充平台兼容性、构建说明与镜像排障

### v1.1-SNAPSHOT
- 项目迁移至 EndlessMC Organization（包名、作者与仓库地址更新）
- 许可证变更为 MIT
- 清理无效代码与未使用的语言键
- 补充 .gitignore
- 精简语言文件，仅保留简体中文、繁体中文（香港/台湾）与美式英语
- 删除重复的翻译 README，README.md 现为简体中文与美式英语双语

### v1.0-SNAPSHOT
- 初始版本发布
- 基础命令传送功能
- 多语言支持
- 配置文件系统

## 许可证

本项目使用 MIT 许可证，详见 [LICENSE](LICENSE)。