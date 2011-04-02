package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.session.ProjectFacadeLocal;
import com.radoslavhusar.tapas.ejb.session.ResourceFacadeLocal;
import javax.ejb.EJB;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
public class StandaloneSolver {

   @EJB(mappedName = "test/ResourceFacade/local")
   ResourceFacadeLocal resourceBean;
   @EJB(mappedName = "test/ProjectFacade/local")
   ProjectFacadeLocal projectBean;

   public static void main(String[] args) {
   }
}
