package it.tgi.common.security.aop;

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
