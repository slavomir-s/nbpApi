package pl.devopsi.akademia.mod7zad2nbp.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Data
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date timestamp;
    private HttpStatus code;
    private String requestUri;
    private String message;


    public ErrorResponse() {
        timestamp = new Date();
    }

    public ErrorResponse(HttpStatus requestStatusCode,String errorMessage,String requestUri) {
        this.timestamp = new Date();
        this.code = requestStatusCode;
        this.message = errorMessage;
        this.requestUri = requestUri;
    }
    public ErrorResponse(HttpStatus requestStatusCode,String errorMessage) {
        this.timestamp = new Date();
        this.code = requestStatusCode;
        this.message = errorMessage;
        this.requestUri = "";
    }

}
