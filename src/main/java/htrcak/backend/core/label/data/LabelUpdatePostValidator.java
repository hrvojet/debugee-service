package htrcak.backend.core.label.data;

import java.util.List;

public class LabelUpdatePostValidator {

    List<Long> addLabelsWithID;

    List<Long> removeLabelsWithID;

    public List<Long> getAddLabelsWithID() {
        return addLabelsWithID;
    }

    public List<Long> getRemoveLabelsWithID() {
        return removeLabelsWithID;
    }
}
