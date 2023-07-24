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
        this.description = label.getDescription() == null ? "" : label.getDescription();
        this.colorHex = label.getColorHex();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getColorHex() {
        return colorHex;
    }
}
