FROM openjdk:8-jdk

MAINTAINER Amjad Khader <amjadkhader49@gmail.com>

COPY /target/skeleton-1.0.0.jar skeleton.jar

EXPOSE 8092

ENTRYPOINT ["java", "-jar", "skeleton.jar"]