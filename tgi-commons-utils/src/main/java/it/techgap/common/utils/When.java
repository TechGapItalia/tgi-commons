package it.techgap.common.utils;

/**
 * Class implementing a Kolin-like when structure, which execute some code when a condition is true
 */
@SuppressWarnings("unchecked")
public class When {

    /**
     * Evaluate a new condition
     *
     * @param expression   boolean condition that triggers the lambda execution
     * @param lambda       code to run when the condition is true
     * @param lambdaParams parameters that can be passed to the {@link WhenLambda}
     * @param <T>          Return type of the method
     * @return the return object of {@link WhenLambda#execute}, or null if the expression is falsey
     */
    public static <T> T when(Boolean expression, WhenLambda lambda, Object... lambdaParams) {
        return expression ? (T) lambda.execute(lambdaParams) : null;
    }

    /**
     * Evaluate a new condition that doesn't need any parameter
     *
     * @param expression boolean condition that triggers the lambda execution
     * @param lambda     code to run when the condition is true
     * @param <T>        Return type of the method
     * @return the return object of {@link WhenLambda#execute}, or null if the expression is falsey
     */
    public static <T> T when(Boolean expression, WhenLambda lambda) {
        return expression ? (T) lambda.execute((Object[]) null) : null;
    }

    /**
     * Execute multiple {@link When#when} and returns the first value that isn't null.
     * Should be used with multiple mutually exclusive whens to execute the correct one based on a precise condition.
     * Ideally, only one of the whens should return a non-null value at a time.
     *
     * @param results outputs of multiple {@link When#when}
     * @param <T>     return type
     * @return the first non-null result value
     */
    public static <T> T multiWhen(Object... results) {
        for (Object result : results) {
            if (result != null) return (T) result;
        }
        return null;
    }
}
