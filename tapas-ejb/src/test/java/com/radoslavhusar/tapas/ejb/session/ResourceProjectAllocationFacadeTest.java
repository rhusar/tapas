package com.radoslavhusar.tapas.ejb.session;

import org.jboss.shrinkwrap.api.ArchivePaths;
import javax.ejb.EJB;
import org.jboss.arquillian.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import javax.persistence.EntityManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@RunWith(Arquillian.class)
public class ResourceProjectAllocationFacadeTest {

   @EJB(mappedName="test/ProjectFacade/local")
   ProjectFacadeLocal instance;

   @Deployment
   public static JavaArchive createTestArchive() {

      /*return ShrinkWrap.create(JavaArchive.class, "test.jar").
      addClasses(TemperatureConverter.class).
      addAsManifestResource(
      EmptyAsset.INSTANCE,
      ArchivePaths.create("beans.xml"));*/

      return ShrinkWrap.create(JavaArchive.class).
              addPackage("com.radoslavhusar.tapas.ejb.entity").
              addPackage("com.radoslavhusar.tapas.ejb.session").
              addAsManifestResource("test-persistence.xml", ArchivePaths.create("persistence.xml"));
   }

   public ResourceProjectAllocationFacadeTest() {
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
      System.out.println("getEntityManager");
      //ResourceProjectAllocationFacade instance = new ResourceProjectAllocationFacade();
      EntityManager expResult = null;
      //EntityManager result = instance.getEntityManager();
      System.out.println(instance.findAll());
      //assertEquals(expResult, result);
      // TODO review the generated test code and remove the default call to fail.
      //fail("The test case is a prototype.");
   }
   /*

   @Test
   public void testFindAllForProject_Project() {
   System.out.println("findAllForProject");
   Project project = null;
   ResourceProjectAllocationFacade instance = new ResourceProjectAllocationFacade();
   List expResult = null;
   List result = instance.findAllForProject(project);
   assertEquals(expResult, result);
   // TODO review the generated test code and remove the default call to fail.
   fail("The test case is a prototype.");
   }

   @Test
   public void testFindAllForProject_long() {
   System.out.println("findAllForProject");
   long projectId = 0L;
   ResourceProjectAllocationFacade instance = new ResourceProjectAllocationFacade();
   List expResult = null;
   List result = instance.findAllForProject(projectId);
   assertEquals(expResult, result);
   // TODO review the generated test code and remove the default call to fail.
   fail("The test case is a prototype.");
   }*/
}
