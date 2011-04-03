package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Entity representing a Trait a skill a property of the resource.
 * 
 * Created with unique constraint on the name - ideally it is created with case insensitive. This is later on
 * used in Drools planner.
 */
@Entity
@Table(name = "TRAIT", uniqueConstraints =
@UniqueConstraint(columnNames = {"name"}))
@NamedQuery(name = "findAllTraitsSorted", query = "select object(o) from Trait as o order by name")
public class Trait implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @Column
   private String name;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final Trait other = (Trait) obj;
      if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
         return false;
      }
      if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
      hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
      return hash;
   }
}
