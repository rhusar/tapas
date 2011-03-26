package com.radoslavhusar.tapas.war.client.util;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationData;
import java.util.Date;

public class DataUtil {

   public static double calculateAssigned(ResourceAllocationData rad) {
      return rad.getP1Allocation() + rad.getP2Allocation() + rad.getP3Allocation() + rad.getTbdAllocation();
   }

   public static double calculateRemaining(ResourceAllocationData rad) {
      return calculateAssigned(rad) - (rad.getP1Completed() + rad.getP2Completed() + rad.getP3Completed() + rad.getTbdCompleted());
   }

   /**
    * @param target until date
    * @param resource for which to calculate
    * @return man days according to formula: (TargetDate - Now) * AllocationOnProject * Contract
    */
   public static double remainingUntil(Date target, Resource resource) {
      return ((double) (target.getTime() - (new Date()).getTime()) / 86400000) * resource.getResourceAllocations().get(0).getPercent() * resource.getContract() / 100 / 100;
   }

   private DataUtil() {
      // Not instantiable, its a static util class.
   }
}
