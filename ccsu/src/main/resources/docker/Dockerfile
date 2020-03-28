#指定基础镜像，在其上进行定制
FROM java:8
#维护者信息
MAINTAINER feng

#在容器的根目录中创建spring-cloud文件夹
RUN mkdir -p /spring-docker

#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
#VOLUME /tmp

#将本地项目jar包拷贝到Docker容器中的位置
COPY ./test-0.0.1-SNAPSHOT.jar /spring-docker/test-0.0.1-SNAPSHOT.jar

#将本地的可执行文件拷贝到Docker容器中的根目录下
COPY ./app-entrypoint.sh /

#给app-entrypoint.sh赋予可执行权限
RUN chmod +x /app-entrypoint.sh


#对外暴漏的端口号
EXPOSE 8080

#开机启动
ENTRYPOINT ["/app-entrypoint.sh"]
