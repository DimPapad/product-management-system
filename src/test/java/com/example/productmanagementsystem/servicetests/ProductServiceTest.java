package com.example.productmanagementsystem.servicetests;

import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.models.Product;
import com.example.productmanagementsystem.repositories.ProductRepository;
import com.example.productmanagementsystem.services.ProductServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    private final Product product=new Product();
    private final ArrayList<Product> products=new ArrayList<>();

    @Test
    public void whenAskProducts_thenReturnsProducts() {
        products.add(new Product());
        given(productRepository.findAll()).willReturn(products);
        Assertions.assertTrue(productServiceImpl.getAllProducts() instanceof List<ProductDto>);
    }

    @Test
    public void whenAskProductsAndThereIsNone_thenReturns204() {
        given(productRepository.findAll()).willReturn(products);
        Exception noProductsException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.getAllProducts());
        Assertions.assertEquals("204 NO_CONTENT \"No products found\"",noProductsException.getMessage());
    }

    @Test
    public void whenAskProduct_thenReturnsProduct() {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);
        given(productRepository.findByName(givenProductDto.getName())).willReturn(Optional.of(product));
        ProductDto actualProductDto=productServiceImpl.getProductByName(givenProductDto);
        Assertions.assertEquals(givenProductDto,actualProductDto);
    }

    @Test
    public void whenAskProductThatDoesNotExist_thenReturns204() {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);
        Exception noProductException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.getProductByName(givenProductDto));
        Assertions.assertEquals("204 NO_CONTENT \"Product not found!\"",noProductException.getMessage());
    }

    @Test
    @WithMockUser
    public void whenAddProductWithABlankField_thenReturns400() {
        ProductDto givenProductDto=new ProductDto("","Mobile Phone",500f);
        Exception blankFieldException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.addProduct(givenProductDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Product name is needed.\"",blankFieldException.getMessage());
    }

    @Test
    @WithMockUser
    public void whenAddProductThatAlreadyExists_thenReturns208() {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);
        given(productRepository.findByName(givenProductDto.getName())).willReturn(Optional.of(product));
        Exception alreadyReportedException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.addProduct(givenProductDto));
        Assertions.assertEquals("208 ALREADY_REPORTED \"Product already exists.\"",alreadyReportedException.getMessage());
    }

    @Test
    public void whenEditProductThatDoesNotExist_thenReturns400() {
        ChangedProductDto givenChangedProductDto=new ChangedProductDto("Android","iPhone","Mobile Phone",500f);

        Exception noProductException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.editProduct(givenChangedProductDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Product not found!\"",noProductException.getMessage());
    }

    @Test
    public void whenDeleteProductThatDoesNotExist_thenReturns400() {
        Exception noProductException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.deleteProduct("no_name"));
        Assertions.assertEquals("400 BAD_REQUEST \"Product not found!\"",noProductException.getMessage());
    }


}
