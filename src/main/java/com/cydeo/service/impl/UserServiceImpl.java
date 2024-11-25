package com.cydeo.service.impl;

import com.cydeo.Repository.UserRepository;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> mapperUtil.convert(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        return mapperUtil.convert(userRepository.findByUserName(username), UserDTO.class);
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(mapperUtil.convert(user, User.class));
    }

    @Override
    public void update(UserDTO user) {

        User foundUser = userRepository.findByUserName(user.getUserName());

        User userUpdated = mapperUtil.convert(user, User.class);

        userUpdated.setId(foundUser.getId());


        userRepository.save(userUpdated);
    }

    @Override
    public void delete(String username) {

       User deletedUser = userRepository.findByUserName(username);
       deletedUser.setDeleted(true);
       deletedUser.setUserName(deletedUser.getUserName()+ "-"+ deletedUser.getId());

       userRepository.save(deletedUser);



    }
}
