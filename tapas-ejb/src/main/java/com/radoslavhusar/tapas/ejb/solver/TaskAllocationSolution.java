package com.radoslavhusar.tapas.ejb.solver;

import com.radoslavhusar.tapas.ejb.entity.Resource;
import com.radoslavhusar.tapas.ejb.entity.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.drools.planner.core.score.HardAndSoftScore;
import org.drools.planner.core.score.Score;
import org.drools.planner.core.solution.Solution;

public class TaskAllocationSolution implements Solution {

   private HardAndSoftScore score;
   private final List<Task> tasks;
   private final List<Resource> resources;

   public TaskAllocationSolution(List<Task> tasks, List<Resource> resources) {
      this.tasks = tasks;
      this.resources = resources;
   }

   @Override
   public void setScore(Score score) {
      this.score = (HardAndSoftScore) score;
   }

   @Override
   public Collection<? extends Object> getFacts() {
      List facts = new ArrayList(tasks.size() + resources.size());
      facts.addAll(tasks);
      facts.addAll(resources);
      return facts;
   }

   @Override
   public Solution cloneSolution() {
      System.out.println("Cloning solution!");
      
      List<Task> clonedTaskList = new ArrayList<Task>(tasks.size());
      for (Task task : tasks) {
         Task clone;
         clone = task.droolsClone();
         clonedTaskList.add(clone);
      }

      clonedTaskList.addAll(tasks);
      List<Resource> clonedResources = new ArrayList<Resource>(resources.size());
      /*for (Resource res : resources) {
      Resource clone;
      clone = res.droolsClone();
      clone.setTasks(new ArrayList());
      // but let those clone reference copied tasks too
      for (Task task : clonedTaskList) {
      if (task.getResource().equals(res)) {
      clone.getTasks().add(task);
      }
      }
      clonedResources.add(clone);
      }*/
      clonedResources.addAll(resources);

      TaskAllocationSolution solutionClone = new TaskAllocationSolution(clonedTaskList, clonedResources);
      solutionClone.setScore(score);

      return solutionClone;
   }

   @Override
   public Score getScore() {
      return score;
   }

   public List<Resource> getResources() {
      return resources;
   }

   public List<Task> getTasks() {
      return tasks;
   }
}