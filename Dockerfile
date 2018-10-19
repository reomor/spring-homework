FROM openjdk:8-jdk-alpine
# ----
# Install Maven
# https://codefresh.io/docker-tutorial/java_docker_pipeline/
RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.3.9
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
#ENTRYPOINT ["/usr/bin/mvn"]

# make source folder
RUN mkdir -p /usr/src/task17
WORKDIR /usr/src/task17
# install maven dependency packages (keep in image)
COPY task17/pom.xml /usr/src/task17
COPY task17/src /usr/src/task17
RUN mvn -T 1C install

EXPOSE 8080
#CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=test", "/app.war"]
CMD ["/usr/bin/java", "-jar", "./target/task17-1.0-SNAPSHOT.jar"]
