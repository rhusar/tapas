package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Project;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
//@Local
public interface ProjectFacadeLocal {

   void create(Project project);

   void edit(Project project);

   void remove(Project project);

   Project find(Object id);

   List<Project> findAll();

   List<Project> findRange(int[] range);

   int count();
}
