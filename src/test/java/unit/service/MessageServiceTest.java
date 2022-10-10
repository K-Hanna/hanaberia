package unit.service;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.model.Messages;
import com.hanaberia.repository.MessagesRepository;
import com.hanaberia.service.MessagesService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unit.mock.MessageMock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class MessageServiceTest {

    @Mock
    private MessagesRepository messagesRepository;

    @InjectMocks
    private MessagesService messagesService;

    private final Messages messageOne = MessageMock.messageOne();
    private final List<Messages> messagesList = MessageMock.messagesList();

    @Test
    void getAllMessagesTest(){
        when(messagesRepository.findAll()).thenReturn(messagesList);

        List<Messages> expectedMessages = messagesRepository.findAll();
        assertEquals(expectedMessages, messagesList);

        verify(messagesRepository).findAll();
    }

    @Test
    void createMessageTest(){
        when(messagesRepository.save(any(Messages.class))).thenReturn(messageOne);

        Messages createdMessage = messagesService.create(messageOne);
        assertEquals(createdMessage, messageOne);

        verify(messagesRepository).save(messageOne);
    }

    @Test
    void retrieveMessageTest(){
        when(messagesRepository.findById(messageOne.getId())).thenReturn(Optional.of(messageOne));

        Messages retrievedMessage = messagesService.retrieve(messageOne.getId());
        assertEquals(retrievedMessage, messageOne);

        verify(messagesRepository).findById(messageOne.getId());
    }

    @Test
    void updateMessageTest(){
        Messages tempMessage = Messages.builder().build();
        when(messagesRepository.findById(tempMessage.getId())).thenReturn(Optional.of(tempMessage));

        messagesService.update(tempMessage.getId(), messageOne);
        assertEquals(tempMessage.getContent(), messageOne.getContent());

        verify(messagesRepository).findById(tempMessage.getId());
        verify(messagesRepository).save(tempMessage);
    }

    @Test
    void deleteMessageTest(){
        Messages tempMessage = Messages.builder().build();
        when(messagesRepository.findById(tempMessage.getId())).thenReturn(Optional.of(tempMessage));

        messagesService.delete(tempMessage.getId());
        assertNull(tempMessage.getId());

        verify(messagesRepository).deleteById(tempMessage.getId());
    }
}
