package htrcak.backend.issues;

import htrcak.backend.issues.data.IssueDTO;

import java.util.List;

public interface IssueService {

    List<IssueDTO> findAll();

    //ProjectDTO findById(long id);
}
