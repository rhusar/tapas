rm /home/rhusar/Download/jboss-5.1.0.GA/server/default/deploy/test.ear
time mvn clean surefire-report:report -Pjbossas-remote-5.1
