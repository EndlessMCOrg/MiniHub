# MiniHub

[简体中文](#简体中文) | [English (US)](#english-us)

> **AI 生成声明**：本项目由 AI 辅助生成，代码仅供正常使用、参考和学习，**请不要将其再次用于 AI 训练**！
> **AI Generated Disclaimer**: This project was generated with AI assistance. The code is provided for normal use, reference, and learning only. **Please do not use it for AI training again!**

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/version-1.1--SNAPSHOT-informational.svg)](https://github.com/EndlessMCOrg/MiniHub/releases)

---

## 简体中文

### 简介

MiniHub 是一个简单的 Velocity 代理服务器插件，允许玩家通过配置的命令传送到指定的服务器。

### 功能特性

- 通过简单命令传送到指定服务器。
- 命令别名可在配置文件中自定义。
- 支持多语言。

### 环境要求

- 服务端：Velocity Proxy。
- Java：17 或更高版本。
- 构建工具：Maven（仅构建时需要）。

### 安装

1. 下载最新的 MiniHub jar 文件。
2. 将 jar 文件放入 Velocity 的 `plugins` 目录。
3. 重启 Velocity 服务器。
4. 插件会自动生成配置文件和语言文件。

### 配置

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
    # 使用 /hub 或 /lobby 均可传送到服务器 lobby
  survival:
    commands: ["survival", "srv"]
  creative:
    commands: ["creative", "crea"]
```

可选语言值：`zh_cn`、`zh_hk`、`zh_tw`、`en_us`。

### 命令

根据配置文件中的设置，玩家可以使用相应的命令传送到目标服务器：

- `/hub` 或 `/lobby`：传送到大厅服务器。
- `/survival` 或 `/srv`：传送到生存服务器。
- `/creative` 或 `/crea`：传送到创造服务器。

### 构建

```bash
mvn clean package
```

默认版本为 `1.1-SNAPSHOT`，可在构建时覆盖：

```bash
mvn clean package -Drevision=1.2.0
```

构建产物位于 `target/MiniHub-<版本>.jar`。

### 变更日志

- `1.1-SNAPSHOT`：项目迁移至 EndlessMC Organization（包名、作者与仓库地址更新）；许可证变更为 MIT；清理无效代码与未使用的语言键；补充 `.gitignore`；精简语言文件，仅保留简体中文、繁体中文（香港/台湾）与美式英语；删除重复的翻译 README，`README.md` 现为简体中文与美式英语双语。
- `1.0-SNAPSHOT`：初始版本发布，包含基础命令传送功能、多语言支持与配置文件系统。

### 许可证

本项目使用 MIT 许可证，详见 [LICENSE](LICENSE)。

Copyright (c) 2024-2026 EndlessMC Organization

---

## English (US)

### Overview

MiniHub is a simple Velocity proxy plugin that lets players teleport to specified servers through configured commands.

### Features

- Teleport to specified servers with simple commands.
- Command aliases are configurable.
- Multi-language support.

### Requirements

- Server: Velocity Proxy.
- Java: 17 or newer.
- Build tool: Maven (build only).

### Installation

1. Download the latest MiniHub jar file.
2. Place the jar file in Velocity's `plugins` directory.
3. Restart the Velocity server.
4. The plugin will automatically generate configuration files and language files.

### Configuration

The configuration file is located at `plugins/MiniHub/config.yml`:

```yaml
# MiniHub plugin configuration file

# Language to use
language: "en_us"

# Server command configuration
# Format: server name -> command list
servers:
  lobby:
    commands: ["hub", "lobby"]
    # Use /hub or /lobby to teleport to the lobby server
  survival:
    commands: ["survival", "srv"]
  creative:
    commands: ["creative", "crea"]
```

Available language values: `zh_cn`, `zh_hk`, `zh_tw`, `en_us`.

### Commands

According to the configuration, players can use the corresponding commands to teleport to target servers:

- `/hub` or `/lobby`: teleport to the lobby server.
- `/survival` or `/srv`: teleport to the survival server.
- `/creative` or `/crea`: teleport to the creative server.

### Building

```bash
mvn clean package
```

The default version is `1.1-SNAPSHOT`. You can override it at build time:

```bash
mvn clean package -Drevision=1.2.0
```

The output is `target/MiniHub-<version>.jar`.

### Changelog

- `1.1-SNAPSHOT`: moved to the EndlessMC Organization (package, authors, and repository updated); changed the license to MIT; removed unused code and unused language keys; added `.gitignore`; trimmed language files to Simplified Chinese, Traditional Chinese (Hong Kong/Taiwan), and English (US); removed duplicate translated READMEs, and `README.md` is now bilingual (Simplified Chinese and English (US)).
- `1.0-SNAPSHOT`: initial release with basic command teleportation, multi-language support, and a configuration file system.

### License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

Copyright (c) 2024-2026 EndlessMC Organization
