package it.tgi.common.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.AbstractAuditable;

@MappedSuperclass
public abstract class AbstractAuditableEntity<U, PK extends Serializable> extends AbstractAuditable<U, PK> implements AbstractEntityInterface<PK> {

	@Override
	public void setId(PK id) {
		super.setId(id);
	}

	@Column(nullable = false)
	private boolean enabled = true;

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getId() + "]";
	}

}