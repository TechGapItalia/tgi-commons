package it.techgap.common.utils;

/**
 * Code to execute when the condition in {@link When#when} is true
 */
public interface WhenLambda {

    /**
     * Code to evaluate
     *
     * @param parameters Optional parameters that can be passed. If none, parameters is null
     * @return something
     */
    Object execute(Object... parameters);
}
