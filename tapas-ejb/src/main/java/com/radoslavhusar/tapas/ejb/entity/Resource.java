package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RESOURCE")
public class Resource implements Serializable {

   private static final long serialVersionUID = 1L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
//   @OneToMany(mappedBy = "assignee")
//   private List<Task> tasks;

   public Long getId() {
      return id;
   }

//   public void setId(Long id) {
//      this.id = id;
//   }
//
//   public List<Task> getTasks() {
//      return Collections.unmodifiableList(tasks);
//   }
}
