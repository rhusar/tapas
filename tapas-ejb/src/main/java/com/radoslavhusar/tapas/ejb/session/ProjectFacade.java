package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.stats.ProjectStats;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.stats.PhaseStatsEntry;
import com.radoslavhusar.tapas.ejb.stats.ResourcePhaseStatsEntry;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ProjectFacadeLocal.class)
public class ProjectFacade extends AbstractFacade<Project> implements ProjectFacadeLocal {

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

   @Override
   public void editOrCreate(Project entity) {
      if (entity.getId() == null) {
         create(entity);
      } else {
         edit(entity);
      }
   }

   /**
    * Returns days remaining and days remaining on each project phase.
    * <em>Some more things can be done as JDBC query. Move it there.</em>
    * 
    * For some stupid reason GILEAD doesn't serialize 2 maps in one entity.
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
      result.setMandayRate(mandayRate);
      //System.out.println("Current rate: " + mandayRate);


      Calendar nowButFuture = Calendar.getInstance();

      double projectAllocated = 0;
      double projectCompleted = 0;

      // Phases must be sorted!!
      Collections.sort(p.getPhases());

      // Calculate estimated times etc.
      for (ProjectPhase pp : p.getPhases()) {

         long phaseId = pp.getId();

         // Get stats for this phase:
         List<ResourcePhaseStatsEntry> rs = resourceBean.tallyResourcesStatsForPhase(phaseId);
         //result.getResources().put(phaseId, rs);

         double allocated = 0;
         double completed = 0;
         double maxMandaysRemaining = 0;

         for (ResourcePhaseStatsEntry s : rs) {
            allocated += s.getAllocated();
            completed += s.getCompleted();

            // increase project counters
            projectAllocated += allocated;
            projectCompleted += completed;
         }

         if (pp.getEnded() != null) {
            // Just get PROJECTIONS for UNFINISHED phases.
            continue;
         }

         // Calculate predicion
         for (ResourcePhaseStatsEntry s : rs) {
            if (s.getResource() != null) {
               //if (s.getRate() > 0) {
               maxMandaysRemaining = Math.max(maxMandaysRemaining, (allocated - completed) / s.getRate());
            }
         }

         // All data is gathered, create an estimate now. 
         int mandaysRemaining = Math.round((float) Math.ceil(maxMandaysRemaining * (double) 7 / (double) 5));

         nowButFuture.add(Calendar.DATE, mandaysRemaining);

         // Calculate a days slip as well
         int slip = (int) ((nowButFuture.getTime().getTime() - pp.getTargetted().getTime()) / (24 * 60 * 60 * 1000));

         // This wouldnt reflect assignment to each people :-/ Estimated date = Now + ( allocated-completed / mandayRate )
         result.getProjection().put(phaseId, new PhaseStatsEntry(allocated, completed, nowButFuture.getTime(), slip));
      }

      result.setAllocated(projectAllocated);
      result.setCompleted(projectCompleted);

      //result.setResources(null);
      return result;
   }

   /* Now its also done in HQL: */
   public static double getRate(Resource resource) {
      return (double) resource.getContract() / (double) 100 * (double) resource.getResourceAllocations().get(0).getPercent() / (double) 100;
   }
}
