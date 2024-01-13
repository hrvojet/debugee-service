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

echo "Full path is $ROOT_WORK_DIR"
echo "Jar name: ${JAR_NAME}"

#create filename from pom.xml

#goto target
echo "Changing work directory to 'target'"
cd target || echo "Missing 'target', build project with mvn clean install!"

#scp
scp "${JAR_NAME}" hrvoje@20.82.136.123:~ || echo "Failed to scp"

#run remote deployment script


# goto root
cd "$ROOT_WORK_DIR" || echo "This really shouldn't have failed..."
pwd
