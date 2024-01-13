package htrcak.backend.security;

import htrcak.backend.security.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Map;

@RestController
public class CallbackController {

    private static final Logger log = LoggerFactory.getLogger(CallbackController.class);

    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Value("${gitlab.client-id}")
    private String clientId;

    @Value("${gitlab.callback-url}")
    private String callbackUrl;

    @Value("${gitlab.web-url}")
    private String webUrl;

    @Value("${gitlab.uri}")
    private String gitlabUri;

    public CallbackController(RestTemplate restTemplate, JwtService jwtService) {
        this.restTemplate = restTemplate;
        this.jwtService = jwtService;
    }

    @GetMapping("callback/gitlab")
    public RedirectView gitlabCallback(@RequestParam("code") final String code, HttpServletResponse response) {
        log.debug(MessageFormat.format("GET:callback/gitlab, code: {0}, exchanging code for token", code));
        String accessToken = exchangeCodeForAccessToken(code);
        RedirectView redirectView = new RedirectView(webUrl);
        redirectView.addStaticAttribute("access_token", accessToken);

        return redirectView;
    }

    private String exchangeCodeForAccessToken(String code) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        log.debug("Setting headers in 'exchangeCodeForAccessToken' method");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(generateParams(code), headers);

        String gitlabTokenEndpoint = gitlabUri + "/oauth/token";
        log.debug("Exchanging code for token using 'restTemplate', sending code to: " + gitlabTokenEndpoint);
        ResponseEntity<Map> response = restTemplate.postForEntity(gitlabTokenEndpoint, request, Map.class);
        log.debug(MessageFormat.format("Got token in body: {0}", response.getBody()));

        return jwtService.generateJWT((String) response.getBody().get("access_token")); // TODO handle error for emptyBody

    }

    private MultiValueMap<String, String> generateParams(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("redirect_uri", callbackUrl); // TODO merge same origin, append callback endpoint
        params.add("grant_type", "authorization_code");
        return params;
    }
}
