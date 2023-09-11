package com.example.productmanagementsystem;

import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.repositories.ProductRepository;
import com.example.productmanagementsystem.repositories.ProductUserRepository;
import com.example.productmanagementsystem.repositories.RoleRepository;
import com.example.productmanagementsystem.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.jboss.logging.MDC.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
// (
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//        classes = ProductManagementSystemApplication.class)
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application.yml")
class ProductManagementSystemApplicationTests {


//    private final MockMvc mockMvc;
//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
//    private final RoleRepository roleRepository;
//    private final ProductUserRepository productUserRepository;
//
//    public ProductManagementSystemApplicationTests(MockMvc mockMvc, UserRepository userRepository, ProductRepository productRepository, RoleRepository roleRepository, ProductUserRepository productUserRepository) {
//        this.mockMvc=mockMvc;
//        this.userRepository=userRepository;
//        this.productRepository=productRepository;
//        this.roleRepository=roleRepository;
//        this.productUserRepository=productUserRepository;
//    }
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
//
//        createTestProduct("bob");
//
//        mockMvc.perform((RequestBuilder)get("/product/name")).andDo((ResultHandler) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$[0].name", is("Queen")));
//    }
//
//    private void createTestProduct(String name) {
//        ProductDto productDto=new ProductDto();
//        productDto.setName("Queen");
//    }

}
