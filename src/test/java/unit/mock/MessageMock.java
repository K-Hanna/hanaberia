package unit.mock;

import com.hanaberia.enums.ContactForms;
import com.hanaberia.model.Messages;

import java.sql.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class MessageMock {

    public static Messages messageOne(){
        return Messages.builder()
                .id(1L)
                .contactForm(ContactForms.EMAIL)
                .contact("aaa@wp.pl")
                .title("Title")
                .content("content")
                .sentDate(LocalDate.parse("2022-10-09"))
                .isRead(false)
                .build();
    }

    public static Messages messageTwo(){
        return Messages.builder()
                .id(1L)
                .contactForm(ContactForms.PHONE)
                .contact("123456789")
                .title("Title")
                .content("content")
                .sentDate(LocalDate.parse("2022-10-08"))
                .isRead(false)
                .build();
    }

    public static Messages messageThree(){
        return Messages.builder()
                .id(1L)
                .contactForm(ContactForms.EMAIL)
                .contact("aaa@wp.pl")
                .title("Title")
                .content("content")
                .sentDate(LocalDate.parse("2022-10-07"))
                .isRead(true)
                .build();
    }

    public static List<Messages> messagesList(){
        return Arrays.asList(messageOne(), messageTwo(), messageThree());
    }
}
