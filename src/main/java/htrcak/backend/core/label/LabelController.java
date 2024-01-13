package htrcak.backend.core.label;

import htrcak.backend.core.label.data.LabelDTO;
import htrcak.backend.core.label.data.LabelPatchValidator;
import htrcak.backend.core.label.data.LabelPostValidator;
import htrcak.backend.core.label.data.LabelUpdatePostValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("labels/{projectID}")
    public ResponseEntity<List<LabelDTO>> getAllLabelsForProject(@PathVariable Long projectID) {
        return labelService.getLabelsForProject(projectID);
    }

    @PostMapping("label/{projectID}")
    public ResponseEntity<LabelDTO> saveNewLabelForProject(@PathVariable Long projectID, @Valid @RequestBody LabelPostValidator labelPostValidator) {
        return labelService.saveNewLabel(projectID, labelPostValidator);
    }

    @PostMapping("label/{labelID}/issue/{issueID}")
    public ResponseEntity<?> addLabelToIssue(@PathVariable Long issueID, @PathVariable Long labelID) {
        return labelService.addLabelToIssue(issueID, labelID);
    }

    @DeleteMapping("label/{labelID}/issue/{issueID}")
    public ResponseEntity<?> removeLabelFromIssue(@PathVariable Long issueID, @PathVariable Long labelID) {
        return labelService.removeLabelFromIssue(issueID, labelID);
    }

    @PostMapping("label/update-issue/{issueID}")
    public ResponseEntity<HttpStatus> updateLabelsForIssue(@PathVariable long issueID, @RequestBody LabelUpdatePostValidator labelUpdatePostValidator) {
        return this.labelService.updateLabelsForIssue(issueID, labelUpdatePostValidator);
    }

    @PatchMapping("label/{labelID}")
    public ResponseEntity<?> editLabel(@PathVariable Long labelID, @RequestBody LabelPatchValidator labelPatchValidator) {
        return labelService.editLabel(labelID, labelPatchValidator);
    }

    @DeleteMapping("label/{labelID}")
    public ResponseEntity<?> deleteLabel(@PathVariable Long labelID) {
        return labelService.deleteLabel(labelID);
    }

}
