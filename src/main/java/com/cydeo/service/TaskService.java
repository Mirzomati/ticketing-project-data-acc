package com.cydeo.service;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Project;

import java.util.List;

public interface TaskService {

    TaskDTO findById(Long id);

    List<TaskDTO> listAllTasks();

    void save(TaskDTO task);

    void update(TaskDTO task);

    void delete(Long id);

    int totalNonCompletedTask(String projectCode);

    int totalCompletedTask(String projectCode);

    void deleteByProject(ProjectDTO dto);

    void completeByProject(ProjectDTO project);
}
