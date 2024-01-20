package br.com.stoom.store.exceptions;

import lombok.Getter;

@Getter
public class ErrorData {
    private final int errorCode;
    private final String[] message;

    public ErrorData(final int errorCode, final String... message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
