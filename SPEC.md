# Spring Boot Study SPEC

基于仓库 `week01` 到 `week04` 当前代码整理，日期为 2026-03-27。

本文档不是未来规划稿，而是“按现有实现反推”的规格说明。凡是代码里已经落地的能力，会按已实现记录；凡是只搭了骨架、未真正完成的内容，会明确标注为“当前未完成”。

## 1. 仓库概览

| 周次 | 目录 | 主题 | 当前状态 |
| --- | --- | --- | --- |
| Week01 | `week01/hello-sample` | Spring Boot 入门与基础接口 | 已完成基础 API 与测试 |
| Week02 | `week02/springboot-week02` | 学生信息内存 CRUD | 已完成主要功能与测试 |
| Week03 | `week03` | 配置读取、Profile、多环境配置 | 部分完成，存在未收尾内容 |
| Week04 | `week04` | 新周项目骨架 | 仅完成脚手架初始化 |

## 2. 技术基线

- 语言版本：Java 17
- 构建方式：Maven Wrapper
- Web 框架：Spring Boot
- 测试框架：Spring Boot Test、JUnit 5、MockMvc
- 数据存储：
  - `week01` 无持久化，仅返回文本或运行时信息
  - `week02` 使用内存 `ConcurrentHashMap` 保存学生数据
  - `week03`、`week04` 无数据库接入

各周依赖版本现状：

- `week01`：Spring Boot `3.5.11`
- `week02`：Spring Boot `3.5.11`
- `week03`：Spring Boot `3.5.12`
- `week04`：Spring Boot `4.0.5`

### 2.1 代码风格规范

本节定义仓库后续代码的默认风格，优先级高于个人习惯。

#### 通用约定

- 统一使用 UTF-8 编码、LF 换行。
- Java 文件统一使用 4 个空格缩进，不使用 Tab。
- 一个文件只放一个主要公共类。
- 导包按 IDE 默认规则整理，不保留未使用 import。
- 代码标识符使用英文，不使用拼音命名。
- 注释、文档、提交说明优先使用中文；对外接口字段名仍保持英文。

#### 命名规范

- 包名：全小写，如 `cn.rabbbbbit.week02.service`
- 类名、枚举名：`UpperCamelCase`
- 方法名、字段名：`lowerCamelCase`
- 常量名：`UPPER_SNAKE_CASE`
- DTO 命名：`XxxCreateDTO`、`XxxUpdateDTO`、`XxxQueryDTO`
- VO 命名：`XxxVO`
- Controller 命名：`XxxController`
- Service 命名：`XxxService`
- 配置类命名：`XxxProperties` 或 `XxxConfig`

#### 分层规范

- `controller`：只负责路由、参数接收、状态码和响应封装，不写核心业务逻辑。
- `service`：负责业务编排、数据处理、规则判断。
- `entity`：表示内部领域对象或持久化对象。
- `dto`：只表示请求入参，不直接作为出参返回。
- `vo`：只表示响应出参，不直接复用实体对象。
- `config`：放配置绑定、Bean 配置、框架接入。
- `common`：放通用响应体、工具类、基础常量或基类。

#### Spring Boot 风格

- Controller 统一使用 REST 风格路径，资源名优先使用复数，如 `/api/students`。
- 新增、更新、删除接口必须显式返回正确 HTTP 状态码。
- 同一模块内只允许一种统一响应结构，不混用多套响应体。
- 新代码优先使用构造器注入，不使用字段注入。
- 配置绑定优先使用 `@ConfigurationProperties`，避免在同一配置对象中大量散落 `@Value`。
- YAML 配置项统一使用 `kebab-case`，Java 字段统一使用 `camelCase`。

#### 方法与实现风格

- 方法职责单一，避免一个方法同时处理路由、校验、业务、组装响应。
- 优先使用早返回减少嵌套层级，嵌套超过 3 层时应主动重构。
- 不写“看起来通用、实际上没人复用”的过度抽象。
- 非业务常量不要在方法体内反复硬编码。
- 允许使用 Lombok 简化 DTO、VO、Entity，但核心流程代码应保持可读性。

#### 注释风格

- 注释只解释“为什么”，不重复“代码做了什么”。
- Controller 和 Service 可保留简短类注释，避免堆砌模板化作者信息。
- 对外接口、关键配置和容易误用的逻辑应写注释。
- 明显自解释的 getter/setter、简单赋值语句不写注释。

#### 测试风格

- Service 层测试命名：`XxxServiceTest`
- Controller 层 MockMvc 测试命名：`XxxControllerTest`
- Spring Boot 启动烟雾测试命名：`XxxApplicationTests`
- 测试方法名要体现行为，例如 `createStudentShouldReturn201`。
- 每个测试只验证一个清晰行为；初始化数据通过 `setUp()` 或专用工厂方法统一处理。
- 新增接口或修复 bug 时，优先补对应测试，再改实现。

### 2.2 Git 提交风格

#### Commit Message 格式

统一采用：

```text
<type>(<scope>): <subject>
```

说明：

- `type`：提交类型
- `scope`：影响范围，优先写周次或模块
- `subject`：一句话说明本次变更，简洁、可落地、不要写成流水账

推荐类型：

- `feat`：新增功能
- `fix`：修复问题
- `refactor`：重构，不改变外部行为
- `test`：新增或调整测试
- `docs`：文档变更
- `style`：纯格式调整，不改逻辑
- `chore`：构建、脚本、依赖、工程配置

推荐 scope：

- `week01`
- `week02`
- `week03`
- `week04`
- `root`
- `docs`
- `build`

提交要求：

- 一次提交只做一类事情，避免“功能 + 重构 + 格式化”混在同一个 commit。
- `subject` 优先使用中文动宾短句，不加句号。
- 不允许提交 `update`、`fix bug`、`misc`、`wip` 这类信息量过低的标题。
- 大范围格式化必须单独提交，避免淹没有效代码变更。
- 提交前至少保证当前修改能通过最基本的编译或测试验证。

推荐示例：

```text
feat(week02): 完成学生信息新增与查询接口
fix(week03): 修复 profile 配置读取不一致问题
test(week02): 补充 StudentController 的删除接口测试
docs(root): 新增仓库 SPEC 说明文档
style(week01): 统一控制器代码格式
```

#### 分支风格

如需新建分支，命名建议采用：

```text
feature/week02-student-crud
fix/week03-profile-binding
docs/root-spec
```

分支命名要求：

- 小写英文
- 使用中划线分词
- 名称直接表达目的，不使用临时缩写
- 一个分支只承载一个明确主题

## 3. Week01 规格

### 3.1 目标

`week01` 是一个 Spring Boot 入门项目，目标是完成最基础的 REST 接口开发，并补充自动化测试。

### 3.2 应用信息

- 主类：`cn.rabbbbbit.backendstudy.BackendStudyApplication`
- 配置文件：`week01/hello-sample/src/main/resources/application.yml`
- 运行端口：`12348`

### 3.3 已实现接口

#### `GET /api/hello`

- 返回类型：纯文本
- 返回内容：`Hello, Spring Boot!`
- 用途：验证最基础的控制器与路由是否工作正常

#### `GET /api/health`

- 返回类型：JSON
- 返回字段：
  - `status`
  - `timestamp`
  - `uptimeMs`
- 当前行为：
  - `status` 固定返回 `UP`
  - `timestamp` 使用 `Instant.now()`
  - `uptimeMs` 来自 JVM 运行时信息

#### `GET /api/system-info`

- 返回类型：JSON
- 返回字段：
  - `osName`
  - `osVersion`
  - `osArch`
  - `javaVersion`
  - `javaVendor`
  - `userTimezone`
  - `availableProcessors`
  - `freeMemoryBytes`
  - `totalMemoryBytes`
  - `maxMemoryBytes`
  - `hostName`
  - `hostAddress`
- 当前行为：
  - 优先读取本机系统与 JVM 属性
  - 主机名和 IP 获取失败时，回退为 `unknown`

### 3.4 测试范围

已存在 `MockMvc` 测试，覆盖以下行为：

- `/api/hello` 返回 `200` 且正文正确
- `/api/health` 返回 `200`，并包含 `status`、`timestamp`、`uptimeMs`
- `/api/system-info` 返回 `200`，并包含关键字段
- 基础 `contextLoads` 启动测试

### 3.5 当前边界

- 无 service 层和 repository 层拆分
- 无统一响应体封装
- 无数据库、鉴权、异常处理、参数校验
- `StudyEntity` 已存在，但未参与接口逻辑

## 4. Week02 规格

### 4.1 目标

`week02` 在 `week01` 基础上升级为一个简化的学生信息管理系统，重点是：

- 设计实体、DTO、VO
- 提供统一响应结构
- 完成学生数据的增删改查
- 使用测试保证行为稳定

### 4.2 应用信息

- 主类：`cn.rabbbbbit.week02.SpringbootWeek02Application`
- 配置文件：`week02/springboot-week02/src/main/resources/application.yml`
- 运行端口：`12349`

### 4.3 领域模型

核心实体为 `Student`，字段如下：

- `id`
- `name`
- `avatar`
- `mobile`
- `gender`
- `enabled`
- `birthday`
- `createTime`

辅助类型：

- `GenderEnum`
  - `MALE`
  - `FEMALE`
  - `UNKNOWN`
- `StudentAddDTO`
  - 用于新增学生
- `StudentUpdateDTO`
  - 用于修改学生基础信息
- `StudentVO`
  - 对外返回的视图对象，仅暴露 `id`、`name`、`mobile`、`gender`、`createTime`
- `ApiResponse<T>`
  - 统一响应包装，字段为 `success`、`message`、`data`、`timestamp`

### 4.4 数据存储与初始化

当前未接入数据库，使用进程内静态内存存储：

- 容器类型：`ConcurrentHashMap<Long, Student>`
- 主键生成：`AtomicLong`
- 初始学生数据：
  - `1001`，姓名 `张三`
  - `1002`，姓名 `张三三`
- 新增学生时，下一个 ID 从 `1003` 开始

测试和开发时可通过 `StudentService.reset()` 重置为初始数据。

### 4.5 已实现接口

#### `GET /api/students`

- 功能：查询所有学生
- 返回：`ApiResponse<List<StudentVO>>`
- 当前行为：
  - 返回所有学生
  - 按 `id` 升序排序

#### `GET /api/students/search?name={keyword}`

- 功能：按姓名模糊查询
- 返回：`ApiResponse<List<StudentVO>>`
- 当前行为：
  - 使用 `String.contains` 做包含匹配
  - 返回结果按 `id` 升序排序

#### `GET /api/students/{id}`

- 功能：按 ID 查询学生
- 成功返回：`200`
- 失败返回：`404`
- 失败消息：`Student not found.`

#### `POST /api/students`

- 功能：新增学生
- 请求体：`StudentAddDTO`
- 成功返回：`201`
- 成功消息：`Student created.`
- 当前行为：
  - `enabled` 固定写为 `true`
  - `createTime` 写入当前时间

#### `PUT /api/students/{id}`

- 功能：更新学生基础信息
- 请求体：`StudentUpdateDTO`
- 成功返回：`200`
- 失败返回：`404`
- 成功消息：`Student updated.`
- 当前行为：
  - 仅更新 `name`、`mobile`、`avatar`
  - 不更新 `gender`、`birthday`、`enabled`、`createTime`

#### `DELETE /api/students/{id}`

- 功能：删除学生
- 成功返回：`200`
- 失败返回：`404`
- 成功消息：`Student deleted.`

### 4.6 测试范围

`week02` 的测试相对完整，包含两层：

- `StudentServiceTest`
  - 查询全部学生
  - 新增学生
  - 查询单个学生
  - 姓名模糊查询
  - 更新学生
  - 删除学生
  - 重置数据
- `StudentControllerTests`
  - 列表接口
  - 单个查询接口
  - 新增接口
  - 更新接口
  - 搜索接口
  - 删除接口

### 4.7 当前边界

- 数据只保存在内存中，应用重启后丢失
- 已引入 `spring-boot-starter-validation`，但 DTO 上暂未添加参数校验注解
- 无全局异常处理与统一错误码体系
- 无分页、排序参数、批量操作、持久化能力

## 5. Week03 规格

### 5.1 本周主题

从现有代码判断，`week03` 的主题是“配置管理与多环境配置”，重点包括：

- 通过 `application.yaml` 和多套 profile 文件管理配置
- 读取项目基础信息
- 尝试引入统一返回结构 `Result<T>`

### 5.2 应用信息

- 主类：`cn.rabbbbbit.week03.Week03Application`
- 默认激活环境：`prod`
- 配置文件：
  - `application.yaml`
  - `application-dev.yaml`
  - `application-test.yaml`
  - `application-prod.yaml`

### 5.3 配置结构

各 profile 文件中定义了以下信息：

- `spring.application.name`
- `server.port`
- `app.name-name`
- `app.version`
- `app.description`
- `app.author.name`
- `app.author.website`
- `app.author.email`
- `app.features`
- `app.published`

当前三个 profile 文件内容基本一致，区别主要体现在应用名称文本上：

- 开发环境：`week03 开发环境`
- 测试环境：`week03 测试环境`
- 生产环境：`week03 生产环境`

### 5.4 已实现代码结构

#### `Result<T>`

提供统一响应封装，包含：

- `code`
- `message`
- `data`

并提供以下工厂方法：

- `success(data)`
- `success(message, data)`
- `error(message)`
- `error(code, message)`

#### `ProjectInfoController`

已实现接口：

- `GET /project/info`

返回字段：

- `applicationName`
- `version`
- `description`

取值来源：

- `@Value("${app.name-name:}")`
- `@Value("${app.version:}")`
- `@Value("${app.description:}")`

#### `AppConfig`

已定义为 `@ConfigurationProperties(prefix = "app")` 的配置类，当前包含字段：

- `appName`
- `appVersion`
- `appDescription`
- `author`
- `features`

### 5.5 当前未完成或不一致部分

以下内容是根据当前代码直接观察到的实现状态：

- `Week03Application.main` 中，`SpringApplication.run(...)` 被注释掉了。
  - 当前主方法执行的是一段 `switch` 示例代码。
  - 这意味着如果直接运行主类，应用不会按正常 Spring Boot Web 项目启动。
- `BatchConfigController` 只有类定义，没有已生效的接口方法。
- `AppConfig` 字段名与 YAML 中的键名并不完全对齐。
  - 代码字段为 `appName`、`appVersion`、`appDescription`
  - 配置键为 `app.name-name`、`app.version`、`app.description`
  - 从意图上看，这是在尝试做配置绑定，但当前命名并不统一
- `AppConfigTest` 只有一个打印配置对象的测试有实际内容，其余测试方法为空

### 5.6 当前可视为已落地的能力

如果仅按现有代码定义“已落地规格”，那么 `week03` 当前已落地的内容主要是：

- 多 profile 配置文件结构
- 一个项目基础信息读取接口
- 一个统一响应对象定义

尚不能视为完整交付的内容包括：

- 稳定可运行的应用入口
- 完整的批量配置接口
- 完整且可验证的 `ConfigurationProperties` 绑定测试

## 6. Week04 规格

### 6.1 当前状态

`week04` 目前仍处于脚手架初始化阶段。

### 6.2 应用信息

- 主类：`cn.rabbbbbit.week04.Week04Application`
- 配置文件：`week04/src/main/resources/application.yaml`
- 当前配置：
  - `spring.application.name=week04`

### 6.3 依赖现状

`week04` 当前依赖包括：

- `spring-boot-starter-webmvc`
- `spring-boot-devtools`
- `lombok`
- `spring-boot-starter-webmvc-test`

### 6.4 已实现内容

- Spring Boot 启动类
- 基础 `contextLoads` 启动测试

### 6.5 当前未实现内容

- 无 controller
- 无 service
- 无实体、DTO、VO
- 无接口文档
- 无业务测试
- 无数据库或内存业务模型

因此，`week04` 当前只能定义为“已完成项目初始化，但尚未进入业务实现阶段”。

## 7. 演进结论

从 `week01` 到 `week04`，仓库体现出比较清晰的学习路线：

1. `week01`：先完成最基础的 HTTP 接口和运行时信息输出
2. `week02`：引入分层、DTO/VO、统一响应体和 CRUD 测试
3. `week03`：开始尝试多环境配置与配置读取，但实现尚未完全收口
4. `week04`：新项目骨架已建立，等待明确业务主题

## 8. 建议的后续对齐方向

如果后续希望让仓库更像一个连续演进的学习项目，建议优先对齐下面几件事：

- 统一各周的启动方式与 Spring Boot 版本策略
- 修复 `week03` 主类无法正常启动的问题
- 明确 `week03` 中 `AppConfig` 与 YAML 键名的绑定策略
- 给 `week04` 定义明确主题，再补 controller、service 和测试
- 将 `week02` 的统一响应、分层方式延续到后续周次
