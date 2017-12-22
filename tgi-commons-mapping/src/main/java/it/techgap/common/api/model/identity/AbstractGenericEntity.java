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
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;

import it.techgap.common.api.model.BaseEntity;

/**
 * Base implementation of {@link it.techgap.common.api.model.BaseEntity}
 *
 * @param <PK>
 *            Class of the identifier
 */
@MappedSuperclass
public abstract class AbstractGenericEntity<PK extends Serializable> implements Persistable<PK>, BaseEntity<PK> {

	// Persistable implementations

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private PK id;

	public PK getId() {

		return id;
	}

	/**
	 * Sets the id of the entity.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(final PK id) {

		this.id = id;
	}

	public boolean isNew() {

		return null == getId();
	}

	// BaseEntity implementations

	@Column(nullable = false)
	private boolean enabled = true;

	@Column(unique = true)
	private String uuid;

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