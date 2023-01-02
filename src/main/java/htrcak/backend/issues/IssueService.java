package htrcak.backend.issues;

import htrcak.backend.issues.data.IssueDTO;
import htrcak.backend.projects.data.ProjectDTO;

import java.util.List;

public interface IssueService {

    List<IssueDTO> findAll();

    IssueDTO findById(long id);
}
