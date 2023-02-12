package com.athub.framework.exception;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    protected String exceptionCode;

    protected String exceptionMessage;

    public BusinessException() {
    }


    public BusinessException(String exceptionCode, String exceptionMessage) {
        super(formatMessage(exceptionCode, exceptionMessage));
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    public BusinessException(String exceptionCode, String exceptionMessage, Throwable cause) {
        super(formatMessage(exceptionCode, exceptionMessage), cause);
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    private static String formatMessage(String exceptionCode, String exceptionMessage) {
        return String.format("[ %s - %s]", exceptionCode, exceptionMessage);
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
