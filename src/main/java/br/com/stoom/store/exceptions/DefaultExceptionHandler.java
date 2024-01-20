package br.com.stoom.store.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class, HttpMessageNotReadableException.class})
    private ResponseEntity<ErrorData> integrityViolation(final Throwable exception) {
        return new ResponseEntity<>(new ErrorData(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

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
