package it.tgi.common.security.cache;

import it.tgi.common.security.security.SecurityPolicy;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * A simple cache for Totem's security policies
 */
public interface SecurityCache<U extends Authentication> {

    Collection<? extends SecurityPolicy> retrieve(U user, String method) throws ExecutionException;

    void refresh(U user);
}
