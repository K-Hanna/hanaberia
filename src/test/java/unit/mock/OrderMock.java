package unit.mock;

import com.hanaberia.model.Orders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OrderMock {

    public static Orders mockedOrderNotCompleted(){
        return Orders.builder()
                .id(8L)
                .completed(false)
                .message("message")
                .user(UserMock.mockedUserOne())
                .build();
    }

    public static Orders mockedOrderCompleted(){
        return Orders.builder()
                .id(9L)
                .completed(true)
                .completedDate(LocalDate.now())
                .message("message")
                .user(UserMock.mockedUserOne())
                .build();
    }

    public static List<Orders> ordersList(){
        return Arrays.asList(mockedOrderNotCompleted(), mockedOrderCompleted());
    }
}
