FROM amd64/amazoncorretto:21

WORKDIR /app

COPY ./build/libs/watchdog-0.0.1-SNAPSHOT.jar /app/notify.jar

CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=dev", "/app/notify.jar"]