FROM maven:3.6.1-jdk-11 AS build
LABEL maintainer="qiangyt@wxcount.com"

COPY docker/settings.xml /root/.m2/settings.xml

WORKDIR /workspace

# force to install all plugins and dependencies for pom POM
COPY pom.xml                    ./pom.xml
COPY common/pom.xml             ./common/pom.xml
COPY api/pom.xml                ./api/pom.xml
COPY server/pom.xml             ./server/pom.xml
COPY jacoco/pom.xml             ./jacoco/pom.xml
COPY integration-test/pom.xml   ./integration-test/pom.xml

RUN mvn clean dependency:go-offline test

# process resource and source code, and test
COPY common/src    ./common/src
COPY api/src       ./api/src
COPY server/src    ./server/src
RUN mvn -B -Dmaven.gitcommitid.skip=true clean install

# ------------------------------------------------------------------------------
FROM openjdk:11.0.6-jre-slim
LABEL maintainer="qiangyt@wxcount.com"

EXPOSE 8080 8081 8888
WORKDIR /opt/app

COPY docker/for-ever.sh   docker/wait-for-it.sh  docker/entrypoint.sh   ./

COPY --from=build /workspace/server/target/server-1.0.0.jar ./server.jar

CMD /opt/app/entrypoint.sh

