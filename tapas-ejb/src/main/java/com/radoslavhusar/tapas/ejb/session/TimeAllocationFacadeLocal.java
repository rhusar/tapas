package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.TimeAllocation;
import java.util.List;

public interface TimeAllocationFacadeLocal {

   // CRUD
   void create(TimeAllocation taskTimeAllocation);

   void edit(TimeAllocation taskTimeAllocation);

   void remove(TimeAllocation taskTimeAllocation);

   TimeAllocation find(Object id);

   List<TimeAllocation> findAll();

   List<TimeAllocation> findRange(int[] range);

   int count();
   // NON-CRUD
}
