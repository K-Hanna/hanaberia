package unit.mock;

import com.hanaberia.model.Reservations;

import java.time.LocalDate;

public class ReservationMock {

    public static Reservations mockedReservationOne(){
        return Reservations.builder()
                .id(7L)
                .expiringDate(LocalDate.now())
                .user(UserMock.mockedUserOne())
                .build();
    }
}
