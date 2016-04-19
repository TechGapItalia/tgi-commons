package it.tgi.common.api.model;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * Interface for a database-backed entity that can be enabled/disabled and has an UUID (for consistent usage inside collections)
 * <br>
 * Implementations should override {@link it.tgi.common.api.model.AbstractGenericEntity} and its subclasses in order to
 * provide consistent semantics for usage inside collections.
 *
 * @param <PK> Identifier class
 */
public interface BaseEntity<PK extends Serializable> extends Persistable<PK> {

    void setId(PK id);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    String getUuid();
}