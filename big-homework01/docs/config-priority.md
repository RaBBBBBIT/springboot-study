# 配置加载优先级说明

本项目验证的优先级顺序为：

`命令行参数 > 环境变量 > 外部配置 > 环境配置文件 > 主配置文件`

## 1. 主配置文件

`application.yml` 中定义了公共配置，例如：

- `spring.profiles.active=dev`
- `server.port=8080`
- `app.name / app.version / app.description`
- `deepseek.base-url / deepseek.api-key / deepseek.model`

## 2. 环境配置文件

- `application-dev.yml` 覆盖 `app.database.host=localhost`
- `application-prod.yml` 覆盖 `app.database.host=prod.example.com`

当通过 `--spring.profiles.active=prod` 启动时，`/api/config/db-info` 返回的数据库地址会从 `localhost` 切换为 `prod.example.com`。

## 3. 环境变量

生产环境敏感信息不直接写入仓库，通过环境变量注入：

```bash
export DB_PASSWORD=your-prod-password
export DEEPSEEK_API_KEY=your-deepseek-key
export DEEPSEEK_MODEL=deepseek-chat
```

对应配置：

- `application-prod.yml` 中的 `app.database.password: ${DB_PASSWORD:}`
- `application.yml` 中的 `deepseek.api-key: ${DEEPSEEK_API_KEY:}`

## 4. 命令行参数

命令行参数优先级最高，可以直接覆盖主配置和环境配置：

```bash
java -jar target/big-homework01-0.0.1-SNAPSHOT.jar --server.port=9090
```

项目内还提供了自动化测试 `ConfigControllerPortOverrideTest`，断言当 `server.port=9090` 时接口中读取到的端口就是 `9090`。

## 5. 本次本机验证说明

在 `2026-03-24` 本机环境中，`9090` 端口已被 HBuilderX 的 `uni --platform h5` 开发服务占用，因此手工启动演示改用 `18090`，自动化测试继续保留 `9090` 断言，验证逻辑不变。
