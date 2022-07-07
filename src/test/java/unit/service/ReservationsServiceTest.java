package unit.service;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.repository.ReservationsRepository;
import com.hanaberia.service.ReservationsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unit.mock.ReservationMock;
import unit.mock.UserMock;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class ReservationsServiceTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @InjectMocks
    private ReservationsService reservationsService;

    private final Reservations reservationOne = ReservationMock.mockedReservationOne();
    private final Users user = UserMock.mockedUserOne();

    @Test
    void createReservationTest(){
        when(reservationsRepository.save(any(Reservations.class))).thenReturn(reservationOne);

        Reservations createdReservation = reservationsService.create(reservationOne, user);
        assertEquals(createdReservation, reservationOne);

        verify(reservationsRepository).save(reservationOne);
    }

    @Test
    void retrieveReservationTest(){
        when(reservationsRepository.findById(reservationOne.getId())).thenReturn(Optional.of(reservationOne));

        Reservations expectedReservation = reservationsService.retrieve(reservationOne.getId());
        assertEquals(expectedReservation, reservationOne);

        verify(reservationsRepository).findById(reservationOne.getId());
    }

    @Test
    void deleteReservationTest(){
        Reservations tempReservation = Reservations.builder().expiringDate(LocalDate.now()).build();
        when(reservationsRepository.findById(tempReservation.getId())).thenReturn(Optional.of(tempReservation));

        reservationsService.delete(tempReservation.getId());
        assertNull(tempReservation.getId());

        verify(reservationsRepository).findById(tempReservation.getId());
        verify(reservationsRepository).deleteById(tempReservation.getId());
    }

    @Test
    void deleteEmptyReservationTest(){
        Reservations tempReservation = Reservations.builder().build();
        when(reservationsRepository.findById(tempReservation.getId())).thenReturn(Optional.of(tempReservation));

        reservationsService.deleteEmptyReservation(tempReservation);
        assertNull(tempReservation.getId());

        verify(reservationsRepository).delete(tempReservation);
    }
}