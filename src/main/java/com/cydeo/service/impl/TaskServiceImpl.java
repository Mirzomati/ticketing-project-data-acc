package com.cydeo.service.impl;

import com.cydeo.Repository.TaskRepository;
import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;


    public TaskServiceImpl(TaskRepository taskRepository, MapperUtil mapperUtil, UserService userService) {
        this.taskRepository = taskRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
    }

    @Override
    public TaskDTO findById(Long id) {

        Optional<Task> task = taskRepository.findById(id);

        if (task.isPresent()){
            return mapperUtil.convert(task.get(), TaskDTO.class);
        }

        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {

        List<TaskDTO> tasks = taskRepository.findAll()
                .stream()
                .map(task -> mapperUtil.convert(task, TaskDTO.class))
                .toList();

        return tasks;
    }

    @Override
    public void save(TaskDTO taskDTO) {
        Task task = mapperUtil.convert(taskDTO, Task.class);
        task.setTaskStatus(Status.OPEN);
        task.setAssignedDate(LocalDate.now());
        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO task) {

        Optional<Task> foundTask = taskRepository.findById(task.getId());

        Task convertedTask = mapperUtil.convert(task, Task.class);

        if (foundTask.isPresent()){

            convertedTask.setAssignedDate(foundTask.get().getAssignedDate());

            convertedTask.setTaskStatus(task.getTaskStatus() == null ? foundTask.get().getTaskStatus() : task.getTaskStatus());

            taskRepository.save(convertedTask);
        }

    }

    @Override
    public void delete(Long id) {

       Optional<Task> task = taskRepository.findById(id);

       if (task.isPresent()){
           task.get().setDeleted(true);
           taskRepository.save(task.get());
       }
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {

        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {

        return taskRepository.totalCompletedTask(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO dto) {

       List<Task> tasksToDelete = taskRepository.findByProject(mapperUtil.convert(dto, Project.class));

       tasksToDelete.forEach(task -> delete(task.getId()));

    }

    @Override
    public void completeByProject(ProjectDTO dto) {

        List<Task> tasksToComplete = taskRepository.findByProject(mapperUtil.convert(dto, Project.class));

        tasksToComplete.forEach(task -> {

            TaskDTO taskDTO = mapperUtil.convert(task, TaskDTO.class);

            taskDTO.setTaskStatus(Status.COMPLETE);

            update(taskDTO);
        });


    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {

        UserDTO loggedInUser = userService.findByUserName("john@employee.com");

        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, mapperUtil.convert(loggedInUser,User.class));

        return tasks.stream().map(task -> mapperUtil.convert(task, TaskDTO.class)).toList();
    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {

        UserDTO loggedInUser = userService.findByUserName("john@employee.com");

        List<Task> tasks = taskRepository.findAllByTaskStatusAndAssignedEmployee(status, mapperUtil.convert(loggedInUser,User.class));

        return tasks.stream().map(task -> mapperUtil.convert(task, TaskDTO.class)).toList();
    }

    @Override
    public List<TaskDTO> listAllNonCompletedByAssignedEmployee(UserDTO employee) {

        List<Task> tasks = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE, mapperUtil.convert(employee, User.class));

        return tasks.stream().map(task -> mapperUtil.convert(task, TaskDTO.class)).toList();
    }
}
