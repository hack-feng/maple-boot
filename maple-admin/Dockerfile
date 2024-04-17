FROM openjdk:8-jdk-alpine
VOLUME /temp
EXPOSE 6666
ADD maple-admin.jar maple-admin.jar
ENTRYPOINT ["java","-jar","/maple-admin.jar"]