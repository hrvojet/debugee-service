package htrcak.backend.core.projects;

import htrcak.backend.core.projects.data.ProjectDTO;
import htrcak.backend.core.projects.data.ProjectPatchValidator;
import htrcak.backend.core.projects.data.ProjectPostValidator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectDTO> findAllProjects() { return projectService.findAll(); }

    @GetMapping("/{id}")
    public ProjectDTO findProjectById(@PathVariable final Long id) {
        return projectService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> saveProject(@Valid @RequestBody final ProjectPostValidator projectPost) {
        return projectService.saveNewProject(projectPost)
                .map(
                        ProjectDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(ProjectDTO)
                ).orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .build()
                );
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,String>> delete(@PathVariable long id) {
        try{
            projectService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            Map<String,String> json = new HashMap<>();
            json.put("projectID",Long.toString(id));
            json.put("error","No project with such ID");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@Valid @RequestBody final ProjectPatchValidator projectPost, @PathVariable final long id) {
        // TODO javax.persistence.EntityNotFoundException
        // https://www.baeldung.com/exception-handling-for-rest-with-spring
        return projectService.updateById(projectPost, id)
                .map(ProjectDTO -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ProjectDTO))
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .build()
                );
    }

}
