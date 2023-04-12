package htrcak.backend.core.issues;

import htrcak.backend.UtilsTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IssueControllerTest {

    static private String accessTokenAdmin;
    static private String accessTokenRegular;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    @Order(1)
    static void initialiseTest() {
        accessTokenAdmin = UtilsTest.generateAdminAccessToken();
        accessTokenRegular = UtilsTest.generateRegularAccessToken();
    }

    @Test
    @Order(10)
    void findAllIssues() throws Exception {
        this.mockMvc.perform(
                get("/issues")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

}
