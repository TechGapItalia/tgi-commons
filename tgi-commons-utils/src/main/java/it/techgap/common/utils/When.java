package it.techgap.common.utils;

/*-
 * #%L
 * tgi-commons-utils
 * %%
 * Copyright (C) 2016 - 2017 TechGap Italia
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


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
