package htrcak.backend.core.comments.data;


public class CommentPatchValidator {

    private String text; //TODO maybe add @NotNull, so it's not validated in service?

    public String getText() {
        return text;
    }
}
