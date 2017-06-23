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
        if (expression) {
            return (T) lambda.execute(lambdaParams);
        }
        return null;
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
        if (expression) {
            return (T) lambda.execute((Object[]) null);
        }
        return null;
    }
}
