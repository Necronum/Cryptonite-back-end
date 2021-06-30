package com.cryptonite.cryptonite.service;

import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.user.UserDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.mapper.UserMapper;
import com.cryptonite.cryptonite.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getUsers(){
        List<User> users = userRepository.findAll();
        return userMapper.mapToUserDtoList(users);
    }

    public UserDto getUserById(Long id){
        return userRepository.findById(id)
                .map(userMapper::mapToUserDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
    }

    public void addUser(UserDto userDto){
        User user = userMapper.mapToUser(userDto);
        userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public UserDto updateUser(Long id, UserDto userDto){
        return userRepository.findById(id)
                .map(user -> {
                    User updatedUser = userMapper.mapToUser(userDto);
                    userRepository.save(updatedUser);
                    return userMapper.mapToUserDto(updatedUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
    }
}
