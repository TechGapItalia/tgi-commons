package it.tgi.common.api.exception;

public class IdMismatchException extends EntityCheckException {

	private static final long serialVersionUID = 2530797646681558509L;

	public IdMismatchException(Class<?> entityClass) {
        super("Could not modify " + entityClass.getClass().getSimpleName()+ ". The given id does not match");
	}

}
