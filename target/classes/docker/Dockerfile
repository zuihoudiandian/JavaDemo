FROM mykro/java8-jre:latest
VOLUME /tmp
ADD *.jar app.jar
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar" ]
