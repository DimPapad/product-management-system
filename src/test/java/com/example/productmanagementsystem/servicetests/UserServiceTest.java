package com.example.productmanagementsystem.servicetests;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.models.Role;
import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.repositories.RoleRepository;
import com.example.productmanagementsystem.repositories.UserRepository;
import com.example.productmanagementsystem.services.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
//    @InjectMocks
//    private SecurityContextHolder securityContextHolder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @InjectMocks
    private User user;
    @InjectMocks
    private Role role;

    @Test
    public void whenRegisterUser_thenReturnsUser() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","1234","1234");
        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_USER");
        given(userRepository.save(user)).willReturn(user);
        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.of(new Role("ROLE_USER")));
        UserDto actualUserDto=userServiceImpl.registerUser(givenNewUserDto);
        Assertions.assertEquals(expectedUserDto,actualUserDto);
    }

    @Test
    public void whenRegisterUserWithBlankValue_thenReturns404() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","","papdim@pmail.com","1234","1234");
        Exception exception=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Username is needed.\"",exception.getMessage());
    }

    @Test
    public void whenRegisterUserWithNotMatchingPasswords_thenReturns404() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","12345","1234");
        Exception exception=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Passwords do not match.\"",exception.getMessage());
    }

//    @Test
//    @WithMockUser(username = "mocker",roles = "ADMIN",setupBefore = TestExecutionEvent.TEST_METHOD)
//    public void whenLoggedInUser_thenReturnsUser() {
////        User user=new User("asd","asd","asd","mocker","asd","asd","asd","asd");
////        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","1234","1234");
////        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_USER");
//        given(userRepository.findByUsername("mocker")).willReturn(Optional.of(user));
//        given(securityContextHolder.getContext().getAuthentication().getName()).willReturn("mocker");
////        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.of(new Role("ROLE_USER")));
//        UserDto actualUserDto=userServiceImpl.loggedInUser();
//        Assertions.assertEquals("mocker",actualUserDto.getUsername());
//    }


}
