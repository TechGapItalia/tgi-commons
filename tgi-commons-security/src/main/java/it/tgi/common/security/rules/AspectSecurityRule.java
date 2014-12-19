package it.tgi.common.security.rules;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;

public abstract class AspectSecurityRule<U extends Authentication> {
    public abstract boolean check(ProceedingJoinPoint pjp, U user);

    public abstract String describe();
}
