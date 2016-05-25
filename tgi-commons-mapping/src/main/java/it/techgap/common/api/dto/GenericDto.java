package it.techgap.common.api.dto;

import java.io.Serializable;

/**
 * A DTO able to use any kind of {@link Serializable} as its identifier
 * @param <PK> Identifier type
 */
public abstract class GenericDto<PK extends Serializable> implements Serializable {

    private String clazz;
    private PK id;
    private boolean enabled;

    public GenericDto() {
        this.clazz = getClass().getSimpleName().replace("DTO", "");
    }

    public String getClazz() {
        return clazz;
    }

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + clazz + ", " + id + "]";
    }

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}