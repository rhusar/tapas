package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.ProjectPhase;
import java.util.List;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
//@Local
public interface ProjectPhaseFacadeLocal {

   void create(ProjectPhase projectPhase);

   void edit(ProjectPhase projectPhase);

   void remove(ProjectPhase projectPhase);

   ProjectPhase find(Object id);

   List<ProjectPhase> findAll();

   List<ProjectPhase> findRange(int[] range);

   int count();
}
