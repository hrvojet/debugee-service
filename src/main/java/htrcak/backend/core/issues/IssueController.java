package htrcak.backend.core.issues;

import htrcak.backend.core.issues.data.IssueDTO;
import htrcak.backend.core.issues.data.IssuePatchValidator;
import htrcak.backend.core.issues.data.IssuePostValidator;
import htrcak.backend.core.issues.data.IssueSearchCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("issues")
public class IssueController {

    private final IssueService issueService;

    // TODO možda pass param i dohvati samo issue vezane za projekt?

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public Page<IssueDTO> findAllIssues(
            @RequestParam(required = false) final Long projectId,
            @RequestParam(required = false, defaultValue = "0") final int page,
            @RequestParam(required = false, defaultValue = "5") final int size,
            @RequestParam(required = false, defaultValue = "ASC") final String sortBy,
            @RequestParam(required = false, defaultValue = "id") final String id) { //TODO default sort change
        if (projectId == null) {
           return issueService.findAll(createPageRequest(page, size, sortBy, id));
        } else {
            return issueService.getAllIssuesForProject(projectId, createPageRequest(page, size, sortBy, id));
        }
    }

    @GetMapping("/{id}")
    public IssueDTO findIssueById(@PathVariable final Long id) {
        return issueService.findById(id);
    }

    // https://stackoverflow.com/questions/54774350/multiple-sort-optional-query-spring-rest-controller-configuration-with-paginat
    @PostMapping("/search")
    public Page<IssueDTO> searchIssuesByProject(
            @RequestParam final Long projectId,
            @RequestParam(required = false, defaultValue = "0") final int page,
            @RequestParam(required = false, defaultValue = "5") final int size,
            @RequestParam(required = false, defaultValue = "ASC") final String sortBy,
            @RequestParam(required = false, defaultValue = "id") final String id,
            @Valid @RequestBody IssueSearchCommand isc) {
        return issueService.searchIssues(projectId, createPageRequest(page, size, sortBy, id), isc);
    }

    @PostMapping("/{projectID}")
    public ResponseEntity<IssueDTO> saveIssueByProjectID(@PathVariable Long projectID, @Valid @RequestBody final IssuePostValidator issuePostValidator) {
        return issueService.saveNewIssue(projectID, issuePostValidator);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<?> delete(@PathVariable long issueId) {
        return issueService.deleteById(issueId);
    }

    @PatchMapping("/{issueId}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable final long issueId, @Valid @RequestBody final IssuePatchValidator issuePatchValidator) {
        return issueService.updateById(issuePatchValidator, issueId);
    }

    private Pageable createPageRequest(int page, int size, String sortBy, String id) {
        return PageRequest.of(page, size, Sort.by("ASC".equalsIgnoreCase(sortBy) ? Sort.Direction.ASC : Sort.Direction.DESC, id));
    }
}
