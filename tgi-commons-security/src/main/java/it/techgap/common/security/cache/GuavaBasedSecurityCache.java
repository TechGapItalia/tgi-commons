package it.techgap.common.security.cache;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;

import it.techgap.common.security.security.SecurityPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class GuavaBasedSecurityCache<U extends Authentication, P extends SecurityPolicy> implements SecurityCache<U> {

    private static final Logger logger = LoggerFactory.getLogger(GuavaBasedSecurityCache.class);

    private static final long MAX_CACHE_SIZE = 1000;

    private final LoadingCache<U, ImmutableListMultimap<String, P>> cache;

    private final PolicyNameGetter getPolicyName;

    public GuavaBasedSecurityCache() {
        this(30, TimeUnit.SECONDS);
    }

    public GuavaBasedSecurityCache(long duration, TimeUnit timeUnit) {
        getPolicyName = new PolicyNameGetter();
        cache = CacheBuilder.newBuilder()
                .maximumSize(MAX_CACHE_SIZE)
                .expireAfterWrite(duration, timeUnit)
                .build(new SecurityCacheLoader());
    }

    protected abstract Collection<P> getPoliciesFor(U user);

    private class PolicyNameGetter implements Function<SecurityPolicy, String> {
        @Override
        public String apply(SecurityPolicy input) {
            return input.getMethod();
        }
    }

    private class SecurityCacheLoader extends CacheLoader<U, ImmutableListMultimap<String, P>> {
        @Override
        public ImmutableListMultimap<String, P> load(U user) throws Exception {
            try {
                final Collection<P> policies = getPoliciesFor(user);
                return Multimaps.index(policies, getPolicyName);
            } catch (Exception e) {
                logger.error("Error while retrieving policies for user {}", user, e);
                return ImmutableListMultimap.of();
            }
        }
    }

    @Override
    public Collection<? extends SecurityPolicy> retrieve(U user, final String method) throws ExecutionException {
        return cache.get(user).get(method);
    }

    @Override
    public void refresh(U user) {
        cache.refresh(user);
    }
}
