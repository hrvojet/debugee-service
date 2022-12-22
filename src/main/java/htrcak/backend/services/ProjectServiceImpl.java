package htrcak.backend.services;

import htrcak.backend.repository.data.ProjectRepositoryJPA;
import htrcak.backend.repository.dto.IssueDTO;
import htrcak.backend.repository.dto.ProjectDTO;
import htrcak.backend.repository.models.Issue;
import htrcak.backend.repository.models.Project;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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

    private ProjectDTO mapProjectToDTO(Project project) {
        return new ProjectDTO(project.getId(), project.getTitle(), project.getDescription());
    }

    private Set<IssueDTO> mapIssuesToDTO(Set<Issue> issues) {
        Set<IssueDTO> issueDTOS = new HashSet<>();

        for(Issue i : issues) {
            issueDTOS.add(new IssueDTO(i.getId(), i.getTitle(), i.getCommentNumber(), i.getIssueType()));
        }

        return issueDTOS;
    }
}
