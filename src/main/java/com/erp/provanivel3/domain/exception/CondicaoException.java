package com.erp.provanivel3.domain.exception;

public class CondicaoException extends RuntimeException {

    String attr;

    public CondicaoException(String message, String attr) {
        super(message);
        this.attr = attr;
    }

    public CondicaoException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return String.format("Condição ilegal: %s", attr);
    }
}
