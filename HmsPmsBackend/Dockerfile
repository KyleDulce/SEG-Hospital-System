FROM gradle:8.2.1-jdk17-alpine as buildNode

WORKDIR /project-build

#Move project into working directory
COPY ./ /project-build

RUN gradle clean bootjar -x test

#Prepare runtime
FROM eclipse-temurin:17-jre-alpine 

COPY --from=buildNode /project-build/build/libs/hmspms-backend.jar app.jar
COPY /ext-tools/docker-compose-wait/wait wait

RUN chmod +x /wait

EXPOSE 8080

ENV WAIT_COMMAND="java -jar app.jar"

ENTRYPOINT [ "/wait" ]
