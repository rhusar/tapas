package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationData;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ResourceFacadeLocal.class)
public class ResourceFacade extends AbstractFacade<Resource> implements ResourceFacadeLocal {

   @PersistenceContext
   private EntityManager em;

   @Override
   protected EntityManager getEntityManager() {
      return em;
   }

   public ResourceFacade() {
      super(Resource.class);
   }

   @Override
   public List<Resource> findAllForProject(long projectId) {
      // Do an inner join - fetch only assigned to the project.
      List<Resource> result = getEntityManager().createNamedQuery("resourcesForProject").setParameter("projectid", projectId).getResultList();

      // Actually, just fetch that one assignement for the project
      // TODO: get allocation facade and do 2 lookups and the assign.
      for (Resource res : result) {
         for (ResourceAllocation pa : res.getResourceAllocations()) {
            if (pa.getKey().getProject().getId().equals(projectId)) {
               List<ResourceAllocation> ral = new ArrayList();
               ral.add(pa);
               res.setResourceAllocations(ral);
               break;
            }
         }
         
         // Lazy loaded, so fetch them all - cant really switch to eager because
         // http://opensource.atlassian.com/projects/hibernate/browse/HHH-2980
         res.getTraits().size();
      }

      return result;
   }

   /**
    *
    * @param resourceId
    * @param projectId
    * @return array of P1 Ass, P1 Done, P2 Ass, P2 Done, P3 Ass, P3 Done, PX Ass, PX Done
    */
   @Override
   public ResourceAllocationData fetchDataForProject(long resourceId, long projectId) {
      // TODO: get this to work, aggregate via Hibernate
//       List l = getEntityManager().
//              createQuery("select sum(a.timeAllocation) from " + Resource.class.getSimpleName() + " as o "
//              + "inner join o.tasks as t "
//              + "inner join t.project as p "
//              + "inner join t.timeAllocations as a "
//              + "where p.project.id = :projectid "
//              + "and o.id = :resourceid ").
//              setParameter("projectid", projectId).
//              setParameter("resourceid", resource.getId()).
//              getResultList();

      // FIXME: WORST MAYBE UGLIEST HACK EVER
      Double[] result = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

      // Fetch all users task - done by lazy loading

      // Iterate through and get the totals
      for (Task t : find(resourceId).getTasks()) {
         System.out.println(t);
         if (t.getProject().getId() == projectId) {
            for (TimeAllocation tta : t.getTimeAllocations()) {
               if (t.getPriority() != null && t.getPriority() >= 1 && t.getPriority() <= 3) {
                  result[(t.getPriority() - 1) * 2] += tta.getAllocation();
                  result[(t.getPriority() - 1) * 2 + 1] += tta.getCompleted();
               } else {
                  result[6] += tta.getAllocation();
                  result[7] += tta.getCompleted();
               }
            }
         }
      }

      ResourceAllocationData ras = new ResourceAllocationData(resourceId, projectId, result[0], result[1], result[2], result[3], result[4], result[5], result[6], result[7]);
      System.out.println(ras.toString());

      return ras;
   }
}
