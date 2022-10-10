package unit.service;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.model.Products;
import com.hanaberia.model.Reservations;
import com.hanaberia.model.Users;
import com.hanaberia.repository.ReservationsRepository;
import com.hanaberia.service.ProductsService;
import com.hanaberia.service.ReservationsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unit.mock.ProductMock;
import unit.mock.ReservationMock;
import unit.mock.UserMock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class ReservationsServiceTest {

    @Mock
    private ReservationsRepository reservationsRepository;

    @Mock
    private ProductsService productsService;

    @InjectMocks
    private ReservationsService reservationsService;

    private final Reservations reservationOne = ReservationMock.mockedReservationOne();
    private final Users user = UserMock.mockedUserOne();
    private final Set<Products> products = ProductMock.productSet();
    private final List<Reservations> reservationsList = ReservationMock.reservationsList();

    @Test
    void createReservationTest(){
        when(reservationsRepository.save(any(Reservations.class))).thenReturn(reservationOne);

        Reservations createdReservation = reservationsService.create(reservationOne, user);
        assertEquals(createdReservation, reservationOne);

        verify(reservationsRepository).save(reservationOne);
    }

    @Test
    void readReservationTest(){
        when(reservationsRepository.findById(reservationOne.getId())).thenReturn(Optional.of(reservationOne));

        Reservations expectedReservation = reservationsService.read(reservationOne.getId());
        assertEquals(expectedReservation, reservationOne);

        verify(reservationsRepository).findById(reservationOne.getId());
    }

    @Test
    void readAllReservationsTest(){
        when(reservationsRepository.findAll()).thenReturn(reservationsList);

        List<Reservations> expectedReservations = reservationsService.readAllReservations();
        assertEquals(expectedReservations, reservationsList);

        verify(reservationsRepository).findAll();
    }

    @Test
    void updateReservationTest(){
        Reservations tempReservation = Reservations.builder().expiringDate(LocalDate.now()).build();
        when(reservationsRepository.findById(tempReservation.getId())).thenReturn(Optional.of(tempReservation));

        reservationsService.update(tempReservation.getId(), reservationOne);
        assertEquals(tempReservation.getUser(), reservationOne.getUser());

        verify(reservationsRepository).findById(tempReservation.getId());
        verify(reservationsRepository).save(tempReservation);
    }

    @Test
    void deleteReservationTest(){
        Reservations tempReservation = Reservations.builder().expiringDate(LocalDate.now()).build();
        tempReservation.setProductsSet(products);

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

        reservationsService.deleteEmptyReservation(tempReservation.getId());
        assertNull(tempReservation.getId());

        verify(reservationsRepository).deleteById(tempReservation.getId());
    }
}