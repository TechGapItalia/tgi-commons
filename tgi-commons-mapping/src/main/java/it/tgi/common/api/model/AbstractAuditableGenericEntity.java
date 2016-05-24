package it.tgi.common.api.model;

import org.joda.time.DateTime;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * Base implementation of BaseAuditableEntity
 *
 * @param <U>  Class of the actor that performs actions on this entity
 * @param <PK> Class of the identifier
 * @see it.tgi.common.api.model.BaseAuditableEntity
 */
@MappedSuperclass
public abstract class AbstractAuditableGenericEntity<U, PK extends Serializable> extends AbstractGenericEntity<PK> implements BaseAuditableEntity<U, PK> {

    @ManyToOne
    private U createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @ManyToOne
    private U lastModifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    public U getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public DateTime getCreatedDate() {
        return null == this.createdDate ? null : new DateTime(this.createdDate);
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = null == createdDate ? null : createdDate.toDate();
    }

    public U getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public DateTime getLastModifiedDate() {
        return null == this.lastModifiedDate ? null : new DateTime(this.lastModifiedDate);
    }

    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.lastModifiedDate = null == lastModifiedDate ? null : lastModifiedDate.toDate();
    }

}