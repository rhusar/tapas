package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.stats.ResourceStats;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ProjectFacadeLocal.class)
public class ProjectFacade extends AbstractFacade<Project> implements ProjectFacadeLocal {

   public static final double WORKWEEK = 5 / 7;
   @EJB
   ResourceFacadeLocal resourceBean;
   @EJB
   TaskFacadeLocal tasksBean;
   /**
    * Or could be   
    * @PersistenceContext(unitName = "TapasPersistenceUnit")
    * but see article: TBD
    */
   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ProjectFacade() {
      super(Project.class);
   }

   /**
    * Returns days remaining and days remaining on each project phase.
    * <em>Needs to be done as JDBC query. Not like this.</em>
    * 
    * @param projectId
    * @return ProjectStats
    */
   @Override
   public ProjectStats tallyProjectStats(long projectId) {
      ProjectStats result = new ProjectStats(projectId);

      // Get the project
      Project p = this.find(projectId);

      // Calculate mandays data - a rate
      double mandayRate = 0;
      List<Resource> resources = resourceBean.findAllForProject(projectId);
      System.out.println("Found " + resources.size() + " resouces on project.");

      for (Resource r : resources) {
         mandayRate += ProjectFacade.getRate(r);
         //System.out.println("Current rate: " + mandayRate);
      }

      // Tax it!
      mandayRate = mandayRate * ((double) 100 - (double) p.getTax()) / (double) 100;
      //System.out.println("Current rate: " + mandayRate);
      result.setMandayRate(mandayRate);

      // Calculate estimated times etc.
      //List<Task> tasks = tasksBean.findAllForProject(projectId);
      List<ResourceStats> rs = em.createQuery("select new com.radoslavhusar.tapas.ejb.stats.ResourceStats(r,sum(ta.allocation),sum(ta.completed),1.0*((100-p.tax)/100*r.contract/100*a.percent/100)) "
              + "from Resource as r "
              + "inner join r.resourceAllocations as a "
              + "inner join a.key.project as p "
              + "left outer join r.tasks as t "
              + "left outer join t.timeAllocations as ta "
              + "where p.id = :projectid "
              + "group by r").
              setParameter("projectid", projectId).
              getResultList();

      System.out.println(rs);

      /*         
      // this needs to be done in a nicer way - JDBC query.
      for (ProjectPhase pp : p.getPhases()) {
      double allocated = 0;
      double completed = 0;
      Date estimatedMax = new Date();
      
      for (Task t : tasks) {
      // This could be a perf killer, this needs to be done in a nicer way - JDBC query.
      for (TimeAllocation ta : t.getTimeAllocations()) {
      if (ta.getPhase().equals(pp)) {
      allocated += ta.getAllocation();
      completed += ta.getCompleted();
      
      // Now do the calculation
      
      
      break; // from TimeAllocation for
      }
      }
      }
      
      // All data is gathered, create an estimate now. 
      // This wouldnt reflect assignment to each people :-/ Estimated date = Now + ( allocated-completed / mandayRate )
      Date pend = new Date();
      result.getProjection().put(pp.getId(), new ProjectPhaseStats(allocated, completed, pend));
      }*/

      return result;
   }

   public static double getRate(Resource resource) {
      return (double) resource.getContract() / (double) 100 * (double) resource.getResourceAllocations().get(0).getPercent() / (double) 100;
   }
}
