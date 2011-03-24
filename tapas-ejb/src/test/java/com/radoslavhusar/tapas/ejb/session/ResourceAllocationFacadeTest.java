package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import org.jboss.shrinkwrap.api.ArchivePaths;
import javax.ejb.EJB;
import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@RunWith(Arquillian.class)
public class ResourceAllocationFacadeTest {

   @EJB(mappedName = "test/ProjectFacade/local")
   ProjectFacadeLocal projectBean;
   @EJB(mappedName = "test/ResourceAllocationFacade/local")
   ResourceAllocationFacadeLocal allocationBean;

   @Deployment
   public static JavaArchive createTestArchive() {

      return ShrinkWrap.create(JavaArchive.class).
              addPackage("com.radoslavhusar.tapas.ejb.entity").
              addPackage("com.radoslavhusar.tapas.ejb.session").
              addAsManifestResource("test-persistence.xml", ArchivePaths.create("persistence.xml"));
   }

   public ResourceAllocationFacadeTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() {
   }

   @After
   public void tearDown() {
   }

   @Test
   public void testGetEntityManager() {
      System.out.println("getEntityManager test - named query test");

      Project p = projectBean.findAll().get(0);
      System.out.println("Got project: " + p);

      System.out.println("Got allocations: " + allocationBean.findAllForProject(p.getId()));
   }
}
