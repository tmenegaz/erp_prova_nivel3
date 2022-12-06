package com.erp.provanivel3.domain.exception;

public class DescontoException extends RuntimeException {

    String attr;

    public DescontoException(String message) {
        super(message);

    }

    public DescontoException(String message, String attr) {
        super(message);
        this.attr = attr;
    }

}
