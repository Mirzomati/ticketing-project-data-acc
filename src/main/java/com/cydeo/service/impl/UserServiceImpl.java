package com.cydeo.service.impl;

import com.cydeo.Repository.UserRepository;
import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> users = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);

        return users.stream().map(user -> mapperUtil.convert(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        return mapperUtil.convert(userRepository.findByUserNameAndIsDeleted(username, false), UserDTO.class);
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(mapperUtil.convert(user, User.class));
    }

    @Override
    public void update(UserDTO user) {

        User foundUser = userRepository.findByUserNameAndIsDeleted(user.getUserName(),false);

        User userUpdated = mapperUtil.convert(user, User.class);

        userUpdated.setId(foundUser.getId());


        userRepository.save(userUpdated);
    }

    @Override
    public void delete(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username,false);

        if(checkIfUserCanBeDeleted(mapperUtil.convert(user, UserDTO.class))){


            user.setDeleted(true);
            user.setUserName(user.getUserName()+ "-"+ user.getId());

            userRepository.save(user);
        }

    }

    private boolean checkIfUserCanBeDeleted(UserDTO user) {

        switch (user.getRole().getDescription()) {

            case "Manager":

                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(user);

                return projectDTOList.size() == 0;

            case "Employee":

                    List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(user);

                    return taskDTOList.size() == 0;

            default:
                return true;
        }
    }



    @Override
    public List<UserDTO> findAllUsersByRole(String role) {

        return userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false)
                .stream()
                .map(user -> mapperUtil.convert(user, UserDTO.class))
                .toList();
    }
}
