package gr.iot.iot.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by smyrgeorge on 5/12/17.
 */
public class ServiceException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;

    public ServiceException(HttpStatus httpStatus, String errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public ServiceException(HttpStatus httpStatus, String errorCode) {
        super(errorCode);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public ServiceException(String message, String errorCode) {
        super(message);
        this.httpStatus = null;
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode) {
        super(errorCode);
        this.httpStatus = null;
        this.errorCode = errorCode;
    }

    public ServiceException(HttpStatus httpStatus, String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

