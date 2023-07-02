package htrcak.backend.core.issues;

import htrcak.backend.core.issues.data.IssueDTO;
import htrcak.backend.core.issues.data.IssuePatchValidator;
import htrcak.backend.core.issues.data.IssuePostValidator;
import htrcak.backend.core.issues.data.IssueSearchCommand;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("issues")
public class IssueController {

    private final IssueService issueService;

    // TODO možda pass param i dohvati samo issue vezane za projekt?

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public Page<IssueDTO> findAllIssues(@RequestParam(required = false) final Long projectId) {
        if (projectId == null) {
           return issueService.findAll();
        } else {
            return issueService.getAllIssuesForProject(projectId);
        }
    }

    @GetMapping("/{id}")
    public IssueDTO findIssueById(@PathVariable final Long id) {
        return issueService.findById(id);
    }

    @PostMapping("/search/{projectId}")
    public Page<IssueDTO> searchIssuesByProject(@PathVariable final long projectId, @Valid @RequestBody(required = false) IssueSearchCommand isc) {
        return issueService.searchIssues(projectId, isc);
    }

    @PostMapping
    public ResponseEntity<IssueDTO> saveIssueByProjectID(@Valid @RequestBody final IssuePostValidator issuePostValidator) {
        return issueService.saveNewIssue(issuePostValidator);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<?> delete(@PathVariable long issueId) {
        return issueService.deleteById(issueId);
    }

    @PatchMapping("/{issueId}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable final long issueId, @Valid @RequestBody final IssuePatchValidator issuePatchValidator) {
        return issueService.updateById(issuePatchValidator, issueId);
    }
}
