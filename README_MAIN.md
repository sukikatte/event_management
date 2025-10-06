# Event Management System

一个基于 Spring Boot 的全栈活动票务管理平台，支持多角色权限管理和完整的活动生命周期管理。

A full-stack event ticketing management platform built with Spring Boot, featuring multi-role access control and complete event lifecycle management.

---

## 🌟 特性 / Features

- 🔐 **多角色权限管理**: 管理员、组织者、普通用户三种角色
- 🎫 **票务系统**: 支持站票、座票、VIP票三种票型
- 🏢 **场馆管理**: 完整的场馆 CRUD 操作
- 📅 **活动管理**: 活动创建、发布、修改、删除
- 🔒 **安全认证**: Spring Security + Session-based 认证
- 🐳 **容器化部署**: Docker + Docker Compose
- 📊 **完整文档**: 70+ UML 图表 + 完整的 SDLC 文档

---

## 🛠️ 技术栈 / Tech Stack

### 后端 / Backend
- Spring Boot 3.3.6
- Spring Security (认证和授权)
- Spring Data JPA (数据持久化)
- Hibernate (ORM)
- MySQL 9.4 (数据库)

### 前端 / Frontend
- Thymeleaf (模板引擎)
- HTML5/CSS3
- JavaScript

### 开发工具 / Development Tools
- Maven (构建工具)
- Lombok (减少样板代码)
- Docker & Docker Compose (容器化)

---

## 🚀 快速启动 / Quick Start

### 方式 1：IntelliJ IDEA（推荐）

```bash
# 1. 启动 MySQL
docker-compose up -d mysql

# 2. 在 IntelliJ 中运行主类
# EventManagamentSystemApplication.java
```

### 方式 2：Docker Compose

```bash
# 1. 构建 JAR 文件（需要 Java 17）
export JAVA_HOME=$(/usr/libexec/java_home -v 17)  # macOS
./mvnw clean package -DskipTests

# 2. 启动所有服务
docker-compose up --build
```

### 访问应用
浏览器打开：**http://localhost:8080**

详细说明请查看 [SETUP.md](SETUP.md)

---

## 📁 项目结构 / Project Structure

```
├── 01-requirements/      # 需求分析文档（19个用例）
├── 02-analysis/          # 系统分析（类图、用例分析）
├── 03-design/            # 详细设计（序列图、设计类图）
├── src/
│   └── main/
│       ├── java/
│       │   └── ucd/comp3013j/ems/
│       │       ├── model/           # 实体类、DTO、Repository
│       │       ├── websecurity/     # 安全配置
│       │       └── ...
│       └── resources/
│           ├── templates/           # HTML 模板（34个页面）
│           ├── application.yaml     # 开发配置
│           └── application-prod.yaml# 生产配置
├── Dockerfile                       # Docker 镜像配置
├── compose.yaml                     # Docker Compose 配置
└── pom.xml                         # Maven 依赖
```

---

## 👥 用户角色 / User Roles

### Administrator（管理员）
- 管理所有用户账户
- 查看所有场馆和活动
- 删除场馆和活动

### Organiser（组织者）
- 创建和管理自己的活动
- 创建和管理场馆
- 设置票价和票数

### User（普通用户）
- 注册和登录
- 浏览活动
- 购买票务
- 查看购票历史

---

## 📊 UML 文档 / UML Documentation

项目包含完整的 UML 文档：
- 用例图（Use Case Diagram）
- 领域模型图（Domain Model）
- 类图（Class Diagram）
- 序列图（Sequence Diagram）- 38张

共计 **70+ UML 图表**，展示从需求分析到系统实现的完整追溯性。

---

## 🔧 开发说明 / Development Notes

### 重要提示
1. **必须使用 Java 17**（Lombok 与 Java 21+ 不兼容）
2. **MySQL 密码统一为 `verysecret`**
3. **首次启动等待 MySQL 初始化**（约10-15秒）

### 常见问题
请查看 [SETUP.md](SETUP.md) 的故障排除部分

---

## 📄 License

本项目为 UCD COMP3013J 课程作业

---

## 👨‍💻 Contributors

8人团队项目 / 8-Person Team Project

Advisor: Asst. Prof. Seán Russell

---

## 📞 Support

如有问题，请查看：
- [快速启动指南](SETUP.md)
- [IntelliJ 运行说明](RUN_INSTRUCTIONS.md)
- [Docker Compose 说明](DOCKER_COMPOSE_INSTRUCTIONS.md)

