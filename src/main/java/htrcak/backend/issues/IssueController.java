package htrcak.backend.issues;

import htrcak.backend.issues.data.IssueDTO;
import htrcak.backend.issues.data.IssuePostValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) { this.issueService = issueService; }

    @GetMapping
    public List<IssueDTO> findAllIssues() { return issueService.findAll(); }

    @GetMapping("/{id}")
    public IssueDTO findIssueById(@PathVariable final Long id) {
        return issueService.findById(id);
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<IssueDTO> saveIssueById(@PathVariable final long projectId, @RequestBody final IssuePostValidator issuePostValidator) {

        return issueService.saveNewIssue(projectId, issuePostValidator)
                .map(IssueDTO -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(IssueDTO))
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .build()
                );
    }
}
