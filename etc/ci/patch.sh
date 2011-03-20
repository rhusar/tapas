#!/bin/bash

echo "The diff is:"

diff Application.gwt.xml ../../tapas-war/src/main/java/com/radoslavhusar/tapas/war

echo "Patching (overwrite)..."
cp Application.gwt.xml ../../tapas-war/src/main/java/com/radoslavhusar/tapas/war
echo "Patch done."
