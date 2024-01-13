package htrcak.backend.core.webhook;

import htrcak.backend.core.webhook.models.Webhook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/webhook")
public class WebhookController {

    @PostMapping
    public ResponseEntity<String> handleGitlabHook(@RequestHeader("X-Gitlab-Token") final String header, @RequestBody final Webhook webhook) {
        // TODO https://docs.gitlab.com/14.10/ee/user/project/integrations/webhooks.html#failing-webhooks
        // TODO handle hooks for creating groups, users, projects, permissions
        // TODO create group entity
        System.out.println(webhook);
        // TODO async save
        if(webhook != null) {
            if (webhook.getEvent_name().equals("project_create")) {
                System.out.println("Project created");
                System.out.println("Project ID: " + webhook.getProject_id());
            } else if (webhook.getEvent_name().equals("user_create")) {
                System.out.println("User created");
                System.out.println("User ID: " + webhook.getUser_id());
            } else {
                System.out.println("Hook event not recognised!");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
