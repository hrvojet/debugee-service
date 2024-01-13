package htrcak.backend.core.issues;

import htrcak.backend.UtilsTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Order(25)
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
    void List_all_issues() throws Exception {
        this.mockMvc.perform(
                get("/api/issues")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$['content']").isArray())
                .andDo(print());
    }

    @Test
    @Order(15)
    void Get_an_issue_by_ID() throws Exception {
        this.mockMvc.perform(
                        get("/api/issues/1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "projectId": 1,
                        "title": "Problem with persistence",
                        "commentNumber": 7,
                        "issueType": "Bug",
                        "originalPoster": {
                            "id": 5,
                            "username": "Reporter",
                            "email": "reporter@asd.asd"
                        }
                    }
                """));
    }

    @Test
    @Order(20)
    void Save_new_issue() throws Exception {
        this.mockMvc.perform(
                        post("/api/issues/2")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "title":"Title POST2",
                                    "firstComment":"sec comment POST"
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                        "id": 8,
                        "projectId": 2,
                        "title": "Title POST2",
                        "commentNumber": 1,
                        "issueType": "",
                        "originalPoster": {
                            "id": 7,
                            "username": "debugee",
                            "email": "de@bug.e"
                        }
                    }
                    """));
    }

    @Test
    @Order(21)
    void Search_for_issues() throws Exception {
        this.mockMvc.perform(
                        post("/api/issues/search?projectId=1&page=0&size=5")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                    "title":"dependency"
                                }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                    "content": [
                            {
                                "id": 2,
                                "projectId": 1,
                                "title": "Unable to install dependency",
                                "commentNumber": 1,
                                "issueType": "Feature",
                                "originalPoster": {
                                    "id": 5,
                                    "username": "Reporter",
                                    "email": "reporter@asd.asd"
                                }
                            },
                            {
                                "id": 4,
                                "projectId": 1,
                                "title": "How to uninstall dependency",
                                "commentNumber": 0,
                                "issueType": "Feature",
                                "originalPoster": {
                                    "id": 3,
                                    "username": "developer",
                                    "email": "developer@example.com"
                                }
                            }
                        ],
                        "totalElements": 2
                    }
                    """));
    }

    @Test
    @Order(25)
    void Update_existing_issue() throws Exception {
        this.mockMvc.perform(
                        patch("/api/issues/7")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "title": "title UPDATED"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                            {
                            "id": 7,
                            "projectId": 5,
                            "title": "title UPDATED",
                            "commentNumber": 0,
                            "issueType": "Feature",
                            "originalPoster": {
                                "id": 7,
                                "username": "debugee",
                                "email": "de@bug.e"
                            }
                        }
                        """));
    }

    @Test
    @Order(25)
    void Fail_to_update_existing_issue_as_non_owner() throws Exception {
        this.mockMvc.perform(
                        patch("/api/issues/1")
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
    void Fail_to_delete_issue_as_non_owner() throws Exception {
        this.mockMvc.perform(
                        delete("/api/issues/6")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenRegular)
                )
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    @Order(30)
    void Delete_issue() throws Exception {
        this.mockMvc.perform(
                        delete("/api/issues/6")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void Fail_to_fetch_issues_without_authorization() throws Exception {
        this.mockMvc.perform(
                        get("/api/issues")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer badJwt")
                )
                .andExpect(status().isUnauthorized());
    }

}
