package htrcak.backend.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            EntityNotFoundException.class,
            EmptyResultDataAccessException.class
    })
    public final ResponseEntity<?> handleExceptions(Exception exception, WebRequest request) {

        Map<String, String> body = parseMessage(exception);

        if (exception instanceof EntityNotFoundException || exception instanceof EmptyResultDataAccessException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        }
    }


    private HashMap<String,String> parseMessage(Exception ex) {
        String message = null;
        if (ex.getMessage() != null) {
            message = filterPackagePath(ex.getMessage());
        }
        HashMap<String ,String > body = new HashMap<>();
        body.put("exception", ex.getClass().getSimpleName());
        body.put("message", message);
        return body;
    }

    private String filterPackagePath(String str) {
        String regex = "\\b\\w+(\\.\\w+)+\\.(.*)\\b";
        String replacement = "$2";
        return str.replaceAll(regex,replacement);
    }
}
