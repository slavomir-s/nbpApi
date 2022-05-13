package pl.devopsi.akademia.mod7zad2nbp.exceptions;

import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

@Component
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse response,HttpStatus statusCode) throws IOException {
        String responseBodyStr="";
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(response.getBody()));
            responseBodyStr = bf.lines().collect(Collectors.joining(""));
        }catch(Exception e){
            System.err.println(e);
        }
        throw new RestTemplateException(responseBodyStr,statusCode);
    }
}
