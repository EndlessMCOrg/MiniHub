# MiniHub - Velocity Plugin

> **AI Generation Statement**: This project is AI-assisted generated, the code is for normal use, reference and learning purposes only, please do not use it for AI training again!

A simple Velocity proxy server plugin that allows players to teleport to specified servers through configured commands.

## Features

- 🚀 Teleport to specified servers via simple commands. Supports English commands!

## Installation

1. Download the latest MiniHub jar file
2. Place the jar file in Velocity's `plugins` directory
3. Restart the Velocity server
4. The plugin will automatically generate configuration files and language files

## Configuration

Configuration file located at `plugins/MiniHub/config.yml`:

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

Available language values: `zh_cn`, `zh_hk`, `zh_tw`, `en_us`.

## Command Usage

According to the configuration settings, players can use corresponding commands to teleport to target servers:

- `/hub` or `/lobby` - Teleport to lobby server
- `/survival` or `/srv` - Teleport to survival server  
- `/creative` or `/crea` - Teleport to creative server

## Development Information

- **Development Language**: Java 17
- **Build Tool**: Maven
- **Dependency Management**: Maven
- **Supported Platform**: Velocity Proxy
- **Repository**: https://github.com/EndlessMCOrg/MiniHub/
- **Authors**: OutHimic, EndlessMCOrg
- **License**: MIT

The version can be overridden at build time, for example: `mvn clean package -Drevision=1.2.0`.

## Changelog

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

# MiniHub - Velocity 插件

> **AI 生成声明**: 本项目由 AI 辅助生成，代码仅供正常使用和参考和学习使用，请不要将其再次投入AI训练！

一个简单的 Velocity 代理服务器插件，允许玩家通过配置的命令传送到指定的服务器。

## 功能特性

- 🚀 通过简单命令传送到指定服务器。可以用中文命令！

## 安装

1. 下载最新的 MiniHub jar 文件
2. 将 jar 文件放入 Velocity 的 `plugins` 目录
3. 重启 Velocity 服务器
4. 插件会自动生成配置文件和语言文件

## 配置

配置文件位于 `plugins/MiniHub/config.yml`：

```yaml
# MiniHub 插件配置文件

# 使用的语言
language: "zh_cn"

# 服务器命令配置
# 格式: 服务器名称 -> 命令列表
servers:
  lobby:
    commands: ["hub", "lobby"]
    #使用/hub或/lobby均可传送到服务器lobby
  survival:
    commands: ["survival", "srv"]
  creative:
    commands: ["creative", "crea"]
```

可选语言值：`zh_cn`、`zh_hk`、`zh_tw`、`en_us`。

## 命令使用

根据配置文件中的设置，玩家可以使用相应的命令传送到目标服务器：

- `/hub` 或 `/lobby` - 传送到大厅服务器
- `/survival` 或 `/srv` - 传送到生存服务器  
- `/creative` 或 `/crea` - 传送到创造服务器

## 开发信息

- **开发语言**: Java 17
- **构建工具**: Maven
- **依赖管理**: Maven
- **支持平台**: Velocity Proxy
- **仓库地址**: https://github.com/EndlessMCOrg/MiniHub/
- **作者**: OutHimic、EndlessMCOrg
- **许可证**: MIT

构建时可指定版本号，例如：`mvn clean package -Drevision=1.2.0`。

## 更新日志

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
