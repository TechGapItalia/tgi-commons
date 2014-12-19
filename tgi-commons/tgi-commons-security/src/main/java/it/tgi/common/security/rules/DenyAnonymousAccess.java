package it.tgi.common.security.rules;


import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.security.core.Authentication;

public abstract class DenyAnonymousAccess<U extends Authentication> extends AspectSecurityRule<U> {

    @Override
    public boolean check(ProceedingJoinPoint pjp, U user) {
        return user != null && user.isAuthenticated();
    }

    @Override
    public String describe() {
        return "Allows access to authenthicated users only";
    }
}