package htrcak.backend.core.label.data;

import javax.validation.constraints.NotBlank;

public class LabelPostValidator {

    @NotBlank(message = "Text must not be empty")
    private String name;

    @NotBlank(message = "Text must not be empty")
    private String colorHex;

    private String description;

    public String getName() {
        return name;
    }

    public String getColorHex() {
        return colorHex;
    }

    public String getDescription() {
        return description;
    }
}
