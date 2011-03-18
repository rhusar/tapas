#!/bin/bash

#undeploy the app anyway
rm ~/mtdep/tapas-ear*.ear

cd tapas-ejb
mvn clean deploy -Dmaven.test.skip

cd ..
cd tapas-ear
mvn clean deploy -Dmaven.test.skip

mv tapas-ear/target/tapas-ear-0.0.1-SNAPSHOT.ear ~/mtdep/
