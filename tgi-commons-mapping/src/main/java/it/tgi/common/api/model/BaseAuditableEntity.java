package it.tgi.common.api.model;

import org.springframework.data.domain.Auditable;

import java.io.Serializable;

/**
 * Extension of {@link it.tgi.common.api.model.BaseEntity} that supports Spring's auditing mechanisms.
 *
 * @param <U>  Class of the actor that performs actions on this entity
 * @param <PK> Class of the identifier
 */
public interface BaseAuditableEntity<U, PK extends Serializable> extends BaseEntity<PK>, Auditable<U, PK> {
}
