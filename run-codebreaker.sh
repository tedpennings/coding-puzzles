#!/bin/bash

# fail on compiler or fetch errors
set -o pipefail
set -e

JUNIT_VERSION="4.11"
HAMCREST_VERSION="1.3"

if [ ! -d libs ]
then
  mkdir libs
fi

if [ ! -r "libs/junit-${JUNIT_VERSION}.jar" ]
then
  wget -P libs "https://repository.sonatype.org/service/local/repositories/central-proxy/content/junit/junit/${JUNIT_VERSION}/junit-${JUNIT_VERSION}.jar"
fi

if [ ! -r "libs/hamcrest-all-${HAMCREST_VERSION}.jar" ]
then
  wget -P libs "https://repository.sonatype.org/service/local/repositories/central-proxy/content/org/hamcrest/hamcrest-all/${HAMCREST_VERSION}/hamcrest-all-${HAMCREST_VERSION}.jar"
fi


echo "Compiling..."
javac -cp "codebreaker:libs/hamcrest-all-${HAMCREST_VERSION}.jar:libs/junit-${JUNIT_VERSION}.jar" codebreaker/*.java


echo "Running tests..."
time java -cp ".:codebreaker:libs/hamcrest-all-${HAMCREST_VERSION}.jar:libs/junit-${JUNIT_VERSION}.jar" org.junit.runner.JUnitCore codebreaker.CodeBreakerTest