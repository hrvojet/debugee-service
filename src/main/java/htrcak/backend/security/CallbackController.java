package htrcak.backend.security;

import htrcak.backend.security.services.JwtService;
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
import java.util.Map;

@RestController
public class CallbackController {

    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Value("${gitlab.client-id}")
    private String clientId;

    @Value("${gitlab.callback-url}")
    private String callbackUrl;

    @Value("${gitlab.web-url}")
    private String webUrl;

    public CallbackController(RestTemplate restTemplate, JwtService jwtService) {
        this.restTemplate = restTemplate;
        this.jwtService = jwtService;
    }

    @GetMapping("callback/gitlab")
    public RedirectView gitlabCallback(@RequestParam("code") final String code, HttpServletResponse response) {
        String accessToken = exchangeCodeForAccessToken(code);
        RedirectView redirectView = new RedirectView(webUrl);
        redirectView.addStaticAttribute("access_token", accessToken);

        return redirectView;
    }

    private String exchangeCodeForAccessToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("redirect_uri", callbackUrl);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity("http://192.168.99.101/oauth/token", request, Map.class); // TODO replace GL url with app.prop value
        System.out.println(response.getBody());

        return jwtService.generateJWT((String) response.getBody().get("access_token")); // TODO handle error for emptyBody

    }
}
