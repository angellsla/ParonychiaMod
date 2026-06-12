# ParonychiaMod - 甲沟炎模组

一个为 Minecraft 添加「甲沟炎」负面效果和特效药的趣味模组。

## 🎮 支持的 Minecraft 版本

| Minecraft 版本 | Fabric API 版本 | 模组版本 |
|---------------|----------------|---------|
| 1.20.2 | 0.90.0+1.20.2 | 1.0.3 |

## 📦 安装要求

- **Minecraft**: 1.20.2
- **Fabric Loader**: >= 0.14.21
- **Fabric API**: 0.90.0+1.20.2

## 🎯 模组功能

### 甲沟炎效果 (Paronychia)

玩家从高处摔落时有 30% 几率获得此效果：

- **持续时间**: 3 分钟
- **效果等级**: Ⅰ级（可叠加）

**效果影响**:
- 每 2 秒造成持续伤害（Ⅰ级 1 点，Ⅱ级 2 点，Ⅲ级及以上 3 点）
- 移动速度降低 10%
- 跳跃时造成额外伤害
- 撞墙时造成额外伤害

### 特效药 (Paronychia Cure)

用于治愈甲沟炎效果的物品：

- 使用方式：按住右键蓄力 1.6 秒
- 仅对患有甲沟炎的玩家有效
- 使用后移除甲沟炎效果

## 📁 文件结构

```
src/
└── main/
    ├── java/com/example/paronychia/
    │   ├── ParonychiaMod.java        # 主类
    │   ├── effect/
    │   │   └── ParonychiaEffect.java # 甲沟炎效果
    │   ├── event/
    │   │   └── ParonychiaEvent.java  # 事件处理
    │   ├── item/
    │   │   └── ParonychiaCure.java   # 特效药物品
    │   └── registry/
    │       ├── ModEffects.java       # 效果注册
    │       └── ModItems.java         # 物品注册
    └── resources/
        ├── assets/paronychia_mod/    # 资源文件
        └── fabric.mod.json          # 模组配置
```

## 🛠️ 开发构建

```bash
# 编译模组
./gradlew build

# 构建输出
build/libs/ParonychiaMod-<version>.jar
```

## 📄 许可证

MIT License - 详见 [LICENSE](file:///workspace/LICENSE)

## 🔗 下载

最新版本下载：[Releases](https://github.com/angellsla/ParonychiaMod/releases)