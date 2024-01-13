package htrcak.backend.core.projects;

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
@Order(5)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles("test")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProjectControllerTest {

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
    void Get_a_list_of_all_projects() throws Exception {
        this.mockMvc.perform(
                get("/api/projects")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.['content']").isArray())
                .andDo(print());
    }

    @Test
    @Order(15)
    void Get_a_project_by_ID() throws Exception {
        this.mockMvc.perform(
                        get("/api/projects/1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "title": "Debugee-web",
                        "description": "Description of the first project",
                        "openedIssues": 4,
                        "closedIssues": 0,
                        "owner": {
                            "id": 99,
                            "username": "Hrva",
                            "email": "hrva@va.hr"
                        }
                    }
                """));
    }

    @Test
    @Order(20)
    void Save_new_project() throws Exception {
        this.mockMvc.perform(
                post("/api/projects")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title":"Title of a new project",
                                    "description":"Description of a new project"
                                }
                                """)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                        "id": 6,
                        "title": "Title of a new project",
                        "description": "Description of a new project",
                        "openedIssues": 0,
                        "closedIssues": 0,
                        "owner": {
                            "id": 7,
                            "username": "debugee",
                            "email": "de@bug.e"
                        }
                    }"""));
    }

    @Test
    @Order(25)
    void Update_existing_project() throws Exception {
        this.mockMvc.perform(
                        patch("/api/projects/5")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "description": "description UPDATED"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "id": 5,
                            "title": "Newbie project",
                            "description": "description UPDATED",
                            "openedIssues": 2,
                            "closedIssues": 0,
                            "owner": {
                                "id": 7,
                                "username": "debugee",
                                "email": "de@bug.e"
                            }
                        }"""));
    }

    @Test
    @Order(27)
    void Handle_trying_to_delete_non_existing_project() throws Exception {
        this.mockMvc.perform(
                        delete("/api/projects/44")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenRegular)
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Order(28)
    void Fail_to_delete_project_as_non_owner() throws Exception {
        this.mockMvc.perform(
                        delete("/api/projects/5")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenRegular)
                )
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    @Order(30)
    void Delete_project() throws Exception {
        this.mockMvc.perform(
                delete("/api/projects/6")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    void Fail_to_fetch_projects_without_authorization() throws Exception {
        this.mockMvc.perform(
                get("/api/projects")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer badJwt")
        )
                .andExpect(status().isUnauthorized());
    }

}