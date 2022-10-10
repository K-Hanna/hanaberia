package unit.mock;

import com.hanaberia.model.Reservations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReservationMock {

    public static Reservations mockedReservationOne(){
        return Reservations.builder()
                .id(7L)
                .expiringDate(LocalDate.now())
                .user(UserMock.mockedUserOne())
                .productsSet(ProductMock.productSet())
                .build();
    }

    public static Reservations mockedReservationTwo(){
        return Reservations.builder()
                .id(8L)
                .expiringDate(LocalDate.now())
                .user(UserMock.mockedUserTwo())
                .build();
    }

    public static Reservations mockedReservationThree(){
        return Reservations.builder()
                .id(9L)
                .expiringDate(LocalDate.now())
                .user(UserMock.mockedUserOne())
                .build();
    }

    public static List<Reservations> reservationsList(){
        return Arrays.asList(mockedReservationOne(), mockedReservationTwo(), mockedReservationThree());
    }
}