#!/bin/bash

set -e
set -x

/opt/app/wait-for-it.sh ${WAIT_LIST}

java -Djava.security.egd=file:/dev/./urandom \
     -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
     ${JAVA_OPTS} \
     -jar /opt/app/server.jar
