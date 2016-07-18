package it.techgap.common.security.aop;

/*-
 * #%L
 * tgi-commons-security
 * %%
 * Copyright (C) 2016 TechGap Italia
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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Modifier;

public abstract class AspectSecurityUtils {

    public static Class<?> getTargetClass(final ProceedingJoinPoint pjp) {
        final Class<?> declaringType = pjp.getSignature().getDeclaringType();
        final int modifiers = declaringType.getModifiers();
        if (Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers)) {
            return pjp.getTarget().getClass();
        } else {
            return declaringType;
        }
    }

    public static MethodSignature getMethodSignature(final ProceedingJoinPoint pjp) {
        return (MethodSignature) pjp.getSignature();
    }

    public static String getSignatureString(final ProceedingJoinPoint pjp) {
        return getTargetClass(pjp).getCanonicalName() + "." + getMethodSignature(pjp).getName();
    }
}
