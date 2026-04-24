# week05

Spring Boot 3 + MySQL + MyBatis + OpenAPI 的最小示例工程。

## 本周要求对应

1. 已加入依赖：MySQL、MyBatis、OpenAPI
2. 已完成配置：`application.yml` 中包含数据源配置和 MyBatis 驼峰映射配置
3. 已提供 `User` 实体类，可直接作为 MyBatisX 生成实体类的参考结果
4. 已提供测试接口：`GET /api/test/user`
5. 启动后可访问 Swagger：`http://localhost:8080/swagger-ui.html`

## 初始化数据库

先创建数据库：

```sql
CREATE DATABASE IF NOT EXISTS springboot3_db
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;
```

应用启动时会自动执行 `schema.sql` 和 `data.sql`，创建 `tb_user` 表并写入一条测试数据。

## IDEA 中添加数据源

1. 打开 IDEA 的数据库工具窗口
2. 新建 `MySQL 8` 数据源
3. 配置主机 `localhost`、端口 `3306`、数据库 `springboot3_db`
4. 用户名填写 `root`，密码填写 `123456`
5. 测试连接成功后保存

## 使用 MyBatisX 生成实体类

1. 安装 MyBatisX 插件
2. 在 IDEA 数据库窗口连接到 `springboot3_db`
3. 选中 `tb_user` 表
4. 执行 `MyBatisX-Generator`
5. 生成 Entity 到 `cn.rabbbbbit.week05.entity`

当前仓库中的 `User` 已按课堂示例需要准备好，并统一使用 `LocalDateTime` 作为时间字段类型。
