package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import java.io.Serializable;
import org.drools.planner.core.score.constraint.ConstraintType;
import org.drools.planner.core.score.constraint.DoubleConstraintOccurrence;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
@Deprecated
public class OneTaskResourceRemainingLogical extends DoubleConstraintOccurrence implements Serializable {

   public Resource resource;
   public double weight;

   public OneTaskResourceRemainingLogical(String ruleId, ConstraintType constraintType, Resource resource, double weight, Object... causes) {
      super(ruleId, constraintType, weight, causes);
      this.resource = resource;
      this.weight = weight;
   }

   public Resource getResource() {
      return resource;
   }

   @Override
   public double getWeight() {
      return weight;
   }
}
