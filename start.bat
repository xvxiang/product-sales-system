@echo off
chcp 65001 >nul
echo ========================================
echo   商品销售系统 - 启动脚本
echo ========================================
echo.

REM 检查 Java 环境
echo [1/3] 检查 Java 环境...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未检测到 Java 环境，请先安装 JDK 17+
    pause
    exit /b 1
)
echo [成功] Java 环境检查通过

REM 下载 Maven 依赖
echo.
echo [2/3] 下载 Maven 依赖...
call mvnw.cmd clean compile -DskipTests
if %errorlevel% neq 0 (
    echo [错误] Maven 依赖下载失败
    pause
    exit /b 1
)
echo [成功] Maven 依赖下载完成

REM 启动应用
echo.
echo [3/3] 启动应用...
echo 应用将在 http://localhost:8080/index.html 运行
echo 按 Ctrl+C 可停止应用
echo.
call mvnw.cmd spring-boot:run

pause
