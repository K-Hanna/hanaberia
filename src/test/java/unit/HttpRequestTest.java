package unit;

import com.hanaberia.HanaberiaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = HanaberiaApplication.class)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void greetingShouldBeVisible() throws Exception{
        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("Hanaberia");
    }
}
