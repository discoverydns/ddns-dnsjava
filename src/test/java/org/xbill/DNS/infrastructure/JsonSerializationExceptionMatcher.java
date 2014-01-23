package org.xbill.DNS.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.xbill.DNS.utils.json.exception.JsonSerializationException;
import org.xbill.DNS.utils.json.exception.JsonSerializationException.JsonSerializationExceptionCode;

public class JsonSerializationExceptionMatcher extends
        BaseMatcher<JsonSerializationException> {
    private JsonSerializationExceptionCode exceptionCode;
    private Object[] arguments;
    private Class<? extends Throwable> causeClass;
    private Throwable cause;

    public JsonSerializationExceptionMatcher(
            final JsonSerializationExceptionCode exceptionCode,
            final Throwable cause, final Object[] objects) {
        this.exceptionCode = exceptionCode;
        this.cause = cause;
        this.arguments = objects;
    }

    public JsonSerializationExceptionMatcher(
            final JsonSerializationExceptionCode exceptionCode,
            final Object[] objects) {
        this.exceptionCode = exceptionCode;
        this.arguments = objects;
    }

    @Override
    public boolean matches(Object item) {
        if (item == null
            || !JsonSerializationException.class.isAssignableFrom(item.getClass())) {
            return false;
        }
        JsonSerializationException exception = (JsonSerializationException) item;
        if (!exceptionCode.equals(exception.getExceptionCode())) {
            return false;
        }
        if (arguments != null) {
            Object[] messageArguments = exception.getObjects();
            if (messageArguments == null || arguments.length != messageArguments.length) {
                return false;
            }
            for (int i = 0 ; i < arguments.length; i++) {
                if (arguments[i] == null) {
                    if (messageArguments[i] != null) {
                        return false;
                    } else {
                        continue;
                    }
                }
                if (!arguments[i].equals(messageArguments[i])) {
                    return false;
                }
            }
        }
        if (cause != null && !cause.equals(exception.getCause())) {
            return false;
        }
        if (causeClass != null) {
            if (exception.getCause() == null || !causeClass.equals(exception.getCause().getClass())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) { }
}
