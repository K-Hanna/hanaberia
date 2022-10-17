package unit.mock;

import com.hanaberia.model.Reservations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ReservationMock {

    public static Reservations mockedReservationOne(){
        return Reservations.builder()
                .id(7L)
                .expiringDate(LocalDate.parse("2022-12-12"))
                .build();
    }

    public static Reservations mockedReservationTwo(){
        return Reservations.builder()
                .id(8L)
                .expiringDate(LocalDate.parse("2022-12-12"))
                .build();
    }

    public static Reservations mockedReservationThree(){
        return Reservations.builder()
                .id(9L)
                .expiringDate(LocalDate.parse("2022-12-12"))
                .build();
    }

    public static List<Reservations> reservationsList(){
        return Arrays.asList(mockedReservationOne(), mockedReservationTwo(), mockedReservationThree());
    }
}