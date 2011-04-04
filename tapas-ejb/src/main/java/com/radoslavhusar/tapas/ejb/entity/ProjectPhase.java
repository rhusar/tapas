package com.radoslavhusar.tapas.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PROJECT_PHASE")
public class ProjectPhase implements Serializable, Comparable<ProjectPhase> {

   private static final long serialVersionUID = 3L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   @Column
   private String name;
   @ManyToOne(fetch = FetchType.LAZY)
   private Project project;
   /*
   // There is no need for a start date. The project is continuous, so the previous
   // end date is the next start date and the first start date is the start date of the
   // project itself.
   @Column
   @Temporal(TemporalType.DATE)
   private Date startDate;
    */
   @Column
   @Temporal(TemporalType.DATE)
   private Date targetted;
   @Column
   @Temporal(TemporalType.DATE)
   private Date ended;

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

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

   public Date getEnded() {
      return ended;
   }

   public void setEnded(Date endDate) {
      this.ended = endDate;
   }

   public Date getTargetted() {
      return targetted;
   }

   public void setTargetted(Date targetDate) {
      this.targetted = targetDate;
   }

   /**
    * Sorts by TARGET date.
    *
    * @return positive integer if this phase starts after the compared to.
    */
   @Override
   public int compareTo(ProjectPhase o) {
      if (o.getTargetted() == null || targetted == null) {
         // Really cant judge them
         return 0;
      }

      /*if (o.getTargetted().after(this.targetDate)) {
      return -1;
      } else {
      return 1;
      }*/

      // Need to cast to loss of precision
      return (int) (this.targetted.getTime() - o.getTargetted().getTime());
   }

   @Override
   public String toString() {
      return "ProjectPhase{id=" + id
              + ",name=" + name
              + ",project=" + (project == null ? "null" : project.getId())
              + ",targetted=" + (targetted == null ? "null" : targetted)
              + ",ended=" + (ended == null ? "null" : ended)
              + "}";
   }
}
