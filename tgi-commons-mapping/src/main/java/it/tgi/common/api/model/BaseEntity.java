package it.tgi.common.api.model;

import org.springframework.data.domain.Persistable;

public interface BaseEntity extends Persistable<Long> {

	void setId(Long id);

	boolean isEnabled();

	void setEnabled(boolean enabled);

	String getUuid();
}