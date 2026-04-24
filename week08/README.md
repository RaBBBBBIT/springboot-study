# Week08 Redis 作业

## 运行前准备

1. 启动本机 Redis，默认连接 `localhost:6379`，数据库编号为 `0`。
2. 打开 Redis 桌面管理工具，连接同一个 Redis 实例和数据库。

Redis 连接和 lettuce 连接池配置在 `src/main/resources/application.yml`。
`src/main/java/cn/rabbbbbit/week08/config/RedisConfig.java` 中额外配置了 `RedisTemplate<String, Object>`，value 使用 FastJson2 序列化。
`src/main/java/cn/rabbbbbit/week08/entity/User.java` 是普通实体类，不需要实现 `Serializable`。

## 录屏运行命令

```bash
cd week08
mvn spring-boot:run
```

程序启动后会使用 `StringRedisTemplate` 写入 3 个分组 key：

- `week08:user:1001:profile`
- `week08:user:1001:visit-count`
- `week08:session:demo-token`

默认过期时间是 30 秒，程序会等待 35 秒后再次读取并输出 `null`。录屏时需要展示：

1. Redis 桌面管理工具中 key 出现。
2. 控制台打印读取到的字符串值和数值。
3. Redis 桌面管理工具中 key 过期消失。
4. 控制台打印过期后读取结果为 `null`。

## 测试命令

```bash
cd week08
mvn test
```

测试覆盖字符串存取、数值自增、分组 key 和过期时间。
其中 `RedisTemplateObjectTest` 用 `@Resource` 注入 `RedisTemplate<String, Object>` 测试对象存取。
取出对象时先接收为 `Object`，再用 `JSON.parseObject(JSON.toJSONString(userObj), User.class)` 反序列化，不直接强转。
