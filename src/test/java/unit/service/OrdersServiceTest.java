package unit.service;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.model.Orders;
import com.hanaberia.repository.OrdersRepository;
import com.hanaberia.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unit.mock.OrderMock;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class OrdersServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrdersService ordersService;

    private final Orders orderOne = OrderMock.mockedOrderNotCompleted();
    private final Orders orderTwo = OrderMock.mockedOrderCompleted();
    private final List<Orders> ordersList = OrderMock.ordersList();

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

        Orders createdOrder = ordersService.create(orderOne);
        assertEquals(createdOrder, orderOne);

        verify(ordersRepository).save(orderOne);
    }

    @Test
    void retrieveOrderTest(){
        when(ordersRepository.findById(orderOne.getId())).thenReturn(Optional.of(orderOne));

        Orders expectedOrder = ordersService.retrieve(orderOne.getId());
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
    void deleteOrderTest(){
        Orders tempOrder = Orders.builder().build();
        tempOrder.setProductsSet(new HashSet<>());
        when(ordersRepository.findById(tempOrder.getId())).thenReturn(Optional.of(tempOrder));

        ordersService.delete(tempOrder.getId());
        assertNull(tempOrder.getId());

        verify(ordersRepository).deleteById(tempOrder.getId());
    }
}
