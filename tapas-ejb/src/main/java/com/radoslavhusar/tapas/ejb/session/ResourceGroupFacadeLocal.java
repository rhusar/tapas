
package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.ResourceGroup;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
//@Local
public interface ResourceGroupFacadeLocal {

   void create(ResourceGroup resourceGroup);

   void edit(ResourceGroup resourceGroup);

   void remove(ResourceGroup resourceGroup);

   ResourceGroup find(Object id);

   List<ResourceGroup> findAll();

   List<ResourceGroup> findRange(int[] range);

   int count();

}
