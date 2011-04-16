package com.radoslavhusar.tapas.ejb.stats;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author <a href="mailto:me@radoslavhusar.com">Radoslav Husar</a>
 * @version 2011
 */
public class TaskAllocationPlanMeta implements Serializable {

   private long projectId;
   private Map<Long, Long> taskToResource;

   public TaskAllocationPlanMeta() {
   }

   public TaskAllocationPlanMeta(long projectId, Map<Long, Long> taskToResource) {
      this.projectId = projectId;
      this.taskToResource = taskToResource;
   }

   public long getProjectId() {
      return projectId;
   }

   public void setProjectId(long projectId) {
      this.projectId = projectId;
   }

   public Map<Long, Long> getTaskToResource() {
      return taskToResource;
   }

   public void setTaskToResource(Map<Long, Long> taskToResource) {
      this.taskToResource = taskToResource;
   }

   @Override
   public String toString() {
      return "TaskAllocationPlanMeta{projectId=" + projectId + ", taskToResource=" + taskToResource + '}';
   }
}
