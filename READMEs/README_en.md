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

## Changelog

### v1.1-SNAPSHOT
- Moved to the EndlessMC Organization (package, authors and repository updated)
- License changed to MIT
- Removed unused code and unused language keys
- Added `.gitignore`

### v1.0-SNAPSHOT
- Initial version release
- Basic command teleportation functionality
- Multi-language support
- Configuration file system

## License

This project is licensed under the MIT License. See [LICENSE](../LICENSE) for details.