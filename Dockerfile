FROM amazoncorretto:17.0.8-alpine as build
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew
RUN apk add dos2unix
RUN dos2unix ./gradlew
RUN ./gradlew build || return 0
COPY src ./src
RUN ./gradlew clean bootJar

FROM amazoncorretto:17.0.8-alpine
ENV APP_HOME=/app \
    DB_HOST=default_host \
    DB_PORT=default_port \
    DB_NAME=default_name \
    DB_USER=default_user \
    DB_PASSWORD=default_password \
    S3_BUCKET_NAME=s3_bucket \
    S3_ACCESS_KEY=s3_key \
    S3_SECRET_KEY=s3_secret \
    STORE_HOSTNAME=default_store_host \
    USER_AUTH_HOSTNAME=default_user_host
WORKDIR $APP_HOME
ARG JAR_FILE=build/libs/*.jar
COPY --from=build $APP_HOME/$JAR_FILE ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]