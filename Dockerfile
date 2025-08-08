# This line tells Docker to base an image on a pre-built image with Alpine Linux. You can use other images from OpenJDK
# registry. Alpine Linux benefit is that the image is pretty small. We also select JRE-only image since we don't need
# to compile code on the image, only run precompiled classes.
FROM eclipse-temurin:17-jdk-alpine

# If for some reason you wish to use the full JDK, the following line can be used
# FROM adoptopenjdk/openjdk11:alpine

ARG PROJECT_VERSION
RUN echo "Project version set to -> ${PROJECT_VERSION}"

ENV APPLICATION_USER=ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

RUN mkdir /home/$APPLICATION_USER/.fhir
RUN chown -R $APPLICATION_USER /home/$APPLICATION_USER/.fhir

USER $APPLICATION_USER

# These lines copy the packaged application into the Docker image and sets the working directory to where it was copied.
COPY ./build/libs/validator-wrapper-jvm-${PROJECT_VERSION}.jar /app/validator-wrapper.jar
WORKDIR /app

# Environment vars here
ENV ENVIRONMENT=prod

EXPOSE 3500

# The last line instructs Docker to run java with G10s GC,  assigns 79% of the system's available memory, and indicates the packaged application.
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:InitialRAMPercentage=79", "-XX:MinRAMPercentage=79", "-XX:MaxRAMPercentage=79", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-XX:+CrashOnOutOfMemoryError", "-jar", "validator-wrapper.jar", "-startServer"]
