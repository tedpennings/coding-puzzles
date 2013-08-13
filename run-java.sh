#!/bin/bash

# fail on compiler errors
set -o pipefail
set -e

javac $1.java
time java $1