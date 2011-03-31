package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.solver.SolverFacadeLocal;
import com.radoslavhusar.tapas.ejb.entity.Project;
import javax.ejb.EJB;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SolverFacadeLocalTest {

   @EJB(mappedName = "test/SolverFacade/local")
   SolverFacadeLocal solverBean;
   @EJB(mappedName = "test/ResourceFacade/local")
   ResourceFacadeLocal resourceBean;
   @EJB(mappedName = "test/ProjectFacade/local")
   ProjectFacadeLocal projectBean;

   public SolverFacadeLocalTest() {
   }

   @Deployment
   public static JavaArchive createTestArchive() {

      // all dependecies:
      // https://repository.jboss.org/nexus/content/groups/public/org/drools/planner/drools-planner-core/5.2.0.M2/drools-planner-core-5.2.0.M2.pom 
      return ShrinkWrap.create(JavaArchive.class).
              addPackage("com.radoslavhusar.tapas.ejb.entity").
              addPackage("com.radoslavhusar.tapas.ejb.session").
              addPackage("com.radoslavhusar.tapas.ejb.solver"). // needs to include DRL
              addAsResource("com/radoslavhusar/tapas/ejb/solver/taskAllocationScoreRules.drl"). // https://issues.jboss.org/browse/SHRINKWRAP-150
              addAsResource("com/radoslavhusar/tapas/ejb/solver/taskAllocationSolverConfig.xml").
              addDirectory("com.radoslavhusar.tapas.ejb.solver"). // needs to include DRL
              /*addAsResource(MavenArtifactResolver.resolve(
              "org.drools.planner",
              "drools-planner-core",
              "5.1.0.M2")).
              addAsResource(MavenArtifactResolver.resolve(
              "com.thoughtworks.xstream",
              "xstream",
              "1.3.1")).
              addAsResource(MavenArtifactResolver.resolve(
              "commons-io",
              "commons-io",
              "1.4")).*/
              addAsManifestResource("test-persistence.xml", ArchivePaths.create("persistence.xml"));
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
   public void testSolveAssignments() {
      System.out.println("solveAssignments");

      Project p = projectBean.findAll().get(0);
      solverBean.solveAssignments(p.getId());
   }
}
