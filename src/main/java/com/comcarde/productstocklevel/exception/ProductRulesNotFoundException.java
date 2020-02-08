package com.comcarde.productstocklevel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProductRulesNotFoundException extends Throwable {
    public ProductRulesNotFoundException(String message) {
        super(message);
    }
}
