package gr.iot.iot.web.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 7/29/16.
 */
@RestController
public class IndexController implements ErrorController {

    public static final String ERROR_PATH = "/error";
    private static final String SWAGGER_PATH = "/swagger-ui.html";


    @RequestMapping(value = "/swagger", method = RequestMethod.GET)
    public ResponseEntity swagger() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", SWAGGER_PATH);
        return new ResponseEntity<String>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
    }

    @RequestMapping(value = ERROR_PATH)
    public String error() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/static/html/error.html");
        return IOUtils.toString(inputStream);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
