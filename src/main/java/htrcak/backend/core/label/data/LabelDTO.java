package htrcak.backend.core.label.data;

import htrcak.backend.core.label.Label;

public class LabelDTO {

    private long id;

    private String name;

    private String description;

    private String colorHex;

    public LabelDTO(Label label) {
        this.id = label.getId();
        this.name = label.getName();
        this.description = label.getDescription();
        this.colorHex = label.getColorHex();
    }
}
