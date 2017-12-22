package it.techgap.common.api.mapper;

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
import it.techgap.common.api.model.BaseEntity;

/**
 * Specialization of {@link it.techgap.common.api.mapper.GenericMapper} for entities identified by {@link java.lang.Long}
 *
 * @param <Entity> Entity class
 * @param <DTO>    DTO class
 * @see it.techgap.common.api.mapper.GenericMapper
 */
public abstract class Mapper<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> extends GenericMapper<Entity, DTO, Long> {

    protected Mapper(Class<Entity> entityClazz, Class<DTO> dtoClazz) {
        super(entityClazz, dtoClazz);
    }
}
