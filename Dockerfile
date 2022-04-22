# This line tells Docker to base an image on a pre-built image with Alpine Linux. You can use other images from OpenJDK
# registry. Alpine Linux benefit is that the image is pretty small. We also select JRE-only image since we don't need
# to compile code on the image, only run precompiled classes.
#FROM adoptopenjdk/openjdk11:alpine-jre

# We're presently evaluating memory consumption, so we're switching to JDK for some time. If this is still here by 
# 2022-05-21, please ruthlessly harass david.otasek
FROM adoptopenjdk/openjdk11:alpine

ARG PROJECT_VERSION
RUN echo "Project version set to -> ${PROJECT_VERSION}"

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

# These lines copy the packaged application into the Docker image and sets the working directory to where it was copied.
COPY ./build/libs/validator-wrapper-jvm-${PROJECT_VERSION}.jar /app/validator-wrapper.jar
WORKDIR /app

# Environment vars here
ENV ENVIRONMENT prod

EXPOSE 3500

# The last line instructs Docker to run java with G10s GC,  assigns 79% of the system's available memory, and indicates the packaged application.
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:InitialRAMPercentage=79", "-XX:MinRAMPercentage=79", "-XX:MaxRAMPercentage=79", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-XX:+CrashOnOutOfMemoryError", "-jar", "validator-wrapper.jar", "-startServer"]
