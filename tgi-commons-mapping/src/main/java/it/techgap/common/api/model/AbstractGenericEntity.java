package it.techgap.common.api.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

/**
 * Base implementation of {@link it.techgap.common.api.model.BaseEntity}
 *
 * @param <PK> Class of the identifier
 */
@MappedSuperclass
public abstract class AbstractGenericEntity<PK extends Serializable> extends AbstractPersistable<PK> implements BaseEntity<PK> {

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(unique = true)
    private String uuid;

    @Override
    public void setId(PK id) {
        super.setId(id);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        return getUuid().equals(((AbstractGenericEntity) obj).getUuid());
    }

    @Override
    public final int hashCode() {
        return getUuid().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + "]";
    }

}