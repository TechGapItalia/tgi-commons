package it.tgi.common.api.model;

import org.springframework.data.domain.Auditable;

public interface BaseAuditableEntity<U> extends BaseEntity, Auditable<U, Long> {
}
