package htrcak.backend.core.webhook.models;

public class Webhook {

    private String event_name;

    private int project_id;

    private int user_id;

    public Webhook(String event_name, int project_id, int user_id) {
        this.event_name = event_name;
        this.project_id = project_id;
        this.user_id = user_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public int getProject_id() {
        return project_id;
    }

    public int getUser_id() {
        return user_id;
    }
}
