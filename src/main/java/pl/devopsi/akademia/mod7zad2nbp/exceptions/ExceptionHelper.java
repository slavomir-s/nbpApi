package pl.devopsi.akademia.mod7zad2nbp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHelper {
    @ExceptionHandler(value = RestTemplateException.class)
    public ResponseEntity<ErrorResponse> handleRestTemplateException(RestTemplateException ex, HttpServletRequest request){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getHttpStatus(),ex.getMessage(),request.getMethod()+" "+request.getRequestURI()),
                ex.getHttpStatus());
    }

    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        System.err.println("Wewnętrzny błąd serwera.");
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
