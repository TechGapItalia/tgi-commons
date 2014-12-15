package it.tgi.common.api.model;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity extends AbstractPersistable<Long> implements BaseEntity {

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(unique = true)
    private String uuid;

    @Override
    public void setId(Long id) {
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
        return getUuid().equals(((AbstractEntity) obj).getUuid());
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