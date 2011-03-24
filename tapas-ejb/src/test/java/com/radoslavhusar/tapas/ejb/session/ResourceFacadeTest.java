package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import java.util.List;
import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import javax.ejb.EJB;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(Arquillian.class)
public class ResourceFacadeTest {

   @EJB(mappedName = "test/ResourceFacade/local")
   ResourceFacadeLocal resourceBean;

   @Deployment
   public static JavaArchive createTestArchive() {

      return ResourceAllocationFacadeTest.createTestArchive();
   }

   @Test
   public void testFindAllForProject_long() {
      System.out.println("findAllForProject");
      List<Resource> l = resourceBean.findAllForProject(1);
      for (Resource r : l) {
         System.out.println("There should be NO fetching now!");
         System.out.println(r.getResourceAllocations());
      }

   }
}
