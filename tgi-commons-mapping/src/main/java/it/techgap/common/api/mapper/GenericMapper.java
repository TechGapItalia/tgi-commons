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


import com.google.common.base.Converter;
import com.google.common.base.Throwables;

import it.techgap.common.api.dto.GenericDto;
import it.techgap.common.api.model.BaseEntity;

import java.io.Serializable;

/**
 * A bi-direction {@link Entity} to {@link DTO} converter, implemented using Guava's {@link com.google.common.base.Converter}
 *
 * @param <Entity> Entity class
 * @param <DTO>    DTO class
 * @param <PK>     Identifier class
 */
public abstract class GenericMapper<Entity extends BaseEntity<PK>, DTO extends GenericDto<PK>, PK extends Serializable> extends Converter<Entity, DTO> {

    private Class<Entity> entityClazz;
    private Class<DTO> dtoClazz;

    /**
     * Retrieves an {@link Entity} given its primary key, usually using some kind of
     * {@link it.techgap.common.api.repository.Repository}
     *
     * @param id Primary key of the {@link Entity}
     * @return The requested entity, null if it does not exists
     */
    protected abstract Entity retrieveEntity(PK id);

    /**
     * Converts a {@link DTO} to the corresponding {@link Entity}.
     * <br>
     * The {@link Entity id is automatically set}
     *
     * @param dto source {@link DTO}
     * @param e   target {@link Entity}
     */
    protected abstract void fillEntity(DTO dto, Entity e);

    /**
     * Converts an {@link Entity} to the corresponding {@link DTO}.
     * <br>
     * The {@link DTO id is automatically set}
     *
     * @param e   source {@link Entity}
     * @param dto target {@link DTO}
     */
    protected abstract void fillDto(Entity e, DTO dto);

    protected GenericMapper(Class<Entity> entityClazz, Class<DTO> dtoClazz) {
        this.entityClazz = entityClazz;
        this.dtoClazz = dtoClazz;
    }

    @Override
    protected final DTO doForward(Entity e) {
        DTO result = buildDTO();
        result.setId(e.getId());
        result.setEnabled(e.isEnabled());
        fillDto(e, result);
        return result;
    }

    @Override
    protected final Entity doBackward(DTO input) {
        final PK id = input.getId();
        Entity result = null;

        if (id != null) {
            result = retrieveEntity(id);
        }

        if (result == null) {
            result = buildEntity();
        }
        result.setEnabled(input.getEnabled());
        fillEntity(input, result);
        return result;
    }


    private Entity buildEntity() {
        try {
            return entityClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Throwables.propagate(e);
        }
        return null;
    }

    protected DTO buildDTO() {
        try {
            return dtoClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Throwables.propagate(e);
        }
        return null;
    }
}