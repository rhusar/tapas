echo > ~/jbas/server/default/log/output.log
echo > target/surefire-reports/com.radoslavhusar.tapas.ejb.session.SolverFacadeLocalTest.txt
./tests-managed.sh -Dtest=com.radoslavhusar.tapas.ejb.session.SolverFacadeLocalTest
less target/surefire-reports/com.radoslavhusar.tapas.ejb.session.SolverFacadeLocalTest.txt
less ~/jbas/server/default/log/output.log
