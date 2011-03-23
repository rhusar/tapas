package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import java.util.Arrays;
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
   public List<Resource> findAllForProject(Project project) {
      return this.findAllForProject(project.getId());
   }

   @Override
   public List<Resource> findAllForProject(long projectId) {
      // Do an inner join - fetch only assigned to the project.
      List<Resource> l = getEntityManager().
              createQuery("select object(o) from " + Resource.class.getSimpleName() + " as o "
              + "inner join o.resourceProjectAllocations as a "
              + " where a.key.project.id = :projectid").
              setParameter("projectid", projectId).
              getResultList();

      // Actually, just fetch that one assignement for the project
      for (Resource r : l) {
         for (ResourceAllocation pa : r.getAllocations()) {
            if (pa.getKey().getProject().getId() == projectId) {
               r.getAllocations().clear();
               r.getAllocations().add(pa);
               break;
            }
         }
      }

      return l;
   }

   @Override
   public Double[] statForProject(Resource resource, long projectId) {
      return statForProject(resource.getId(), projectId);
   }

   /**
    *
    * @param resourceId
    * @param projectId
    * @return array of P1 Ass, P1 Done, P2 Ass, P2 Done, P3 Ass, P3 Done, PX Ass, PX Done
    */
   @Override
   public Double[] statForProject(long resourceId, long projectId) {
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

      System.out.println(Arrays.toString(result));

      return result;
   }
}
