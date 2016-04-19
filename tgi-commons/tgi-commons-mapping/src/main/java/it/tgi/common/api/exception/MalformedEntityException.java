package it.tgi.common.api.exception;

public class MalformedEntityException extends EntityCheckException {

	private static final long serialVersionUID = 2530797646681558509L;

	public MalformedEntityException(Class<?> entityClass, String field) {
		this(entityClass, field, null);
	}

	public MalformedEntityException(Class<?> entityClass, String field, Object value) {
		super("Malformed entity " + entityClass.getSimpleName() + ": " + String.valueOf(value) + " is not acceptable for " + field + "!");
	}

}
