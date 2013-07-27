#!/bin/bash

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

time java -cp libs/clojure-${CLOJURE_VERSION}-slim.jar:libs/math.numeric-tower-${CLOJURE_MATH_VERSION}.jar clojure.main $1