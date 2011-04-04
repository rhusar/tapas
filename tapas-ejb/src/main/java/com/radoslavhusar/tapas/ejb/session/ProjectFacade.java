package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.stats.ProjectPhaseStats;
import com.radoslavhusar.tapas.ejb.stats.ResourceStats;
import java.util.Calendar;
import java.util.Date;
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

      Calendar nowButFuture = Calendar.getInstance();

      // Calculate estimated times etc.
      for (ProjectPhase pp : p.getPhases()) {

         if (pp.getEnded() != null) {
            // Just get stats for UNFINISHED phases.
            continue;
         }

         double assigned = 0;
         double completed = 0;
         double maxMandaysRemaining = 0;

         // Get stats for this phase:
         List<ResourceStats> rs = resourceBean.tallyResourcesStatsForPhase(pp.getId());

         for (ResourceStats s : rs) {
            assigned += s.getAssigned();
            completed += s.getCompleted();
            maxMandaysRemaining = Math.max(maxMandaysRemaining, (assigned - completed) / s.getRate());
         }

         // All data is gathered, create an estimate now. 
         int mandaysRemaining = (int) Math.round(Math.ceil(maxMandaysRemaining * WORKWEEK));

         nowButFuture.add(Calendar.DATE, mandaysRemaining);

         // This wouldnt reflect assignment to each people :-/ Estimated date = Now + ( allocated-completed / mandayRate )
         result.getProjection().put(pp.getId(), new ProjectPhaseStats(assigned, completed, nowButFuture.getTime()));
      }

      return result;
   }

   public static double getRate(Resource resource) {
      return (double) resource.getContract() / (double) 100 * (double) resource.getResourceAllocations().get(0).getPercent() / (double) 100;
   }
}
