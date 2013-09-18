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


# Strip .java extension if provided
file=$(echo $1 | sed -e "s/.java$//g")


if [[ $file == *Test ]]
then
  main=$(echo $file | sed -e "s/Test//g")
  if [ -f $main.java ]; then
    echo "Compiling $main first..."
    javac $main.java 
    #main code had better not need junit/hamcrest...
  fi
fi

echo "Compiling $file..."
javac -cp ".:libs/hamcrest-all-${HAMCREST_VERSION}.jar:libs/junit-${JUNIT_VERSION}.jar" $file.java


echo "Running..."
time java -cp ".:libs/hamcrest-all-${HAMCREST_VERSION}.jar:libs/junit-${JUNIT_VERSION}.jar" $file