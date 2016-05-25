package it.techgap.common.security.cache;

import org.springframework.security.core.Authentication;

import it.techgap.common.security.security.SecurityPolicy;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

/**
 * A simple cache for Totem's security policies
 */
public interface SecurityCache<U extends Authentication> {

    Collection<? extends SecurityPolicy> retrieve(U user, String method) throws ExecutionException;

    void refresh(U user);
}
