FROM amazoncorretto:8

ENV APP_DIR /usr/share/app
RUN mkdir -p $APP_DIR
EXPOSE 8001
COPY build/libs/mundanza-service-0.0.1-SNAPSHOT.jar $APP_DIR/

CMD java -noverify -jar $APP_DIR/mundanza-service-0.0.1-SNAPSHOT.jar