package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import javax.ejb.EJB;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.junit.Test;

/**
 * Quick link to test:
 * ./tests-remote.sh -Dtest=com.radoslavhusar.tapas.ejb.session.ProjectFacadeTest; less target/surefire-reports/com.radoslavhusar.tapas.ejb.session.ProjectFacadeTest.txt
 * 
 */
@RunWith(Arquillian.class)
public class ProjectFacadeTest {

   @EJB(mappedName = "test/ProjectFacade/local")
   ProjectFacadeLocal projectBean;

   @Deployment
   public static JavaArchive createTestArchive() {

      return ResourceAllocationFacadeTest.createTestArchive();
   }

   @Test
   public void testTallyProjectData_long() {
      System.out.println("testTallyProjectData_long");
      ProjectStats pd = projectBean.tallyProjectStats((long) 1);

      System.out.println("ProjectStats are " + pd.toString());
   }
}
