package com.radoslavhusar.tapas.war.client.util;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.stats.ResourcePriorityAllocationStats;
import java.util.Date;

public class DataUtil {

   public static double cleanTaxDays(double days, byte tax) {
      return days * (100 - tax) / 100;
   }

   public static double calculateAssigned(ResourcePriorityAllocationStats rad) {
      return rad.getP1Allocation() + rad.getP2Allocation() + rad.getP3Allocation() + rad.getTbdAllocation();
   }

   public static double calculateRemaining(ResourcePriorityAllocationStats rad) {
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
