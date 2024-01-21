package br.com.stoom.store.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler({DataIntegrityViolationException.class, HttpMessageNotReadableException.class})
    private ResponseEntity<ErrorData> integrityViolation(final Throwable exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorData(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MultipartException.class)
    private ResponseEntity<ErrorData> fileErrors(final Throwable exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(new ErrorData(HttpStatus.BAD_REQUEST.value(), "Tamanho maximo do upload excedido"), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorData> handleException(final Throwable exception) {
        log.error(exception.getMessage());
        final Class<? extends Throwable> clazz = exception.getClass();
        return new ResponseEntity<>(new ErrorData(this.getHttpStatus(clazz).value(), exception.getMessage()), this.getHttpStatus(clazz));
    }

    private HttpStatus getHttpStatus(final Class<? extends Throwable> clazz) {
        final ResponseStatus responseStatus = clazz.getAnnotation(ResponseStatus.class);
        return HttpStatus.INTERNAL_SERVER_ERROR == responseStatus.value() ? responseStatus.code() : responseStatus.value();
    }
}
