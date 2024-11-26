package com.cydeo.Repository;


import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;

import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT COUNT(t) FROM Task t WHERE t.taskStatus <> 'COMPLETE'  AND t.project.projectCode = ?1")
    int totalNonCompletedTasks(String projectCode);

    @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p ON t.project_id = p.id " +
            " WHERE t.task_status = 'COMPLETE' AND p.project_code = ?1", nativeQuery = true)
    int totalCompletedTask(String projectCode);


    List<Task> findByProject(Project project);

    List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User employee);

    List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User convert);
}