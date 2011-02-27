#!/bin/bash
rm -rf /tmp/ci
svn export . /tmp/ci
cd /tmp/ci
time mvn clean install -Dmaven.test.skip=true

