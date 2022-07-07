package unit;

import com.hanaberia.HanaberiaApplication;
import com.hanaberia.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = HanaberiaApplication.class)
public class SmokeTest {

    @Autowired
    private HomeController controller;

    @Test
    public void contextLoads() throws Exception{
        assertThat(controller).isNotNull();
    }
}
