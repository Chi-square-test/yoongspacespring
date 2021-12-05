FROM java:8

LABEL maintainer="luckykr1234@gmail.com"

EXPOSE 7963

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} yoongspace-springboot.jar

ENTRYPOINT ["java","${JAVA_OPTS}","-jar","/yoongspace-springboot.jar"]

