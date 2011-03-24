package com.radoslavhusar.tapas.war.client.util;

import com.radoslavhusar.tapas.ejb.entity.ResourceAllocationData;

public class DataUtil {

   public static double calculateAssigned(ResourceAllocationData rad) {
      return rad.getP1Allocation() + rad.getP2Allocation() + rad.getP3Allocation() + rad.getTbdAllocation();
   }

   public static double calculateRemaining(ResourceAllocationData rad) {
      return calculateAssigned(rad) - (rad.getP1Completed() + rad.getP2Completed() + rad.getP3Completed() + rad.getTbdCompleted());
   }

   private DataUtil() {
   }
}
