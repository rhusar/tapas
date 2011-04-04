#!/bin/bash

#undeploy the app anyway
rm ~/mtdep/tapas-ear*.ear

cd tapas-ejb 
mvn clean install -Dmaven.test.skip=true ${1}

cd ../tapas-ear 
mvn clean install -Dmaven.test.skip=true ${1}

mv target/tapas-ear-0.0.1-SNAPSHOT.ear ~/mtdep/
