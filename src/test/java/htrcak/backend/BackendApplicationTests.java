package htrcak.backend;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Order(1)
@ActiveProfiles("test")
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
