package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE_GROUP")
public class ResourceGroup implements Serializable {

   private static final long serialVersionUID = 7440297955003302414L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   @Column
   private String name;
   @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
   private List<Resource> resources;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @SuppressWarnings("ReturnOfCollectionOrArrayField")
   public List<Resource> getResources() {
      return resources;
   }

   public void setResources(List<Resource> resources) {
      this.resources = resources;
   }
}
