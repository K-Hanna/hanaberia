package unit.service;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.model.Users;
import com.hanaberia.repository.UsersRepository;
import com.hanaberia.service.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unit.mock.UserMock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    private final Users userOne = UserMock.mockedUserOne();
    private final List<Users> usersList = UserMock.userList();

    @Test
    void findAllUsersTest(){
        when(usersRepository.findAll()).thenReturn(usersList);

        List<Users> expectedUsers = usersService.findAll();
        assertEquals(expectedUsers, usersList);

        verify(usersRepository).findAll();
    }

    @Test
    void createUserTest(){
        when(usersRepository.save(any(Users.class))).thenReturn(userOne);

        Users createdUser = usersService.create(userOne);
        assertEquals(createdUser, userOne);

        verify(usersRepository).save(userOne);
    }

    @Test
    void retrieveUserTest(){
        when(usersRepository.findById(userOne.getId())).thenReturn(Optional.of(userOne));

        Users expectedUser = usersService.retrieve(userOne.getId());
        assertEquals(expectedUser, userOne);

        verify(usersRepository).findById(userOne.getId());
    }

    @Test
    void retrieveByNameTest(){
        when(usersRepository.findUserByUserName(userOne.getUserName())).thenReturn(userOne);

        Users expectedUser = usersService.retrieveByName(userOne.getUserName());
        assertEquals(expectedUser, userOne);

        verify(usersRepository).findUserByUserName(userOne.getUserName());
    }

    @Test
    void updateUserTest(){

        Users tempUser = Users.builder().build();
        when(usersRepository.findById(tempUser.getId())).thenReturn(Optional.of(tempUser));

        usersService.update(tempUser.getId(), userOne);
        assertEquals(tempUser.getUserName(), userOne.getUserName());

        verify(usersRepository).findById(tempUser.getId());
        verify(usersRepository).save(tempUser);
    }

    @Test
    void deleteUserTest(){
        Users tempUser = Users.builder().build();
        when(usersRepository.findById(tempUser.getId())).thenReturn(Optional.of(tempUser));

        usersService.delete(tempUser.getId());
        assertNull(tempUser.getId());

        verify(usersRepository).findById(tempUser.getId());
        verify(usersRepository).deleteById(tempUser.getId());
    }

    @Test
    void userNameExistsTest(){
        when(usersRepository.findUserByUserName(userOne.getUserName())).thenReturn(userOne);

        assertTrue(usersService.userNameExists(userOne.getUserName()));
        assertFalse(usersService.userNameExists("Ania"));
        verify(usersRepository).findUserByUserName(userOne.getUserName());
    }

    @Test
    void contactExistsTest(){
        when(usersRepository.findUserByContact(userOne.getContact())).thenReturn(userOne);

        assertTrue(usersService.contactExists(userOne.getContact()));
        assertFalse(usersService.contactExists("111222333"));
        verify(usersRepository).findUserByContact(userOne.getContact());
    }
}
