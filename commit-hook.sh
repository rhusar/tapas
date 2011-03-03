#!/bin/bash
rm -rf /tmp/ci
svn export -r BASE . /tmp/ci
cd /tmp/ci
time mvn clean install -Dmaven.test.skip=true

