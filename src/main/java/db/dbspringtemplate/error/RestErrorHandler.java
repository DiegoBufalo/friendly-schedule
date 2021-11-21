package db.dbspringtemplate.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestErrorHandler.class);

    @ExceptionHandler(RestException.class)
    public ResponseEntity<RestErrorModel> handleRestException(RestException rse) {
        RestErrorModel restErrorModel = new RestErrorModel(rse.getErrorMessage().orElse("No additional details"));
        return new ResponseEntity<>(restErrorModel, rse.getStatus());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestErrorModel handleBindException(BindException be) {
        log.warn("Validation exception", be);
        return new RestErrorModel("Bad request");
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorModel handleException(Throwable e) {
        log.error("An unexpected exception has been thrown in a REST service", e);
        return new RestErrorModel("Internal server error");
    }

}
