# This line tells Docker to base an image on a pre-built image with Alpine Linux. You can use other images from OpenJDK
# registry. Alpine Linux benefit is that the image is pretty small. We also select JRE-only image since we don't need
# to compile code on the image, only run precompiled classes.
FROM openjdk:8-jre-alpine

ENV APPLICATION_USER ktor
RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

# These lines copy the packaged application into the Docker image and sets the working directory to where it was copied.
COPY ./build/libs/my-application.jar /app/my-application.jar
WORKDIR /app

# The last line instructs Docker to run java with G10s GC, 4G of memory and the packaged application.
CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "my-application.jar"]