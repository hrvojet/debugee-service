package htrcak.backend.projects;

import htrcak.backend.issues.data.IssueDTO;
import htrcak.backend.projects.data.ProjectDTO;
import htrcak.backend.projects.data.ProjectPost;
import htrcak.backend.projects.data.ProjectRepositoryJPA;
import htrcak.backend.issues.Issue;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepositoryJPA projectRepositoryJPA;

    public ProjectServiceImpl(ProjectRepositoryJPA projectRepositoryJPA) {
        this.projectRepositoryJPA = projectRepositoryJPA;
    }


    @Override
    public List<ProjectDTO> findAll() {
        return projectRepositoryJPA.findAll().stream().map(this::mapProjectToDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectDTO findById(long id) {
        return projectRepositoryJPA.findById(id).stream().map(this::mapProjectToDTO).findAny().orElse(null);
    }

    @Override
    public Optional<ProjectDTO> saveNewProject(ProjectPost projectPost) {
        Project saved = this.projectRepositoryJPA.save(new Project(projectPost.getTitle(), projectPost.getDescription(), 0, 0));

        return Optional.of(new ProjectDTO(saved.getId(), saved.getDescription(), saved.getTitle(), saved.getOpenedIssues(), saved.getClosedIssues()));
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getTitle(), project.getDescription(), project.getOpenedIssues(), project.getClosedIssues());
    }

}
