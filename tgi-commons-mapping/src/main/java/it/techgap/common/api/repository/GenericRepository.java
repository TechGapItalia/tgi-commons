package it.techgap.common.api.repository;

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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import it.techgap.common.api.model.BaseEntity;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity, PK extends Serializable> extends JpaRepository<T, PK> {

	@Transactional(readOnly = true)
	@Query("FROM #{#entityName} t ")
	public List<T> findAll();
	
	@Transactional(readOnly = true)
	@Query("FROM #{#entityName} t WHERE t.enabled = true")
	public List<T> findAllEnabled();

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = true WHERE t.id = :id")
	public void enable(@Param("id") PK id);

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = false WHERE t.id = :id")
	public void disable(@Param("id") PK id);

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = false")
	public void disableAll();

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = true")
	public void enableAll();

}