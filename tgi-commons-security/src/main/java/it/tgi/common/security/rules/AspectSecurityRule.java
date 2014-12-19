package it.tgi.common.security.rules;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;

public interface AspectSecurityRule<U extends Authentication> {
    boolean check(ProceedingJoinPoint pjp, U user);

    String describe();
}
