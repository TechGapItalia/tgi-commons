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

    @Pointcut("within(@it.techgap.common.security.annotation.AopSecured *)")
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
