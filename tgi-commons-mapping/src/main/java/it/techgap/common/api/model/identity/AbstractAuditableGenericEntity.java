package it.techgap.common.api.model.identity;

/*-
 * #%L
 * tgi-commons-mapping
 * %%
 * Copyright (C) 2016 TechGap Italia
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.io.Serializable;
import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import it.techgap.common.api.model.BaseAuditableEntity;

/**
 * Base implementation of BaseAuditableEntity
 *
 * @param <U>
 *            Class of the actor that performs actions on this entity
 * @param <PK>
 *            Class of the identifier
 * @see it.techgap.common.api.model.BaseAuditableEntity
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