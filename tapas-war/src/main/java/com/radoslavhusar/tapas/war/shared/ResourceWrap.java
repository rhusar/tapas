package com.radoslavhusar.tapas.war.shared;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import java.io.Serializable;

/**
 * Encloses Resource and view layer information.
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
@Deprecated
public class ResourceWrap implements Serializable {

   private static final long serialVersionUID = 1L;
   private Resource resource;
   private Double[] plan;
   private boolean changed = false;

   public ResourceWrap(Resource resource, Double[] plan) {
      this.resource = resource;
      this.plan = plan;
   }

   public ResourceWrap(Resource resource) {
      this.resource = resource;
   }

   public Double[] getPlan() {
      return plan;
   }

   public void setPlan(Double[] plan) {
      this.plan = plan;
   }

   public Resource getResource() {
      return resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public boolean isChanged() {
      return changed;
   }

   public void setChanged(boolean changed) {
      this.changed = changed;
   }
}
