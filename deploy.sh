#!/bin/bash

#undeploy the app anyway
rm ~/mtdep/tapas-ear*.ear
sh build.sh ${1}
mv tapas-ear/target/tapas-ear-0.0.1-SNAPSHOT.ear ~/mtdep/
