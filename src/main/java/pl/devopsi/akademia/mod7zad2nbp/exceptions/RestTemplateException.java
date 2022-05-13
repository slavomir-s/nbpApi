package pl.devopsi.akademia.mod7zad2nbp.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class RestTemplateException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;
    public RestTemplateException(){
        super();
    }
    public RestTemplateException(String message){

        super(message);
    }
    public RestTemplateException(String message, HttpStatus httpStatusCode){
        super(message);
        this.message = message;
        this.httpStatus =httpStatusCode;
    }
    public RestTemplateException(String message, HttpStatus httpStatus,String requestURI){
        super(message);
        this.message = message;
        this.httpStatus = this.httpStatus;
    }
    public RestTemplateException(String message, String requestURI){
        super(message);
        this.message = message;
    }

}
