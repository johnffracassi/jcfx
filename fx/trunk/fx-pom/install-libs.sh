#!/bin/bash

mvn install:install-file -DgroupId=talib -DartifactId=talib -Dversion=1.0 -Dfile=lib/ta-lib.jar -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -DgroupId=jgraphx -DartifactId=jgraphx -Dversion=1.5.1_11 -Dfile=lib/jgraphx.jar -Dpackaging=jar -DgeneratePom=true

