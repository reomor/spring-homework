FROM openjdk:8-jdk-alpine
# Install Maven
RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.5.4
ARG USER_HOME_DIR="/root"
ENV PROJECT_DIR="/root/project"
ENV TASK_DIR="/root/project/task18"

RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

RUN mkdir -p $TASK_DIR
WORKDIR $TASK_DIR
COPY ./ $TASK_DIR
RUN mvn package

RUN cp $TASK_DIR/target/task18-1.0-SNAPSHOT.jar $PROJECT_DIR/app.jar

EXPOSE 8080

CMD ["/usr/bin/java", "-jar", "/root/project/app.jar"]
