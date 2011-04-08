package com.radoslavhusar.tapas.ejb.session;

import com.radoslavhusar.tapas.ejb.entity.Trait;
import java.util.List;

public interface TraitFacadeLocal {

   void create(Trait trait);

   void edit(Trait trait);

   void editOrCreate(Trait entity);

   void remove(Trait trait);

   Trait find(Object id);

   List<Trait> findAll();

   List<Trait> findRange(int[] range);

   int count();
}
