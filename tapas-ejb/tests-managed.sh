rm /home/rhusar/Download/jboss-5.1.0.GA/server/default/deploy/*.ear
time mvn clean surefire-report:report -Pjbossas-managed-5.1 ${1}
