# Event Management System - 快速启动指南 / Quick Start Guide

## 📋 前置要求 / Prerequisites

- **Java 17** (必须使用 Java 17，不支持 Java 21+)
- **Docker Desktop** (用于运行 MySQL 数据库)
- **Maven** (已包含 Maven Wrapper)
- **IDE**: IntelliJ IDEA 或其他 Java IDE（可选）

---

## 🚀 方式 1：使用 IntelliJ IDEA 运行（推荐）

### 步骤 1：启动 MySQL 数据库
```bash
docker-compose up -d mysql
```

### 步骤 2：配置 Java 版本
在 IntelliJ IDEA 中：
1. File → Project Structure → Project SDK
2. 选择 **Java 17**

### 步骤 3：运行应用
1. 打开 `src/main/java/ucd/comp3013j/ems/EventManagamentSystemApplication.java`
2. 点击主类旁边的绿色运行按钮 ▶️
3. 或右键 → Run 'EventManagamentSystemApplication'

### 步骤 4：访问应用
打开浏览器访问：http://localhost:8080

---

## 🐳 方式 2：使用 Docker Compose 运行

### 步骤 1：构建 JAR 文件
```bash
# macOS/Linux
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
./mvnw clean package -DskipTests

# Windows
set JAVA_HOME=C:\path\to\java17
mvnw.cmd clean package -DskipTests
```

### 步骤 2：启动所有服务
```bash
docker-compose up --build
```

或后台运行：
```bash
docker-compose up -d --build
```

### 步骤 3：访问应用
打开浏览器访问：http://localhost:8080

### 查看日志
```bash
docker-compose logs -f
```

### 停止服务
```bash
docker-compose down
```

---

## 📝 数据库配置

### 开发环境（本地运行）
- **数据库**：localhost:3306
- **数据库名**：eventdb
- **用户名**：root
- **密码**：verysecret

### 生产环境（Docker Compose）
- **数据库主机**：mysql（Docker 网络内部）
- **数据库名**：eventdb
- **用户名**：root
- **密码**：verysecret

配置文件：
- 开发环境：`src/main/resources/application.yaml`
- 生产环境：`src/main/resources/application-prod.yaml`

---

## 🔧 技术栈 / Tech Stack

- **后端框架**: Spring Boot 3.3.6
- **安全框架**: Spring Security
- **数据持久化**: Spring Data JPA + Hibernate
- **数据库**: MySQL 9.4
- **模板引擎**: Thymeleaf
- **构建工具**: Maven
- **容器化**: Docker & Docker Compose
- **开发工具**: Lombok

---

## 📁 项目结构 / Project Structure

```
EventManagementSystem/
├── 01-requirements/          # 需求文档和用例
├── 02-analysis/             # 系统分析和类图
├── 03-design/               # 详细设计和序列图
├── 04-implementation/       # 实现代码（备份）
├── src/main/java/           # Java 源代码
│   └── ucd/comp3013j/ems/
│       ├── model/           # 实体类、DTO、Repository、Service
│       │   ├── controllers/ # 控制器
│       │   ├── dto/         # 数据传输对象
│       │   ├── repos/       # JPA Repository
│       │   └── services/    # 业务逻辑层
│       └── websecurity/     # Spring Security 配置
├── src/main/resources/
│   ├── templates/           # Thymeleaf HTML 模板（34个）
│   ├── application.yaml     # 开发环境配置
│   └── application-prod.yaml# 生产环境配置
├── Dockerfile               # Docker 镜像配置
├── compose.yaml             # Docker Compose 配置
└── pom.xml                  # Maven 依赖配置
```

---

## 🎯 核心功能 / Core Features

### 三种角色
- **管理员 (Administrator)**: 管理所有账户、场馆、活动
- **组织者 (Organiser)**: 创建和管理活动、场馆
- **用户 (User)**: 购买票务、查看活动

### 主要功能
- ✅ 用户注册/登录
- ✅ 账户管理（CRUD）
- ✅ 场馆管理（CRUD）
- ✅ 活动管理（CRUD）
- ✅ 票务购买（三种票型：站票、座票、VIP票）
- ✅ 基于角色的访问控制（RBAC）

---

## ⚠️ 常见问题 / Troubleshooting

### 问题 1：编译失败
**错误**: 找不到 getter/setter 方法

**解决**: 确保使用 Java 17（不要使用 Java 21+）
```bash
java -version  # 检查版本
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

### 问题 2：无法连接数据库
**错误**: Communications link failure

**解决**: 
1. 确保 MySQL 容器运行：`docker-compose ps`
2. 检查配置文件密码是否为 `verysecret`
3. 等待 MySQL 完全启动（约10-15秒）

### 问题 3：8080 端口被占用
**错误**: Address already in use

**解决**: 
```bash
lsof -i :8080        # 查找占用进程
kill <PID>           # 终止进程
```

### 问题 4：Docker 构建失败
**错误**: JAR file not found

**解决**: 先构建 JAR 文件
```bash
./mvnw clean package -DskipTests
```

---

## 📦 上传到 GitHub 前的准备

### 需要添加的文件

创建 `.gitignore`:
```
target/
.idea/
*.iml
.DS_Store
*.log
```

### 建议添加的文件
- ✅ `SETUP.md` - 快速启动指南（本文件）
- ✅ `.dockerignore` - Docker 忽略文件
- ✅ `RUN_INSTRUCTIONS.md` - IntelliJ 运行说明
- ✅ `DOCKER_COMPOSE_INSTRUCTIONS.md` - Docker Compose 说明

---

## 🔍 代码修改说明

为了让项目可以正常运行，已修复以下问题：

### 1. `pom.xml` 修复
- ✅ 修复 MySQL 依赖的 scope 配置（从 `<scope>8.3.0</scope>` 改为正确的 `<version>8.3.0</version>` + `<scope>runtime</scope>`）
- ✅ 添加 Lombok 版本配置（1.18.34）
- ✅ 添加 Maven Compiler Plugin 配置支持 Lombok 注解处理

### 2. Lombok 注解统一
- ✅ `Event.java`: 统一使用 `@Data`
- ✅ `Venue.java`: 统一使用 `@Data`
- ✅ `Organiser.java`: 移除 `@NoArgsConstructor`（与手动构造函数冲突）
- ✅ `ModifyVenueDTO.java`: 改用 `@Data`
- ✅ `ModifyEventDTO.java`: 改用 `@Data`

### 3. 构造函数修复
- ✅ `Administrator.java`: 修复无参构造函数
- ✅ `User.java`: 修复无参构造函数
- ✅ `Organiser.java`: 添加无参构造函数

### 4. Docker 配置优化
- ✅ `Dockerfile`: Java 版本从 21 改为 17
- ✅ `compose.yaml`: MySQL 端口从 `expose` 改为 `ports` 映射
- ✅ `application.yaml`: 密码统一为 `verysecret`
- ✅ `application-prod.yaml`: 数据库主机改为 `mysql`，密码改为 `verysecret`

### 5. 新增文件
- ✅ `.dockerignore`: 允许 JAR 文件被复制到 Docker 镜像
- ✅ `RUN_INSTRUCTIONS.md`: IntelliJ 运行说明
- ✅ `DOCKER_COMPOSE_INSTRUCTIONS.md`: Docker 运行说明

---

## 📤 上传到 GitHub

现在项目已经可以正常运行，可以上传了！

