package htrcak.backend.core.projects;

import htrcak.backend.UtilsTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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
    void findAllProjects() throws Exception {
        this.mockMvc.perform(
                get("/projects")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andDo(print());
    }

    @Test
    @Order(15)
    void findProjectById() throws Exception {
        this.mockMvc.perform(
                        get("/projects/1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    {
                        "id": 1,
                        "title": "Title 1",
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
    void saveNewProject() throws Exception {
        this.mockMvc.perform(
                post("/projects")
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
                        "id": 4,
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
    void updateProject() throws Exception {
        this.mockMvc.perform(
                        patch("/projects/4")
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
                            "id": 4,
                            "title": "Title of a new project",
                            "description": "description UPDATED",
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
    @Order(30)
    void deleteProject() throws Exception {
        this.mockMvc.perform(
                delete("/projects/4")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenAdmin)
        )
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}