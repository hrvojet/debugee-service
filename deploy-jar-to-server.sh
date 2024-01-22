#!/bin/bash

### Publish script for debugee-backend
# BEFORE USE: dependencies required: xmllint (libxml2-utils)
#
#
###

#save currnet path
ROOT_WORK_DIR=$(pwd)
PACKAGE_NAME=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="artifactId"]/text()' pom.xml)
PACKAGE_VERSION=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' pom.xml)
JAR_NAME="${PACKAGE_NAME}-${PACKAGE_VERSION}.jar"

#create filename from pom.xml
echo "Full path is $ROOT_WORK_DIR"
echo "Jar name: ${JAR_NAME}"

#goto target
echo "Changing work directory to 'target'"
cd target || echo "Missing 'target', build project with mvn clean install!"

# scp jar to .be-deploy dir
scp "${JAR_NAME}" hrvoje@20.82.136.123:~/.be-deploy/new_jar || echo "Failed to scp"

# Run remote deployment script
# TODO: nohup java -jar -Dspring.profiles.active=prod backend-0.0.1-SNAPSHOT.jar > be.log 2>&1 &
ssh hrvoje@20.82.136.123 "/home/hrvoje/be-deploy.sh"


# goto root
cd "$ROOT_WORK_DIR" || echo "This really shouldn't have failed..."
pwd
