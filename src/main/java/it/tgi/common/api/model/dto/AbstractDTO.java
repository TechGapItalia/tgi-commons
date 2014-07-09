package it.tgi.common.api.model.dto;

import java.io.Serializable;

public abstract class AbstractDTO implements Serializable {

	protected String clazz;
	protected Long id;

	public AbstractDTO() {
		this.clazz = getClass().getSimpleName().replace("DTO", "");
	}

	public String getClazz() {
		return clazz;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + clazz + ", " + id + "]";
	}

}