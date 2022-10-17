package unit.service;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.model.Orders;
import com.hanaberia.model.Products;
import com.hanaberia.model.Users;
import com.hanaberia.repository.OrdersRepository;
import com.hanaberia.service.OrdersService;
import com.hanaberia.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unit.mock.OrderMock;
import unit.mock.ProductMock;
import unit.mock.UserMock;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class OrdersServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private OrdersService ordersService;

    private final Orders orderOne = OrderMock.mockedOrderNotCompleted();
    private final Orders orderTwo = OrderMock.mockedOrderCompleted();
    private final List<Orders> ordersList = OrderMock.ordersList();
    private final Users userOne = UserMock.mockedUserOne();
    private final Set<Products> products = ProductMock.productSet();

    @Test
    void findAllOrdersTest(){
        when(ordersRepository.findAll()).thenReturn(ordersList);

        List<Orders> expectedOrders = ordersService.getAllOrders();
        assertEquals(expectedOrders, ordersList);

        verify(ordersRepository).findAll();
    }

    @Test
    void createOrderTest(){
        when(ordersRepository.save(any(Orders.class))).thenReturn(orderOne);

        Orders createdOrder = ordersService.create(orderOne, userOne);
        assertEquals(createdOrder, orderOne);

        verify(ordersRepository).save(orderOne);
    }

    @Test
    void readOrderTest(){
        when(ordersRepository.findById(orderOne.getId())).thenReturn(Optional.of(orderOne));

        Orders expectedOrder = ordersService.read(orderOne.getId());
        assertEquals(expectedOrder, orderOne);

        verify(ordersRepository).findById(orderOne.getId());
    }

    @Test
    void completeOrderTest(){
        Orders tempOrder = Orders.builder().build();
        when(ordersRepository.findById(tempOrder.getId())).thenReturn(Optional.of(tempOrder));

        ordersService.complete(tempOrder.getId(), orderTwo);
        assertEquals(tempOrder.getCompletedDate(), orderTwo.getCompletedDate());
    }

    @Test
    void updateOrderTest(){
        Orders tempOrder = Orders.builder().build();
        when(ordersRepository.findById(tempOrder.getId())).thenReturn(Optional.of(tempOrder));

        ordersService.update(tempOrder.getId(), orderOne);
        assertEquals(tempOrder.getMessage(), orderOne.getMessage());

        verify(ordersRepository).findById(tempOrder.getId());
        verify(ordersRepository).save(tempOrder);
    }

    @Test
    void deleteOrderTest(){
        Orders tempOrder = Orders.builder()
                .productsSet(new HashSet<>())
                .build();

        when(ordersRepository.findById(tempOrder.getId())).thenReturn(Optional.of(tempOrder));

        ordersService.delete(tempOrder.getId());
        assertNull(tempOrder.getId());

        verify(ordersRepository).deleteById(tempOrder.getId());
    }

    @Test
    void deleteCompletedOrderTest(){
        Orders tempOrder = Orders.builder()
                .completedDate(LocalDate.now())
                .completed(true)
                .productsSet(products)
                .build();

        when(ordersRepository.findById(tempOrder.getId())).thenReturn(Optional.of(tempOrder));

        ordersService.deleteCompletedOrders(tempOrder.getId());
        assertNull(tempOrder.getId());

        verify(ordersRepository).deleteById(tempOrder.getId());
    }
}