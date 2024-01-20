package br.com.stoom.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorData> handleException(final Throwable throwable) {
        final Class<? extends Throwable> clazz = throwable.getClass();
        return new ResponseEntity<>(new ErrorData(this.getHttpStatus(clazz).value(), throwable.getMessage()), this.getHttpStatus(clazz));
    }

    private HttpStatus getHttpStatus(final Class<? extends Throwable> clazz) {
        final ResponseStatus responseStatus = clazz.getAnnotation(ResponseStatus.class);
        return HttpStatus.INTERNAL_SERVER_ERROR == responseStatus.value() ? responseStatus.code() : responseStatus.value();
    }
}
