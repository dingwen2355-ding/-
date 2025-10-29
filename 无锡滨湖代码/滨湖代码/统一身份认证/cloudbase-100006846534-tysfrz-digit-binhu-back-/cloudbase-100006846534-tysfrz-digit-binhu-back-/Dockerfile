FROM java:openjdk-8u111-alpine
EXPOSE 81
WORKDIR /lc
VOLUME /lc/tmp
ADD ./target/digit-binhu-back.jar  /app.jar
ENV PROFILES=prod
RUN apk add --update font-adobe-100dpi ttf-dejavu fontconfig
ENTRYPOINT [ "java", "-jar", "/app.jar","--spring.profiles.active=${PROFILES}","--server.port=81" ]
