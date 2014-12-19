package it.tgi.common.security.rules;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;

public abstract class Allow<U extends Authentication> implements AspectSecurityRule<U> {

    @Override
    public boolean check(ProceedingJoinPoint pjp, U user) {
        return true;
    }


    @Override
    public String describe() {
        return "Always allows access";
    }
}