# 使用官方 Java 镜像
FROM openjdk:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制构建好的 JAR 文件到容器
COPY /target/bigwork_meta-1.0.0-SNAPSHOT.jar bigwork_meta-1.0.0-SNAPSHOT.jar

# 暴露端口
EXPOSE 8082 8084

# 启动应用
ENTRYPOINT ["java", "-jar", "bigwork_meta-1.0.0-SNAPSHOT.jar"]