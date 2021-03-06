#!/bin/bash

# fail on compiler or fetch errors
set -o pipefail
set -e

CLOJURE_VERSION="1.5.1"
CLOJURE_MATH_VERSION="0.0.2"

if [ ! -d libs ]
then
	mkdir libs
fi

if [ ! -r "libs/clojure-${CLOJURE_VERSION}-slim.jar" ]
then
	wget -P libs "https://repository.sonatype.org/service/local/repositories/central-proxy/content/org/clojure/clojure/${CLOJURE_VERSION}/clojure-${CLOJURE_VERSION}-slim.jar"
fi

if [ ! -r "libs/math.numeric-tower-${CLOJURE_MATH_VERSION}.jar" ]
then
	wget -P libs "https://repository.sonatype.org/service/local/repositories/central-proxy/content/org/clojure/math.numeric-tower/${CLOJURE_MATH_VERSION}/math.numeric-tower-${CLOJURE_MATH_VERSION}.jar"
fi

file=$1
load_main=""

if [[ $file == *test.clj ]]
then
  main=$(echo $file | sed -e "s/-test//g")
  if [ -f $main ]; then
    load_main="-i $main"
    echo "Running test $file with $main included..."
  fi
fi

time java -cp libs/clojure-${CLOJURE_VERSION}-slim.jar:libs/math.numeric-tower-${CLOJURE_MATH_VERSION}.jar clojure.main $load_main $file