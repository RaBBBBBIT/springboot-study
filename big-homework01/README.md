# big-homework01

Spring Boot 第 03 周大作业独立项目，覆盖以下内容：

- `@ConfigurationProperties` 配置绑定与校验
- `dev/prod` 多环境配置
- Hutool `IdUtil / SecureUtil / FileUtil`
- 统一响应与全局异常处理
- DeepSeek 风格 AI 接口接入，配置支持环境变量注入

## 运行方式

```bash
cd /Users/rabbit/Documents/Github/springboot-study/big-homework01
./mvnw test
./mvnw package
java -jar target/big-homework01-0.0.1-SNAPSHOT.jar
```

## 常用命令

开发环境：

```bash
java -jar target/big-homework01-0.0.1-SNAPSHOT.jar
```

生产环境：

```bash
DB_PASSWORD=your-prod-password \
DEEPSEEK_API_KEY=your-deepseek-key \
java -jar target/big-homework01-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

命令行覆盖端口：

```bash
java -jar target/big-homework01-0.0.1-SNAPSHOT.jar --server.port=9090
```

## 接口

- `GET /api/config/app-info`
- `GET /api/config/db-info`
- `GET /api/hutool/id`
- `GET /api/hutool/md5?text=123456`
- `POST /api/hutool/upload`
- `POST /api/unified/ai/chat`

## 环境变量

- `DB_PASSWORD`
- `DEEPSEEK_API_KEY`
- `DEEPSEEK_MODEL`，默认 `deepseek-chat`

## 文档

- [配置优先级说明](./docs/config-priority.md)
- [测试报告](./docs/week03-homework-test-report.md)
