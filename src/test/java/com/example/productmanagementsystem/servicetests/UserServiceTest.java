package com.example.productmanagementsystem.servicetests;

import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.models.Role;
import com.example.productmanagementsystem.models.User;
import com.example.productmanagementsystem.dto.UserProductDto;
import com.example.productmanagementsystem.repositories.ProductUserRepository;
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

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {


    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ProductUserRepository productUserRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private final User user=new User();
    private final Role role=new Role();

    @Test
    public void whenRegisterUser_thenReturnsUser() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","1234","1234");
        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_USER");
        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.of(new Role("ROLE_USER")));
        UserDto actualUserDto=userServiceImpl.registerUser(givenNewUserDto);
        Assertions.assertEquals(expectedUserDto,actualUserDto);
    }

    @Test
    public void whenRegisterUserWithBlankUsername_thenReturns400() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","  ","papdim@pmail.com","1234","1234");
        Exception blankUsername=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Username is needed.\"",blankUsername.getMessage());
    }

    @Test
    public void whenRegisterUserWithBlankEmail_thenReturns400() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","  ","1234","1234");
        Exception blankEmail=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Email is needed.\"",blankEmail.getMessage());
    }

    @Test
    public void whenRegisterUserWithBlankPassword_thenReturns400() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","  ","1234");
        Exception blankPassword=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Password is needed.\"",blankPassword.getMessage());
    }

    @Test
    public void whenRegisterUserWithNotMatchingPasswords_thenReturns400() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","12345","1234");
        Exception notMatchingPasswords=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Passwords do not match.\"",notMatchingPasswords.getMessage());
    }

    @Test
    public void whenRegisterUserWithEmailAlreadyExisting_thenReturns208() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","1234","1234");
        given(userRepository.findByEmail(givenNewUserDto.getEmail())).willReturn(Optional.of(user));
        Exception emailAlreadyExists=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("208 ALREADY_REPORTED \"Email already exists.\"",emailAlreadyExists.getMessage());
    }

    @Test
    public void whenRegisterUserWithUsernameAlreadyExisting_thenReturns208() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","1234","1234");
        given(userRepository.findByUsername(givenNewUserDto.getUsername())).willReturn(Optional.of(user));
        Exception usernameAlreadyExists=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("208 ALREADY_REPORTED \"Username already exists.\"",usernameAlreadyExists.getMessage());
    }

    @Test
    public void whenRegisterUserAndRoleUserDoesNotExist_thenReturns500() {
        NewUserDto givenNewUserDto=new NewUserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","1234","1234");
        given(roleRepository.findByName("ROLE_USER")).willReturn(Optional.empty());
        Exception notExistingRoleUser=Assertions.assertThrowsExactly(ResponseStatusException.class,()->userServiceImpl.registerUser(givenNewUserDto));
        Assertions.assertEquals("500 INTERNAL_SERVER_ERROR \"There is not ROLE_USER in the database, in order to be assigned to the new User.\"",notExistingRoleUser.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenChangeRole_thenReturnsUser() {
        UserDto givenAndExpectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_ADMIN");
        given(userRepository.findByUsername("PapDim")).willReturn(Optional.of(user));
        given(roleRepository.findByName("ROLE_ADMIN")).willReturn(Optional.of(role));
        UserDto actualUserDto=userServiceImpl.changeRole(givenAndExpectedUserDto);
        Assertions.assertEquals(givenAndExpectedUserDto,actualUserDto);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenChangeToRoleThatDoesNotExist_thenReturns400() {
        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_ADMIN");
        given(userRepository.findByUsername("PapDim")).willReturn(Optional.of(user));
        given(roleRepository.findByName("ROLE_ADMIN")).willReturn(Optional.empty());
        Exception roleException=Assertions.assertThrows(ResponseStatusException.class,()->userServiceImpl.changeRole(expectedUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Role not found!\"",roleException.getMessage());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenChangeRoleToNonExistingUser_thenReturns400() {
        UserDto expectedUserDto=new UserDto("Papadogiannakis","Dimitrios","PapDim","papdim@pmail.com","ROLE_ADMIN");
        given(userRepository.findByUsername("PapDim")).willReturn(Optional.empty());
        Exception userException=Assertions.assertThrows(ResponseStatusException.class,()->userServiceImpl.changeRole(expectedUserDto));
        Assertions.assertEquals("400 BAD_REQUEST \"User not found!\"",userException.getMessage());
    }

    @Test
    @WithMockUser
    public void whenAskUserAudit_thenReturnsUserProducts() {
        Assertions.assertTrue(userServiceImpl.getUserAudit("333") instanceof List<UserProductDto>);
    }


}
