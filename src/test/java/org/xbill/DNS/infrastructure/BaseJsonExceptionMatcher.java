package org.xbill.DNS.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.xbill.DNS.utils.json.exception.BaseJsonException;

public class BaseJsonExceptionMatcher extends BaseMatcher<BaseJsonException> {

	private Class<? extends BaseJsonException> exceptionClass;
	private final Enum<?> exceptionCode;
	private final Object[] objects;
	private Class<? extends Throwable> causeClass;
	private Throwable cause;
	
	public BaseJsonExceptionMatcher(Class<? extends BaseJsonException> exceptionClass,
                                    Enum<?> exceptionCode,
                                    Object[] objects) {
		super();
		this.exceptionClass = exceptionClass;
		this.exceptionCode = exceptionCode;
		this.objects = objects;
	}
	
	public BaseJsonExceptionMatcher(Class<? extends BaseJsonException> exceptionClass,
                                    Enum<?> exceptionCode,
                                    Object[] objects, Class<? extends Throwable> causeClass) {
		super();
		this.exceptionClass = exceptionClass;
		this.exceptionCode = exceptionCode;
		this.objects = objects;
		this.causeClass = causeClass;
	}

	public BaseJsonExceptionMatcher(Class<? extends BaseJsonException> exceptionClass,
                                    Enum<?> exceptionCode,
                                    Object[] objects, Throwable cause) {
		super();
		this.exceptionClass = exceptionClass;
		this.exceptionCode = exceptionCode;
		this.objects = objects;
		this.cause = cause;
	}

	@Override
	public boolean matches(Object item) {
		if (!exceptionClass.equals(item.getClass())) {
			return false;
		}
        BaseJsonException exception = (BaseJsonException) item;
		if (!exceptionCode.equals(exception.getExceptionCode())) {
			return false;
		}
		if (objects != null) {
			Object[] messageArguments = exception.getObjects();
			if (messageArguments == null || objects.length != messageArguments.length) {
				return false;
			}
			for (int i = 0 ; i < objects.length; i++) {
				if (objects[i] == null) {
					if (messageArguments[i] != null) {
						return false;
					} else {
						continue;
					}
				}
				if (!objects[i].equals(messageArguments[i])) {
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
