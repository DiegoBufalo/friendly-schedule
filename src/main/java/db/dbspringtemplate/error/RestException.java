package db.dbspringtemplate.error;

import org.springframework.http.HttpStatus;

import java.util.Optional;

public class RestException extends RuntimeException {

    private final HttpStatus status;
    private final String errorMessage;

    public RestException(HttpStatus status, String errorMessage) {
        super();
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Optional<String> getErrorMessage() {
        return Optional.of(this.errorMessage);
    }

}
