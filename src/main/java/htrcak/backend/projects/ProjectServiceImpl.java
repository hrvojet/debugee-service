package htrcak.backend.projects;

import htrcak.backend.projects.data.ProjectDTO;
import htrcak.backend.projects.data.ProjectPatchValidator;
import htrcak.backend.projects.data.ProjectPostValidator;
import htrcak.backend.projects.data.ProjectRepositoryJPA;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<ProjectDTO> saveNewProject(ProjectPostValidator projectPost) {
        Project saved = this.projectRepositoryJPA.save(new Project(projectPost.getTitle(), projectPost.getDescription(), 0, 0));

        return Optional.of(new ProjectDTO(saved.getId(), saved.getDescription(), saved.getTitle(), saved.getOpenedIssues(), saved.getClosedIssues()));
    }

    @Override
    public void deleteById(long id) {
        this.projectRepositoryJPA.deleteById(id);
    }

    @Override
    public Optional<ProjectDTO> updateById(ProjectPatchValidator projectPost, long id) {
        Project p = this.projectRepositoryJPA.getById(id);
        boolean projectIsUpdated = false;

        if(projectPost.getDescription() != null && !projectPost.getDescription().isBlank() && !projectPost.getDescription().equals(p.getDescription())) {
            p.setDescription(projectPost.getDescription());
            projectIsUpdated = true;
        }
        if(projectPost.getTitle() != null && !projectPost.getTitle().isBlank() && !projectPost.getTitle().equals(p.getTitle())) {
            p.setTitle(projectPost.getTitle());
            projectIsUpdated = true;
        }

        if(projectIsUpdated) {
            return Optional.of(mapProjectToDTO(this.projectRepositoryJPA.save(p)));
        } else {
            return Optional.empty();
        }
    }

    private ProjectDTO mapProjectToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getTitle(), project.getDescription(), project.getOpenedIssues(), project.getClosedIssues());
    }

}
