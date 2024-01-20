package br.com.stoom.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockLimitException extends RuntimeException {
    public StockLimitException(final String msg) {
        super(msg);
    }
}
