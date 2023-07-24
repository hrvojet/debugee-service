package htrcak.backend.core.label;

import htrcak.backend.core.label.data.LabelDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("label")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/{projectID}")
    public List<LabelDTO> getAllLabelsForProject(@PathVariable Long projectID) {
        labelService.doStuff(projectID);

        return  null;
    }

    @PostMapping("/{projectID}")
    public LabelDTO saveNewLabelForProject(@PathVariable Long projectID) {
        labelService.doOtherStuff();
        return null;
    }

}
