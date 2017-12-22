package it.techgap.common.api.service;

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

import it.techgap.common.api.dto.GenericDto;
import it.techgap.common.api.mapper.MapperWithList;
import it.techgap.common.api.model.BaseEntity;
import it.techgap.common.api.repository.GenericRepository;

public abstract class GenericReadOnlyServiceWithList<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> {
    protected abstract GenericRepository<Entity, Long> getEntityRepository();

    
    protected abstract MapperWithList<Entity, DTO> getEntityMapper();

    public Iterable<DTO> list(boolean withDeleted) {
    	return withDeleted?
    			getEntityMapper().convertAll(getEntityRepository().findAll())
    		:	getEntityMapper().convertAll(getEntityRepository().findAllEnabled());
    }
    

    public DTO get(Long id) {
        return getEntityMapper().convert(getEntityRepository().findOne(id));
    }
}
