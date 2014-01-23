package org.xbill.DNS.utils.json.exception;

public class BaseJsonException extends RuntimeException {
    private static final long serialVersionUID = 2875767802768799014L;

    private final Enum<?> exceptionCode;
    private final Object[] objects;

    public BaseJsonException(final Enum<?> exceptionCode, final Object[] objects) {
        super(exceptionCode.name());
        this.exceptionCode = exceptionCode;
        this.objects = objects;
    }

    public BaseJsonException(final Enum<?> exceptionCode, final Throwable cause, final Object[] objects) {
        super(exceptionCode.name(), cause);
        this.objects = objects;
        this.exceptionCode = exceptionCode;
    }

    public Enum<?> getExceptionCode() {
        return exceptionCode;
    }

    public Object[] getObjects() {
        return objects;
    }
}
