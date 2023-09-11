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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

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
//        given(userRepository.save(user)).willReturn(user);
        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.of(new Role("ROLE_USER")));
        UserDto actualUserDto=userServiceImpl.registerUser(givenNewUserDto);
        Assertions.assertEquals(expectedUserDto,actualUserDto);
    }

    @Test
    public void whenRegisterUserWithBlankValue_thenReturns400() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","","papdim@pmail.com","1234","1234");
        Exception exception=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Username is needed.\"",exception.getMessage());
    }

    @Test
    public void whenRegisterUserWithNotMatchingPasswords_thenReturns400() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","12345","1234");
        Exception notMatchingPasswords=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Passwords do not match.\"",notMatchingPasswords.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenChangeRole_thenReturnsUser() {
        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_ADMIN");
        given(userRepository.findByUsername("PapDim")).willReturn(Optional.of(user));
        given(roleRepository.findByName("ROLE_ADMIN")).willReturn(Optional.of(role));
        UserDto actualUserDto=userServiceImpl.changeRole(expectedUserDto);
        Assertions.assertEquals(expectedUserDto,actualUserDto);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenChangeToRoleThatDoesNotExist_thenReturns400() {
        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_ADMIN");
        given(userRepository.findByUsername("PapDim")).willReturn(Optional.of(user));
        Exception roleException=Assertions.assertThrows(ResponseStatusException.class,()->userServiceImpl.changeRole(expectedUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Role not found!\"",roleException.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenChangeRoleToNonExistingUser_thenReturns400() {
        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_ADMIN");
        Exception userException=Assertions.assertThrows(ResponseStatusException.class,()->userServiceImpl.changeRole(expectedUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"User not found!\"",userException.getMessage());
    }


}
