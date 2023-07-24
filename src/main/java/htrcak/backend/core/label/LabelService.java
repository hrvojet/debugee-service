package htrcak.backend.core.label;

import htrcak.backend.core.label.data.LabelDTO;
import htrcak.backend.core.label.data.LabelPatchValidator;
import htrcak.backend.core.label.data.LabelPostValidator;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LabelService {

    ResponseEntity<List<LabelDTO>> getLabelsForProject(Long projectID);

    ResponseEntity<LabelDTO> saveNewLabel(Long projectID, LabelPostValidator labelPostValidator);

    ResponseEntity<?> addLabelToIssue(Long issueID, Long labelID);

    ResponseEntity<?> removeLabelFromIssue(Long issueID, Long labelID);

    ResponseEntity<?> editLabel(Long labelID, LabelPatchValidator labelPatchValidator);

    ResponseEntity<?> deleteLabel(Long labelID);
}
