package com.example.productmanagementsystem.controllertests;

import com.example.productmanagementsystem.controllers.UserController;
import com.example.productmanagementsystem.dto.NewUserDto;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

        ArgumentCaptor<NewUserDto> newUserDtoArgumentCaptor = ArgumentCaptor.forClass(NewUserDto.class);
        Mockito.verify(userService, times(1)).registerUser(newUserDtoArgumentCaptor.capture());
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getLastName(),"Papadogiannakis");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getFirstName(),"Dimitrios");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getUsername(),"PapDim");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getEmail(),"papdim@pmail.com");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getPassword(),"1234");
        Assertions.assertEquals(newUserDtoArgumentCaptor.getValue().getMatchingPassword(),"1234");
    }


}
