package com.example.productmanagementsystem.controllertests;

import com.example.productmanagementsystem.controllers.ProductController;
import com.example.productmanagementsystem.controllers.UserController;
import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.NewUserDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.dto.UserDto;
import com.example.productmanagementsystem.security.services.MyUserDetailsService;
import com.example.productmanagementsystem.services.ProductService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductService productService;
    @MockBean
    private MyUserDetailsService myUserDetailsService;

    @Test
    void whenNotAuthenticatedToViewAllProducts_thenReturns401() throws Exception {
        mockMvc.perform(get("/product/all"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenAuthenticatedAndAuthorizedToViewAllProducts_thenReturns200() throws Exception {
        mockMvc.perform(get("/product/all"))
                .andExpect(status().isOk());
    }

    @Test
    void whenNotAuthenticatedToViewOneProduct_thenReturns401() throws Exception {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);

        mockMvc.perform(get("/product/name")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenProductDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenAuthenticatedAndAuthorizedToViewOneProduct_thenReturns200() throws Exception {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);

        mockMvc.perform(get("/product/name")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenProductDto)))
                .andExpect(status().isOk());
    }

    @Test
    void whenNotAuthenticatedToAddProduct_thenReturns401() throws Exception {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);

        mockMvc.perform(post("/product/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenProductDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenAuthenticatedToAddProduct_thenReturns201() throws Exception {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);

        mockMvc.perform(post("/product/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenProductDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenNotAuthenticatedToEditProduct_thenReturns401() throws Exception {
        ChangedProductDto givenChangedProductDto=new ChangedProductDto("Android","iPhone","Mobile Phone",500f);

        mockMvc.perform(put("/product/edit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenChangedProductDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenAuthenticatedButNotAuthorizedToEditProduct_thenReturns403() throws Exception {
        ChangedProductDto givenChangedProductDto=new ChangedProductDto("Android","iPhone","Mobile Phone",500f);

        mockMvc.perform(put("/product/edit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenChangedProductDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAuthenticatedAndAuthorizedToEditProduct_thenReturns200() throws Exception {
        ChangedProductDto givenChangedProductDto=new ChangedProductDto("Android","iPhone","Mobile Phone",500f);

        mockMvc.perform(put("/product/edit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenChangedProductDto)))
                .andExpect(status().isOk());
    }

    @Test
    void whenNotAuthenticatedToDeleteProduct_thenReturns401() throws Exception {
        mockMvc.perform(delete("/product/delete/{productUuid}","productUuid"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenAuthenticatedButNotAuthorizedToDeleteProduct_thenReturns403() throws Exception {
        mockMvc.perform(delete("/product/delete/{productUuid}","productUuid"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAuthenticatedAndAuthorizedToDeleteProduct_thenReturns204() throws Exception {
        mockMvc.perform(delete("/product/delete/{productUuid}","527000"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void whenInputToGetProductByNameController_thenSameInputToService() throws Exception {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);

        mockMvc.perform(get("/product/name")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenProductDto)));

        ArgumentCaptor<ProductDto> newProductDtoArgumentCaptor = ArgumentCaptor.forClass(ProductDto.class);
        Mockito.verify(productService, times(1)).getProductByName(newProductDtoArgumentCaptor.capture());
        Assertions.assertEquals(newProductDtoArgumentCaptor.getValue().getName(),"iPhone");
        Assertions.assertEquals(newProductDtoArgumentCaptor.getValue().getDescription(),"Mobile Phone");
        Assertions.assertEquals(newProductDtoArgumentCaptor.getValue().getPrice(),500);
    }

    @Test
    @WithMockUser
    void whenInputToAddProductController_thenSameInputToService() throws Exception {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);

        mockMvc.perform(post("/product/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenProductDto)));

        ArgumentCaptor<ProductDto> newProductDtoArgumentCaptor = ArgumentCaptor.forClass(ProductDto.class);
        Mockito.verify(productService, times(1)).addProduct(newProductDtoArgumentCaptor.capture());
        Assertions.assertEquals(newProductDtoArgumentCaptor.getValue().getName(),"iPhone");
        Assertions.assertEquals(newProductDtoArgumentCaptor.getValue().getDescription(),"Mobile Phone");
        Assertions.assertEquals(newProductDtoArgumentCaptor.getValue().getPrice(),500);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenInputToEditProductController_thenSameInputToService() throws Exception {
        ChangedProductDto givenChangedProductDto=new ChangedProductDto("Android","iPhone","Mobile Phone",500f);

        mockMvc.perform(put("/product/edit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(givenChangedProductDto)));

        ArgumentCaptor<ChangedProductDto> newChangedProductDtoArgumentCaptor = ArgumentCaptor.forClass(ChangedProductDto.class);
        Mockito.verify(productService, times(1)).editProduct(newChangedProductDtoArgumentCaptor.capture());
        Assertions.assertEquals(newChangedProductDtoArgumentCaptor.getValue().getOldName(),"Android");
        Assertions.assertEquals(newChangedProductDtoArgumentCaptor.getValue().getNewName(),"iPhone");
        Assertions.assertEquals(newChangedProductDtoArgumentCaptor.getValue().getDescription(),"Mobile Phone");
        Assertions.assertEquals(newChangedProductDtoArgumentCaptor.getValue().getPrice(),500);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenInputToDeleteProductController_thenSameInputToService() throws Exception {
        mockMvc.perform(delete("/product/delete/{productUuid}","527000"));

        ArgumentCaptor<String> productUuidCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(productService, times(1)).deleteProduct(productUuidCaptor.capture());
        Assertions.assertEquals(productUuidCaptor.getValue(),"527000");
    }


}
