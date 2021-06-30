package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarketDto;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.user.UserDto;
import com.cryptonite.cryptonite.mapper.UserMapper;
import com.cryptonite.cryptonite.repository.UserRepository;
import com.cryptonite.cryptonite.service.UserService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;


    @Test
    void shouldGetUsers() throws Exception{
        List<User> userList = new ArrayList<>();
        List<UserDto> userDtoList = new ArrayList<>();

        when(userMapper.mapToUserDtoList(userList)).thenReturn(userDtoList);
        when(userService.getUsers()).thenReturn(userDtoList);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetUser() throws Exception{
        User user = createUser();
        UserDto userDto = createUserDto();

        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        when(userService.getUserById(user.getId())).thenReturn(userDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@mail")));
    }

    @Test
    void shouldAddUser() throws Exception{
        User user = createUser();
        UserDto userDto = createUserDto();

        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void shouldDeleteUser() throws Exception{
        User user = createUser();
        UserDto userDto = createUserDto();

        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        when(userService.getUserById(user.getId())).thenReturn(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(204));
    }

    @Test
    void shouldUpdateUser() throws Exception{
        User user = createUser();
        UserDto userDto = createUserDto();

        when(userMapper.mapToUserDto(user)).thenReturn(userDto);
        when(userMapper.mapToUser(userDto)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(202));
    }

    private User createUser(){
        return User.builder()
                .id(1L)
                .username("test")
                .password("test password")
                .email("test@mail")
                .wallet(new BigDecimal(1000))
                .build();
    }

    private UserDto createUserDto(){
        return UserDto.builder()
                .id(1L)
                .username("test")
                .password("test password")
                .email("test@mail")
                .wallet(new BigDecimal(1000))
                .build();
    }
}
