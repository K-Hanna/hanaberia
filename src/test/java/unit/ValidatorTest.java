package unit;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.validator.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HanaberiaApplication.class)
public class ValidatorTest {

    private final Validator validator = new Validator();
    private boolean isValid;

    @Test
    void invalidUserName(){
        isValid = validator.isUserNameValid("Ala ma kota.");
        assertFalse(isValid);
    }

    @Test
    void validUserName(){
        isValid = validator.isUserNameValid("Anna");
        assertTrue(isValid);
    }
    @Test
    void invalidEmail(){
        isValid = validator.isEmailValid("aaa");
        assertFalse(isValid);
    }

    @Test
    void validEmail(){
        isValid = validator.isEmailValid("aaa@wp.pl");
        assertTrue(isValid);
    }

    @Test
    void invalidPhone(){
        isValid = validator.isPhoneValid("aaa");
        assertFalse(isValid);
    }

    @Test
    void validPhone(){
        isValid = validator.isPhoneValid("123456789");
        assertTrue(isValid);
    }

    @Test
    void notMatchingPasswords(){
        isValid = validator.arePasswordsMatching("aaa", "bbb");
        assertFalse(isValid);
    }

    @Test
    void matchingPasswords(){
        isValid = validator.arePasswordsMatching("aaa", "aaa");
        assertTrue(isValid);
    }
}