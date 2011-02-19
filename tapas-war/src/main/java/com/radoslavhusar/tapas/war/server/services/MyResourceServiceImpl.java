package com.radoslavhusar.tapas.war.server.services;

import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import com.radoslavhusar.tapas.war.shared.services.MyResourceService;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.gwt.PersistentRemoteService;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.hibernate.jboss.HibernateJBossUtil;

public class MyResourceServiceImpl extends PersistentRemoteService implements MyResourceService {

   private static final long serialVersionUID = 1L;

   public MyResourceServiceImpl() {
      HibernateUtil gileadHibernateUtil = new HibernateJBossUtil();
//      gileadHibernateUtil.setSessionFactory(MyHibernateUtil.getSessionFactory());

      PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
      persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);
      persistentBeanManager.setProxyStore(new StatelessProxyStore());

      setBeanManager(persistentBeanManager);
   }

//   public MyResourceServiceImpl() {
////      HibernateUtil hibernateUtil = new HibernateUtil(MyApplicationHibernateUtil.getSessionFactory());
////      PersistentBeanManager persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(hibernateUtil);
////      setBeanManager(persistentBeanManager);
//   }
   /**
    * Constructor
    */
//   public MyResourceServiceImpl() {
//      HibernateUtil util = new HibernateUtil();
//      util.setSessionFactory(util.getSessionFactory());
//      PersistentBeanManager beanManager = new PersistentBeanManager();
//      beanManager.setPersistenceUtil(util);
//      beanManager.setProxyStore(new StatelessProxyStore());
//      setBeanManager(beanManager);
//   }
   /**
    * Constructor
    */
//   public MyResourceServiceImpl() {
//      HibernateUtil gileadHibernateUtil = new HibernateUtil();
////      gileadHibernateUtil.setSessionFactory(MyHibernateUtil.getSessionFactory());
//      gileadHibernateUtil.setSessionFactory(HibernateJBossUtil.getSessionFactory());
//
//
//      PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
//      persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);
//      persistentBeanManager.setProxyStore(new StatelessProxyStore());
//
//      this.setBeanManager(persistentBeanManager);
//   }
   @Override
   public List<Employee> getResourcesForProject(Project project) throws Exception {
      List e = new LinkedList();

      e.add(new Employee(1, "Radoslav", "Husar", null));
      e.add(new Employee(1, "Falsodar", "Husar", null));
      e.add(new Employee(1, "Michal", "Husar", null));

      return e;
   }
   @EJB
   private TaskFacadeLocal tasks;

   public int getCount() throws Exception {
      return tasks.count();
   }
}
