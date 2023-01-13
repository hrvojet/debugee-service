package htrcak.backend.issues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import htrcak.backend.issues.data.IssueDTO;
import htrcak.backend.issues.data.IssuePatchValidator;
import htrcak.backend.issues.data.IssuePostValidator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) { this.issueService = issueService; }

    @GetMapping
    public List<IssueDTO> findAllIssues(@RequestParam(required = false) final Long projectId) {
        if(projectId != null) {
            return issueService.findAllByProjectId(projectId);
        } else {
            return issueService.findAll();
        }
    }

    @GetMapping("/{id}")
    public IssueDTO findIssueById(@PathVariable final Long id) {
        return issueService.findById(id);
    }

    @PostMapping
    public ResponseEntity<IssueDTO> saveIssueById(@Valid @RequestBody final IssuePostValidator issuePostValidator) {

        return issueService.saveNewIssue(issuePostValidator)
                .map(IssueDTO -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(IssueDTO))
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.NO_CONTENT)
                                .build()
                );
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable long issueId) {
        try{
            issueService.deleteById(issueId);
        } catch (EmptyResultDataAccessException e) {
            Map<String,String> json = new HashMap<>();
            json.put("projectID",Long.toString(issueId));
            json.put("error","No issue with such ID");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{issueId}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable final long issueId, @Valid @RequestBody final IssuePatchValidator issuePatchValidator) {

        return issueService.updateById(issuePatchValidator, issueId)
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
