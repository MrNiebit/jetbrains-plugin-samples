# JetBrains 插件示例项目

这是一个用于演示如何开发 JetBrains 插件的示例项目。项目采用Gradle父子模块结构，支持扩展多个插件。每个子模块代表一个独立的插件，便于管理和维护。

## 测试运行
```bash
# 运行IDE
./gradlew runIde

# 离线运行IDE
./gradlew runIde --offline
```

## 打包
```bash
./gradlew buildPlugin
```
1.	编译你的插件代码；
2.	生成 plugin.xml 和相关元信息；
3.	打包成一个 .zip 插件文件，存放在以下路径：`build/distributions/<your-plugin-name>.zip`

📌 打包前确认 checklist：
•	plugin.xml 已配置 id、version、sinceBuild、untilBuild 等；
•	Gradle intellij {} 配置已完成；
•	项目能正常运行 ./gradlew runIde；
•	如果你使用 Kotlin，需要配置 kotlin-stdlib 依赖；
•	有多个子模块时，确认是在哪个模块运行 buildPlugin（一般是插件模块，而不是根项目）。