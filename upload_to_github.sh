#!/bin/bash

echo "======================================"
echo "Event Management System - GitHub 上传脚本"
echo "======================================"
echo ""

# 检查是否已经是 Git 仓库
if [ -d .git ]; then
    echo "✓ Git 仓库已存在"
else
    echo "→ 初始化 Git 仓库..."
    git init
fi

# 添加远程仓库
echo "→ 配置远程仓库..."
git remote remove origin 2>/dev/null
git remote add origin https://github.com/sukikatte/event_management.git

# 检查分支
echo "→ 切换到 main 分支..."
git branch -M main

# 清理不必要的文件
echo "→ 清理构建产物..."
rm -rf target/
rm -rf .idea/
rm -f *.iml

# 添加所有文件
echo "→ 添加文件到 Git..."
git add .

# 显示将要提交的文件
echo ""
echo "======================================"
echo "将要提交的文件："
echo "======================================"
git status --short

# 询问是否继续
echo ""
read -p "是否继续提交并推送? (y/n) " -n 1 -r
echo ""

if [[ $REPLY =~ ^[Yy]$ ]]; then
    # 提交
    echo "→ 提交代码..."
    git commit -m "Initial commit: Event Management System

- Full-stack ticketing platform with multi-role access control
- Spring Boot + Spring Security + JPA/Hibernate + MySQL
- Docker containerization support
- Complete SDLC documentation with 70+ UML diagrams
- Ready to run with detailed setup instructions"

    # 推送
    echo "→ 推送到 GitHub..."
    git push -u origin main

    echo ""
    echo "======================================"
    echo "✓ 上传完成！"
    echo "======================================"
    echo ""
    echo "仓库地址: https://github.com/sukikatte/event_management"
    echo ""
else
    echo "取消上传"
fi

