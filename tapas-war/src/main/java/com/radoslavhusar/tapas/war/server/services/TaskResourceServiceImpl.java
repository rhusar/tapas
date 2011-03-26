package com.radoslavhusar.tapas.war.server.services;

import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocation;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationData;
import com.radoslavhusar.tapas.ejb.entity.Task;
import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import com.radoslavhusar.tapas.ejb.session.ProjectFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ProjectPhaseFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ResourceFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ResourceGroupFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ResourceAllocationFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.TimeAllocationFacadeLocal;
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
   private TaskFacadeLocal taskBean;
   @EJB
   private ProjectFacadeLocal projects;
   @EJB
   private ProjectPhaseFacadeLocal phases;
   @EJB
   private ResourceFacadeLocal resourceBean;
   @EJB
   private TimeAllocationFacadeLocal timeAllocationBean;
   @EJB
   private ResourceAllocationFacadeLocal allocations;
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
   public void editProject(Project project) {
      // Persist the phases
      for (ProjectPhase p : project.getPhases()) {
         if (p.getId() == null) {
            phases.create(p);
         } else {
            phases.edit(p);
         }
      }
      projects.edit(project);
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
   public Map<Long, ResourceAllocationData> fetchAllResourceDataForProject(long projectId) {
      List<Resource> list = resourceBean.findAllForProject(projectId);
      Map<Long, ResourceAllocationData> result = new HashMap<Long, ResourceAllocationData>();

      // Make it better presentable
      for (Resource res : list) {
         result.put(res.getId(), resourceBean.fetchDataForProject(res.getId(), projectId));
      }

      return result;
   }

   /*@Override
   public Map<Long, ResourceAllocation> findAllAllocationsForProject(long projectId) {
   List<ResourceAllocation> list = allocations.findAllForProject(projectId);
   
   // Make it better presentable
   Map<Long, ResourceAllocation> map = new HashMap<Long, ResourceAllocation>();
   
   for (ResourceAllocation ra : list) {
   map.put(ra.getKey().getResource().getId(), ra);
   }
   
   return map;
   }*/
   @Override
   public List<Resource> findAllResourcesForProject(long projectId) {
      return resourceBean.findAllForProject(projectId);
   }

   @Override
   public void editResourcesForProject(long projectId, Collection<Resource> resources) {
      for (Resource res : resources) {
         if (res.getId() == null) {
            // its a new resource, persist it
            resourceBean.create(res);
            // now persist the NEW allocation
            for (ResourceAllocation rpa : res.getResourceAllocations()) {
               if (rpa.getPercent() > 0) {
                  // save only if allocated to more than 0%
                  allocations.create(rpa);
               }
            }
         } else {
            // resource is not new, save changes
            // allocations changed?
            for (ResourceAllocation rpa : res.getResourceAllocations()) {
               if (rpa.getPercent() < 0) {
                  // remove if allocated to 0%
                  allocations.remove(rpa);
               } else {
                  // update it
                  allocations.edit(rpa);
               }
            }
            // secondly - so that references are already persisted
            res.setResourceAllocations(null);
            resourceBean.edit(res);
         }
      }
   }

   @Override
   public void editTasks(Collection<Task> tasks) {
      for (Task t : tasks) {
         if (t.getId() == null) {
            // its is new, persist it
            taskBean.create(t);
            // now persist the NEW time alloc
            for (TimeAllocation ta : t.getTimeAllocations()) {
               if (ta.getAllocation() > 0) {
                  // dont save if allocated to 0%
                  timeAllocationBean.create(ta);
               }
            }
         } else {
            // it is not new, only merge changes
            // allocations changed?
            for (TimeAllocation ta : t.getTimeAllocations()) {
               if (ta.getAllocation() > 0) {
                  timeAllocationBean.edit(ta);
               } else {
                  // remove if its zero
                  timeAllocationBean.remove(ta);
               }
            }
            // secondly - so that references are already persisted
            t.setTimeAllocations(null);
            taskBean.edit(t);
         }
      }
   }

   @Override
   public void editTasksForProject(long projectId, Collection<Task> tasks) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public List<Task> findAllTasksForProject(long projectId) {
      return taskBean.findAllForProject(projectId);
   }

   @Override
   public Project findProject(long projectId) {
      return projects.find(projectId);
   }
}
