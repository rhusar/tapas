#!/bin/bash
rm -rf target war
time mvn clean install -Dmaven.test.skip=true

