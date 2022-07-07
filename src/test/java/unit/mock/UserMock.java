package unit.mock;

import com.hanaberia.enums.ContactForms;
import com.hanaberia.enums.Roles;
import com.hanaberia.model.Users;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

public class UserMock {

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static Users mockedUserOne(){

        return Users.builder()
                .id(1L)
                .userName("Anna")
                .contactForm(ContactForms.EMAIL)
                .contact("anna@wp.pl")
                .password(bCryptPasswordEncoder.encode("12345"))
                .confirm(bCryptPasswordEncoder.encode("12345"))
                .roles(Roles.USER)
                .build();
    }

    public static Users mockedUserTwo(){

        return Users.builder()
                .id(2L)
                .userName("Adam")
                .contactForm(ContactForms.PHONE)
                .contact("123456789")
                .password(bCryptPasswordEncoder.encode("12345"))
                .confirm(bCryptPasswordEncoder.encode("12345"))
                .roles(Roles.USER)
                .build();
    }

    public static Users mockedUserThree(){

        return Users.builder()
                .id(3L)
                .userName("Anita")
                .contactForm(ContactForms.EMAIL)
                .contact("anita@wp.pl")
                .password(bCryptPasswordEncoder.encode("12345"))
                .confirm(bCryptPasswordEncoder.encode("12345"))
                .roles(Roles.ADMIN)
                .build();
    }

    public static List<Users> userList(){
        return Arrays.asList(mockedUserOne(), mockedUserTwo(), mockedUserThree());
    }
}