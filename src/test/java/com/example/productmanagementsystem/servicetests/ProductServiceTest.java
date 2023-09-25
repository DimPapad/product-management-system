package com.example.productmanagementsystem.servicetests;

import com.example.productmanagementsystem.dto.ChangedProductDto;
import com.example.productmanagementsystem.dto.ProductDto;
import com.example.productmanagementsystem.models.Product;
import com.example.productmanagementsystem.dto.ProductUserDto;
import com.example.productmanagementsystem.repositories.ProductRepository;
import com.example.productmanagementsystem.repositories.ProductUserRepository;
import com.example.productmanagementsystem.services.ProductServiceImpl;
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
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductUserRepository productUserRepository;
    @InjectMocks
    private ProductServiceImpl productServiceImpl;
    private final Product product=new Product();

    @Test
    public void whenAskProducts_thenReturnsProducts() {
        Assertions.assertTrue(productServiceImpl.getAllProducts() instanceof List<ProductDto>);
    }

    @Test
    public void whenAskProduct_thenReturnsProduct() {
        ProductDto givenAndExpectedProductDto=new ProductDto("iPhone","Mobile Phone",500f);
        given(productRepository.findByName(givenAndExpectedProductDto.getName())).willReturn(Optional.of(product));
        ProductDto actualProductDto=productServiceImpl.getProductByName(givenAndExpectedProductDto);
        Assertions.assertEquals(givenAndExpectedProductDto,actualProductDto);
    }

    @Test
    public void whenAskProductThatDoesNotExist_thenReturns204() {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",500f);
        Exception noProductException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.getProductByName(givenProductDto));
        Assertions.assertEquals("400 BAD_REQUEST \"There is no product with this name.\"",noProductException.getMessage());
    }

    @Test
    @WithMockUser
    public void whenAddProductWithBlankName_thenReturns400() {
        ProductDto givenProductDto=new ProductDto("  ","Mobile Phone",500f);
        Exception blankNameException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.addProduct(givenProductDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Product name is needed.\"",blankNameException.getMessage());
    }

    @Test
    @WithMockUser
    public void whenAddProductWithBlankDescription_thenReturns400() {
        ProductDto givenProductDto=new ProductDto("iPhone","  ",500f);
        Exception blankDescriptionException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.addProduct(givenProductDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Product description is needed.\"",blankDescriptionException.getMessage());
    }

    @Test
    @WithMockUser
    public void whenAddProductWithBlankPrice_thenReturns400() {
        ProductDto givenProductDto=new ProductDto();
        givenProductDto.setName("iPhone");
        givenProductDto.setDescription("Mobile Phone");
        Exception invalidPriceException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.addProduct(givenProductDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Product price is needed and should be valid.\"",invalidPriceException.getMessage());
    }

    @Test
    @WithMockUser
    public void whenAddProductWithInvalidPrice_thenReturns400() {
        ProductDto givenProductDto=new ProductDto("iPhone","Mobile Phone",-5f);
        Exception invalidPriceException=Assertions.assertThrows(ResponseStatusException.class,()->productServiceImpl.addProduct(givenProductDto));
        Assertions.assertEquals("400 BAD_REQUEST \"Product price is needed and should be valid.\"",invalidPriceException.getMessage());
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

    @Test
    @WithMockUser
    public void whenAskProductAudit_thenReturnsProductUsers() {

        Assertions.assertTrue(productServiceImpl.getProductAudit("333") instanceof List<ProductUserDto>);
    }


}
