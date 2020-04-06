FROM amazoncorretto:8

ENV APP_DIR /usr/share/app
RUN mkdir -p $APP_DIR
EXPOSE 8080
COPY build/libs/gateway-service-0.0.1-SNAPSHOT.jar $APP_DIR/

CMD java -noverify -jar $APP_DIR/gateway-service-0.0.1-SNAPSHOT.jar