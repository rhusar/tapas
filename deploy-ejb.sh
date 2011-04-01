#!/bin/bash

#undeploy the app anyway
rm ~/mtdep/tapas-ear*.ear

cd tapas-ejb
mvn clean install

cd tapas-ear
mvn clean install

mv tapas-ear/target/tapas-ear-0.0.1-SNAPSHOT.ear ~/mtdep/
