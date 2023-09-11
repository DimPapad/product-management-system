package com.example.productmanagementsystem.controllertests;

import com.example.productmanagementsystem.controllers.UserController;
import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.security.services.MyUserDetailsService;
import com.example.productmanagementsystem.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.jboss.logging.MDC.get;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @Test
    void whenValidInput_thenReturns201() throws Exception {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","1234","1234");

        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenNewUserDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenInputToRegisterController_thenSameInputToService() throws Exception {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios",null,"papdim@pmail.com","1234","12345");

        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenNewUserDto)));

        ArgumentCaptor<NewUserDto> newUserDtoArgumentCaptor = ArgumentCaptor.forClass(NewUserDto.class);
        Mockito.verify(userService, times(1)).registerUser(newUserDtoArgumentCaptor.capture());
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getLastName(),"Papadogiannakis");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getFirstName(),"Dimitrios");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getUsername(),null);
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getEmail(),"papdim@pmail.com");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getPassword(),"1234");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getMatchingPassword(),"12345");
    }

    @Test
    void whenLoginPage_thenReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/login"))
                .andExpect(status().isOk());
    }

    @Test
    void whenHomePageWithoutCredentials_thenReturns401() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/home"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenHomePageWithCredentials_thenReturns200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/home"))
                .andExpect(status().isOk());
    }

    @Test
    void whenChangeRoleWithoutCredentials_thenReturns401() throws Exception {
        UserDto givenUserDto=new UserDto("PapDim","ROLE_ADMIN");
        mockMvc.perform(put("/user/changerole").contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenUserDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenChangeRoleWithUserCredentials_thenReturns403() throws Exception {
        UserDto givenUserDto=new UserDto("PapDim","ROLE_ADMIN");
        mockMvc.perform(put("/user/changerole").contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenUserDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenChangeRoleWithUserCredentials_thenReturns200() throws Exception {
        UserDto givenUserDto=new UserDto("PapDim","ROLE_ADMIN");
        mockMvc.perform(put("/user/changerole").contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenUserDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenInputToChangeRoleController_thenSameInputToService() throws Exception {
        UserDto givenUserDto=new UserDto("PapDim","ROLE_ADMIN");

        mockMvc.perform(put("/user/changerole")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(givenUserDto)));

        ArgumentCaptor<UserDto> userDtoArgumentCaptor = ArgumentCaptor.forClass(UserDto.class);
        Mockito.verify(userService, times(1)).changeRole(userDtoArgumentCaptor.capture());
        Assertions.assertEquals(userDtoArgumentCaptor.getValue().getUsername(),"PapDim");
        Assertions.assertEquals(userDtoArgumentCaptor.getValue().getRole(),"ROLE_ADMIN");
    }


}
