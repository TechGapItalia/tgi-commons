package it.techgap.common.security.aop;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import it.techgap.common.security.cache.SecurityCache;
import it.techgap.common.security.rules.AspectSecurityRule;
import it.techgap.common.security.security.SecurityPolicy;

import java.util.Collection;

public abstract class AspectSecurity<UserClass extends Authentication> {

    private static final Logger log = LoggerFactory.getLogger(AspectSecurity.class);

    private final SecurityCache<UserClass> securityCache;
    private String defaultPolicy = "denyAnonymousAccess";

    @Autowired
    ApplicationContext applicationContext;

    public AspectSecurity(final SecurityCache<UserClass> securityCache) {
        this.securityCache = securityCache;
    }

    protected abstract UserClass getUser(Authentication authentication);

    @Pointcut("execution(public * *(..))")
    public final void anyPublicOperation() {
    }

    @Pointcut("within(@it.tgi.common.security.annotation.AopSecured *)")
    public final void isSecuredBean() {
    }

    @Around("isSecuredBean() && anyPublicOperation()")
    public Object checkIfMethodAllowed(ProceedingJoinPoint pjp) throws Throwable {
        // Throws an exception if check fails
        securityCheck(AspectSecurityUtils.getSignatureString(pjp), pjp);
        return pjp.proceed();
    }

    private void securityCheck(String signatureName, ProceedingJoinPoint pjp) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserClass theUser = getUser(authentication);
        if (theUser != null) {
            Collection<? extends SecurityPolicy> rules = securityCache.retrieve(theUser, signatureName);
            if (!checkRules(pjp, theUser, rules)) {
                throw new InsufficientAuthenticationException(
                        "Access to method "
                          //      + AspectSecurityUtils.getSignatureString(pjp)
                                + " is not allowed!");
            }
        } else {
            if (!getChecker(defaultPolicy).check(pjp, null)) {
                throw new InsufficientAuthenticationException(
                        "Access to method "
                          //      + AspectSecurityUtils.getSignatureString(pjp)
                                + " is not allowed!");

            }
        }
    }

    private boolean checkRules(ProceedingJoinPoint pjp, UserClass authenticationUser, Collection<? extends SecurityPolicy> rules) {
        if (CollectionUtils.isNotEmpty(rules)) {
            for (SecurityPolicy rule : rules) {
                final AspectSecurityRule checker = getChecker(rule.getPolicy());
                if (checker != null) {
                    //	log.trace("Applying security rule: {}", rule);
                    if (checker.check(pjp, authenticationUser)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    protected AspectSecurityRule<UserClass> getChecker(String rule) {
        return (AspectSecurityRule<UserClass>) applicationContext.getBean(rule, AspectSecurityRule.class);
    }

    /**
     * Configures AspectSecurity's default policy (denyAnonymousAccess by
     * default)
     *
     * @param defaultPolicy The policy to be enforced if none is found.
     */
    public void setDefaultPolicy(String defaultPolicy) {
        if (defaultPolicy == null) {
            throw new IllegalArgumentException("defaultPolicy cannot be null!");
        }
        this.defaultPolicy = defaultPolicy;
    }
}