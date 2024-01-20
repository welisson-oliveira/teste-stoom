package br.com.stoom.store.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorData {
    private final int errorCode;
    private final String message;

}
