package unit.service;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.enums.Categories;
import com.hanaberia.model.Orders;
import com.hanaberia.model.Products;
import com.hanaberia.model.Reservations;
import com.hanaberia.repository.ProductsRepository;
import com.hanaberia.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unit.mock.OrderMock;
import unit.mock.ProductMock;
import unit.mock.ReservationMock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class ProductsServiceTest {

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private final Products productOne = ProductMock.mockedProductOne();
    private final List<Products> productsList = ProductMock.productList();

    private final Reservations reservation = ReservationMock.mockedReservationOne();
    private final Orders order = OrderMock.mockedOrderNotCompleted();

    @Test
    void findAllProductsTest(){
        when(productsRepository.findAll()).thenReturn(productsList);

        List<Products> expectedProducts = productsService.findAll();
        assertEquals(expectedProducts, productsList);

        verify(productsRepository).findAll();
    }

    @Test
    void createProductTest(){
        when(productsRepository.save(any(Products.class))).thenReturn(productOne);
        
        Products createdProduct = productsService.create(productOne);
        assertEquals(createdProduct, productOne);

        verify(productsRepository).save(productOne);
    }

    @Test
    void retrieveProductTest(){
        when(productsRepository.findById(productOne.getId())).thenReturn(Optional.of(productOne));

        Products expectedProduct = productsService.retrieve(productOne.getId());
        assertEquals(expectedProduct, productOne);

        verify(productsRepository).findById(productOne.getId());
    }

    @Test
    void updateProductTest(){

        Products tempProduct = Products.builder().build();
        when(productsRepository.findById(tempProduct.getId())).thenReturn(Optional.of(tempProduct));

        productsService.update(tempProduct.getId(), productOne);
        assertEquals(tempProduct.getName(), productOne.getName());

        verify(productsRepository).findById(tempProduct.getId());
        verify(productsRepository).save(tempProduct);
    }

    @Test
    void deleteProductTest(){
        Products tempProduct = Products.builder().build();
        when(productsRepository.findById(tempProduct.getId())).thenReturn(Optional.of(tempProduct));

        productsService.delete(tempProduct.getId());
        assertNull(tempProduct.getId());

        verify(productsRepository).deleteById(tempProduct.getId());
    }

    @Test
    void moveToReserved(){
        Products tempProduct = Products.builder().build();
        when(productsRepository.findById(tempProduct.getId())).thenReturn(Optional.of(tempProduct));

        productsService.moveProduct(false, tempProduct, reservation, null);
        assertEquals(tempProduct.getReservation(), reservation);
        assertFalse(tempProduct.isAvailable());
    }

    @Test
    void moveToOrdered(){
        Products tempProduct = Products.builder().build();
        when(productsRepository.findById(tempProduct.getId())).thenReturn(Optional.of(tempProduct));

        productsService.moveProduct(false, tempProduct, null, order);
        assertEquals(tempProduct.getOrder(), order);
        assertFalse(tempProduct.isAvailable());
    }

    @Test
    void getUnavailableProductsTest(){
        when(productsRepository.findByAvailableFalse()).thenReturn(productsList);

        List<Products> expectedProducts = productsService.getUnavailableProducts();
        assertEquals(expectedProducts, productsList);

        verify(productsRepository).findByAvailableFalse();
    }

    @Test
    void getProductsByCategoryTest(){
        when(productsRepository.findByCategory(Categories.BRACELET)).thenReturn(productsList);

        List<Products> expectedProducts = productsService.getByCategory(Categories.BRACELET);
        assertEquals(expectedProducts, productsList);

        verify(productsRepository).findByCategory(Categories.BRACELET);
    }
}
