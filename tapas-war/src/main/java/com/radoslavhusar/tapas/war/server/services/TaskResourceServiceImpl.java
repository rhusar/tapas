package com.radoslavhusar.tapas.war.server.services;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceProjectAllocation;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.session.ProjectFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ProjectPhaseFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ResourceFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ResourceGroupFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ResourceProjectAllocationFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TaskTimeAllocationFacadeLocal;
import com.radoslavhusar.tapas.war.shared.services.TaskResourceService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import net.sf.gilead.configuration.ConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;
import net.sf.gilead.core.hibernate.jboss.HibernateJBossUtil;
import org.jboss.logging.Logger;

public class TaskResourceServiceImpl extends PersistentRemoteService implements TaskResourceService {

   private static final long serialVersionUID = 1L;
   // Using jboss Logger via log4j
   private static final Logger log = Logger.getLogger(TaskResourceServiceImpl.class.getName());
   // Workaround for injection https://issues.jboss.org/browse/JBAS-5646
   @EJB
   private TaskFacadeLocal tasks;
   @EJB
   private ProjectFacadeLocal projects;
   @EJB
   private ProjectPhaseFacadeLocal phases;
   @EJB
   private ResourceFacadeLocal resourceBean;
   @EJB
   private TaskTimeAllocationFacadeLocal taskTime;
   @EJB
   private ResourceProjectAllocationFacadeLocal allocations;
   @EJB
   private ResourceGroupFacadeLocal resourceGroup;

   public TaskResourceServiceImpl() {
      EntityManagerFactory emf = null;
      try {
         InitialContext ic = new InitialContext();
         emf = (EntityManagerFactory) ic.lookup("java:/ManagerFactory");
      } catch (NamingException ex) {
         log.error("Problem getting ManagerFactory for GILEAD: " + ex);
      }

      HibernateJBossUtil gileadHibernateUtil = new HibernateJBossUtil(emf);
      setBeanManager(ConfigurationHelper.initStatefulBeanManager(gileadHibernateUtil));
   }

   @Override
   public int getCount() throws Exception {
      return tasks.count();
   }

   @Override
   public List<Task> findAll() {
      return tasks.findAll();
   }

   @Override
   public void create(Task task) {
      tasks.create(task);
   }

   @Override
   public void edit(Task task) {
      tasks.edit(task);
   }

   @Override
   public void editProject(Project project) {
      // Persist the phases
      for (ProjectPhase p : project.getPhases()) {
         if (p.getId() == 0) {
            phases.create(p);
         } else {
            phases.edit(p);
         }
      }
      projects.edit(project);
   }

   @Override
   public void remove(Task task) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Task find(long id) {
      return tasks.find(id);
   }

   @Override
   public List<Task> findRange(int[] range) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public List<Project> findAllProjects() {
      return projects.findAll();
   }

   @Override
   public List<ResourceGroup> findAllGroups() {
      return resourceGroup.findAll();
   }

   @Override
   public Map<Resource, Double[]> findAllResourceStatsForProject(long projectId) {
      List<Resource> list = resourceBean.findAllForProject(projectId);
      HashMap<Resource, Double[]> result = new HashMap<Resource, Double[]>();

      for (Resource r : list) {
         Double[] stats = resourceBean.statForProject(r.getId(), projectId);
         result.put(r, stats);
      }

      return result;
   }

   @Override
   public List<ResourceProjectAllocation> findAllAllocationsForProject(long projectId) {
      return allocations.findAllForProject(projectId);
   }

   @Override
   public List<Resource> findAllResourcesForProject(long projectId) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void editResourcesForProject(long projectId, Collection<Resource> resources) {
      for (Resource res : resources) {
         if (res.getId() == 0) {
            // its a new resource, persist it
            resourceBean.create(res);

            // now persist the NEW allocation
            for (ResourceProjectAllocation rpa : res.getResourceProjectAllocations()) {
               if (rpa.getPercent() != 0) {
                  // dont save if allocated to 0%
                  allocations.create(rpa);
               }
            }
         } else {
            // resource is not new, save changes

            // allocations changed?
            for (ResourceProjectAllocation rpa : res.getResourceProjectAllocations()) {
               /*if (rpa.getId() == 0) {
               // persist it
               if (rpa.getPercent() != 0) {
               // dont save if allocated to 0%
               allocations.create(rpa);
               }
               } else {
                */ if (rpa.getPercent() == 0) {
                  // remove if allocated to 0%
                  allocations.remove(rpa);
               } else {
                  // update it
                  allocations.edit(rpa);
               }
               /*}*/

            }

            // secondly - so that references are already persisted
            res.setResourceProjectAllocations(null);
            resourceBean.edit(res);
         }
      }
   }
}
