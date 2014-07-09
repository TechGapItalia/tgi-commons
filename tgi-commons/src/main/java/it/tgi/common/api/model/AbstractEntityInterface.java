package it.tgi.common.api.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;

@MappedSuperclass
public interface AbstractEntityInterface<PK extends Serializable> extends Persistable<PK> {

	public void setId(PK id);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

}