package htrcak.backend.core.label;

import htrcak.backend.core.label.data.LabelDTO;
import htrcak.backend.core.label.data.LabelPatchValidator;
import htrcak.backend.core.label.data.LabelPostValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("label")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/{projectID}")
    public ResponseEntity<List<LabelDTO>> getAllLabelsForProject(@PathVariable Long projectID) {
        return labelService.getLabelsForProject(projectID);
    }

    @PostMapping("/{projectID}")
    public ResponseEntity<LabelDTO> saveNewLabelForProject(@PathVariable Long projectID, @Valid @RequestBody LabelPostValidator labelPostValidator) {
        return labelService.saveNewLabel(projectID, labelPostValidator);
    }

    @PostMapping("/{labelID}/issue/{issueID}")
    public ResponseEntity<?> addLabelToIssue(@PathVariable Long issueID, @PathVariable Long labelID) {
        return labelService.addLabelToIssue(issueID, labelID);
    }

    @DeleteMapping("/{labelID}/issue/{issueID}")
    public ResponseEntity<?> removeLabelFromIssue(@PathVariable Long issueID, @PathVariable Long labelID) {
        return labelService.removeLabelFromIssue(issueID, labelID);
    }

    @PatchMapping("/{labelID}")
    public ResponseEntity<?> editLabel(@PathVariable Long labelID, @RequestBody LabelPatchValidator labelPatchValidator) {
        return labelService.editLabel(labelID, labelPatchValidator);
    }

    @DeleteMapping("/{labelID}")
    public ResponseEntity<?> deleteLabel(@PathVariable Long labelID) {
        return labelService.deleteLabel(labelID);
    }

}
