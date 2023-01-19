package htrcak.backend.core.webhook;

import htrcak.backend.core.webhook.models.Webhook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("webhook")
public class WebhookController {

    @PostMapping
    public ResponseEntity<String> handleGitlabHook(@RequestHeader("X-Gitlab-Token") final String header, @RequestBody final Webhook webhook) {
        System.out.println(webhook);
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
