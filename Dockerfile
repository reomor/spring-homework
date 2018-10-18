FROM openjdk:8
ENV PROJECT task17-1.0-SNAPSHOT.jar
ADD task17/target/$PROJECT $PROJECT
EXPOSE 8080

RUN echo "#!/bin/bash \n java -jar $PROJECT" > ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]