package it.techgap.common.api.model.identity;

/**
 * Specialization of {@link it.techgap.common.api.model.AbstractAuditableGenericEntity} for entities using a {@link java.lang.Long} as their primary key
 *
 * @param <U>
 *            Class of the actor that performs actions on this entity
 * @see it.techgap.common.api.model.AbstractAuditableGenericEntity
 */
public abstract class AbstractAuditableEntity<U> extends AbstractAuditableGenericEntity<U, Long> {
}