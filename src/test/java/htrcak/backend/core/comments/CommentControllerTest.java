package htrcak.backend.core.comments;

import htrcak.backend.UtilsTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentControllerTest {

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
    void List_all_comments() throws Exception {
        this.mockMvc.perform(
                        get("/comments")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    @Order(15)
    void Get_a_comment_by_ID() throws Exception {
        this.mockMvc.perform(
                        get("/comments/1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.text", equalTo("This is some long text that is here, bla bla...")))
                .andExpect(jsonPath("$.author.id", is(5)));
    }

    @Test
    @Order(20)
    void Save_new_comments() throws Exception {
        this.mockMvc.perform(
                        post("/comments")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "issueId":"2",
                                    "text":"watch this!"
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(6)))
                .andExpect(jsonPath("$.text", equalTo("watch this!")))
                .andExpect(jsonPath("$.author.id", is(7)));
    }

    @Test
    @Order(25)
    void Update_existing_comment() throws Exception {
        this.mockMvc.perform(
                        patch("/comments/6")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "text":"I am updated"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(6)))
                .andExpect(jsonPath("$.text", equalTo("I am updated")))
                .andExpect(jsonPath("$.author.id", is(7)));
    }

    @Test
    @Order(25)
    void Fail_to_update_existing_comment_as_non_owner() throws Exception {
        this.mockMvc.perform(
                        patch("/comments/6")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenRegular)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "title UPDATED by someone else"
                                        }
                                        """)
                )
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(28)
    void Fail_to_delete_comment_as_non_owner() throws Exception {
        this.mockMvc.perform(
                        delete("/comments/6")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenRegular)
                )
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    @Order(30)
    void Delete_comment() throws Exception {
        this.mockMvc.perform(
                        delete("/comments/6")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void Fail_to_fetch_comments_without_authorization() throws Exception {
        this.mockMvc.perform(
                        get("/comments")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer badJwt")
                )
                .andExpect(status().isUnauthorized());
    }

}
