
package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.TaskTimeAllocation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
//@Local
public interface TaskTimeAllocationFacadeLocal {

   void create(TaskTimeAllocation taskTimeAllocation);

   void edit(TaskTimeAllocation taskTimeAllocation);

   void remove(TaskTimeAllocation taskTimeAllocation);

   TaskTimeAllocation find(Object id);

   List<TaskTimeAllocation> findAll();

   List<TaskTimeAllocation> findRange(int[] range);

   int count();

}
