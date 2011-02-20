package com.radoslavhusar.tapas.war.server.services;

import com.radoslavhusar.tapas.ejb.entity.Employee;
import com.radoslavhusar.tapas.ejb.entity.Project;
import com.radoslavhusar.tapas.ejb.session.TaskFacadeLocal;
import com.radoslavhusar.tapas.war.shared.services.MyResourceService;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import net.sf.gilead.configuration.ConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;
import net.sf.gilead.core.hibernate.jboss.HibernateJBossUtil;

//@PersistenceContext(unitName = "MyPersistenceUnit", name = "persistence/em")
public class MyResourceServiceImpl extends PersistentRemoteService implements MyResourceService {

   private static final long serialVersionUID = 1L;
   @EJB
   private TaskFacadeLocal tasks;
//   @PersistenceUnit(unitName = "MyPersistenceUnit")
//   private EntityManagerFactory emf;

   public MyResourceServiceImpl() {
      EntityManagerFactory emf = null;
      try {
         InitialContext ic = new InitialContext();
         emf = (EntityManagerFactory) ic.lookup("java:/ManagerFactory");
      } catch (NamingException ex) {
         ex.printStackTrace();
      }


      HibernateJBossUtil gileadHibernateUtil = new HibernateJBossUtil(emf);
      //gileadHibernateUtil.setSessionFactory(MyHibernateUtil.getSessionFactory());
      setBeanManager(ConfigurationHelper.initStatefulBeanManager(gileadHibernateUtil));


//         gileadHibernateUtil.setEntityManagerFactory(emf);


//      try {
//         InitialContext ctx = new InitialContext();
//         EntityManagerFactory emf = (EntityManagerFactory) ctx.lookup("java:/mrepoEntityManagerFactory");
//         gileadHibernateUtil.setEntityManagerFactory(emf);
//      } catch (Exception e) {
//         e.printStackTrace();
//      }

      /*   PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
      persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);
      persistentBeanManager.setProxyStore(new StatelessProxyStore());

      setBeanManager(persistentBeanManager);*/
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

   public int getCount() throws Exception {
      return tasks.count();
   }
}
